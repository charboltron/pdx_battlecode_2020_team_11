package master_player;
import battlecode.common.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Miner extends Unit {


    int numDesignSchools        = 0;
    int numRefineries           = 0;
    int numFulFillmentCenters   = 0;
    int numVaporators           = 0;
    int numNetGuns              = 0;
    int numDrones               = 0;
    int numLandscapers          = 0;
    int numMiners               = 0;
    int teamSoup                = 0;

    MapLocation nearestRefinery = null;

    ArrayList<MapLocation> soupLocations;

    public Miner(RobotController r) {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        if(comms.onlyOneMessageToRead()) {comms.getMessages();}
        updateRobotsCounts();


        if(nearestRefinery == null && hqLoc != null){
            nearestRefinery = hqLoc;
        }

        teamSoup = rc.getTeamSoup();
        soupLocations = comms.soupLocations;
        checkIfSoupGone();

        Direction dir = getDirToMine();
        mine(dir);

        buildDesignSchool();
        buildRefinery();
        buildFulFillmentCenter();

        checkIfNeedToRefine();
        updateSoupAndMove();

        comms.getMessages();

//        //numVaporator = 0; this shouldn't be here
//        RobotInfo [] nearbyVaporators = rc.senseNearbyRobots();
//        for (RobotInfo r : nearbyVaporators) {
//            if (r.type == RobotType.VAPORATOR) {
//                //numVaporators++; you can't update the amount of vaporators on a thread by thread basis, it has to go through comms
//                //I'm currently working on setting up the comms for this.
//            }
//        }
//        if (rc.getTeamSoup() > 500 && rc.getRoundNum() > 300 && numVaporators < 1) {
//            if (tryBuild(RobotType.VAPORATOR, Util.randomDirection())) {
//                System.out.println("Teamsoup: " + rc.getTeamSoup() + ", RoundNum: " + rc.getRoundNum() + " build a Vaporator");
//            }
//        }


    }

    private void updateSoupAndMove() throws GameActionException{

        MapLocation nearestSoupLoc = null;
        if(soupLocations.size() == 0){
            soupLocations.addAll(Arrays.asList(rc.senseNearbySoup()));
        }
        if (soupLocations.size() > 0){

            for(MapLocation soupLocation: soupLocations) {
                if(nearestSoupLoc == null || soupLocation.distanceSquaredTo(myLoc) < nearestSoupLoc.distanceSquaredTo(myLoc)){
                    nearestSoupLoc = soupLocation;
                }
                if (rc.getLocation().isWithinDistanceSquared(soupLocation, 20)) {
//                        System.out.println("I am near to some soup..");
                    if(rc.sensePollution(rc.getLocation()) == 0 && rc.senseNearbySoup() == null){
                        System.out.println("there's no more soup here!");
                        soupLocations.remove(soupLocation);
                    }
                }
            }
        }
        if(nearestSoupLoc!=null && soupLocations.contains(nearestSoupLoc)){
            nav.goTo(nearestSoupLoc);
        } else if(nav.goTo(Util.randomDirection())) {
            System.out.println("I moved randomly!");
        }
        //Todo: Update navigation so miners aren't either 1) randomly moving 2) hardcoded to move in a specified way
    }

    private void getNearestRefinery() {
        if(numRefineries > 0) {
            RobotInfo[] robots = rc.senseNearbyRobots();
            for (RobotInfo robot : robots) {
                if (robot.type == RobotType.REFINERY && robot.team == rc.getTeam()) {
                    nearestRefinery = robot.location;
                }
            }
        }
    }

    private void buildRefinery() throws GameActionException {
        if (rc.isReady() && teamSoup > RobotType.REFINERY.cost && numRefineries < 3) {
            if (!hqLoc.isWithinDistanceSquared(rc.getLocation(), 25)
                    && !nearestRefinery.isWithinDistanceSquared(rc.getLocation(), 25)
                    && tryBuild(RobotType.REFINERY, Util.randomDirection()))
                System.out.println("created a refinery");
        }
    }

    private void checkIfNeedToRefine() throws GameActionException{
        if(rc.getSoupCarrying() == RobotType.MINER.soupLimit) {
//            System.out.println("I can't mine anymore!");
            // time to go back to the HQ or find a refinery
            getNearestRefinery();
            if(nearestRefinery != null){
                if(rc.getLocation().isAdjacentTo(nearestRefinery) && tryRefine(rc.getLocation().directionTo(nearestRefinery))) {
//                    System.out.println("I refined soup!");
                }
                else{
//                    System.out.println("Heading to nearest refinery");
                    nav.goTo(nearestRefinery); //will go to HQ if no other refineries
                }
            }
        }
    }

    private void buildDesignSchool() throws GameActionException{
        if (rc.isReady() && teamSoup > RobotType.DESIGN_SCHOOL.cost && numDesignSchools < 3){
            if(!hqLoc.isWithinDistanceSquared(rc.getLocation(), 8) && tryBuild(RobotType.DESIGN_SCHOOL, Util.randomDirection()))
                System.out.println("created a design school");
        }
    }

    private void buildFulFillmentCenter() throws GameActionException{
        if (rc.isReady() && teamSoup > RobotType.FULFILLMENT_CENTER.cost && numFulFillmentCenters < 1){
            if(tryBuild(RobotType.FULFILLMENT_CENTER,Direction.EAST)){ //why are we building to the east?
                System.out.println("created a fulfillment center");
            }
        }
    }

    private Direction getDirToMine() throws GameActionException{
        MapLocation myLoc = rc.getLocation();
        for (Direction dir : Util.directions) {
            MapLocation newLoc = myLoc.add(dir);
            if (rc.canSenseLocation(newLoc) && rc.senseSoup(newLoc) > 0 && rc.canMineSoup(dir)) {
                return dir;
            }
        }
        return null;
    }

    private void mine(Direction dir) throws  GameActionException{
        if (dir != null && tryMine(dir)) {
//            System.out.println("I mined soup! " + rc.getSoupCarrying());
            MapLocation soupLoc = rc.getLocation().add(dir);
            if (!soupLocations.contains(soupLoc)) {
                comms.broadcastSoupLocation(soupLoc);
            }
        }
    }

    boolean tryMine(Direction dir) throws GameActionException {
        if (dir!= null && rc.isReady() && rc.canMineSoup(dir)) {
            rc.mineSoup(dir);
            return true;
        } else return false;
    }

    boolean tryRefine(Direction dir) throws GameActionException {
        if (rc.isReady() && rc.canDepositSoup(dir)) {
            rc.depositSoup(dir, rc.getSoupCarrying());
            return true;
        } else return false;
    }

    void checkIfSoupGone() throws GameActionException {
        if (soupLocations.size() > 0) {
            MapLocation targetSoupLoc = soupLocations.get(0);
            if (rc.canSenseLocation(targetSoupLoc)
                    && rc.senseSoup(targetSoupLoc) == 0) {
                soupLocations.remove(0);
            }
        }
    }

    private void updateRobotsCounts() throws GameActionException {

        comms.updateRobotCounts();
        numDesignSchools      = comms.numDesignSchools;
        numRefineries         = comms.numRefineries;
        numFulFillmentCenters = comms.numFulFillmentCenters;
        numNetGuns            = comms.numNetGuns;
        numVaporators         = comms.numVaporators;
        numDrones             = comms.numDrones;
        numLandscapers        = comms.numLandscapers;
        numMiners             = comms.numMiners;

        System.out.println("I'm aware of "+numDesignSchools+" design schools");

    }
}