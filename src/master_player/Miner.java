package master_player;
import battlecode.common.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

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
    MapLocation nearestSoup     = null;

    ArrayList<MapLocation> soupLocations;

    public Miner(RobotController r) {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        nav.lastThreeSpots[rc.getRoundNum()%3] = myLoc;

        if(comms.onlyOneMessageToRead()) {comms.getMessages();}
        updateRobotsCounts();

        if(nearestRefinery == null && hqLoc != null){
            nearestRefinery = hqLoc;
        }

        teamSoup = rc.getTeamSoup();
        soupLocations = comms.soupLocations;
        checkIfSoupGone();

        Direction dir = getDirToMine();
        tryMine(dir);

        buildDesignSchool();
        //buildRefinery();
        buildFulFillmentCenter();
        buildVaporator();
        checkIfNeedToRefine();
        updateSoupAndMove();
        buildNetgun();

        comms.getMessages();
    }

    public boolean buildVaporator() throws GameActionException {
        if (numLandscapers > 5 && rc.getTeamSoup() > 500 && rc.getRoundNum() > 300 && numVaporators < 3) {
            if (tryBuild(RobotType.VAPORATOR, Util.randomDirection())) {
                System.out.println("Teamsoup: " + rc.getTeamSoup() + ", RoundNum: " + rc.getRoundNum() + " build a Vaporator");
                return true;
            }
        }
        return false;
    }

//        RobotInfo [] nearbyVaporators = rc.senseNearbyRobots();
//        for (RobotInfo r : nearbyVaporators) {
//            if (r.type == RobotType.VAPORATOR) {
//            }
//        }

    public void getNearestRefinery() {
        if(numRefineries > 0) {
            RobotInfo[] robots = rc.senseNearbyRobots();
            for (RobotInfo robot : robots) {
                if (robot.type == RobotType.REFINERY && robot.team == rc.getTeam()) {
                    nearestRefinery = robot.location;
                }
            }
        }
    }

    public boolean buildRefinery() throws GameActionException {
        if (rc.isReady() && teamSoup > RobotType.REFINERY.cost && numRefineries < 1) {
            if (!hqLoc.isWithinDistanceSquared(rc.getLocation(), 30)
                    && !nearestRefinery.isWithinDistanceSquared(rc.getLocation(), 25)
                    && tryBuild(RobotType.REFINERY, Util.randomDirection())) {
                System.out.println("created a refinery");
                return true;
            }
        }
        return false;
    }

    private void checkIfNeedToRefine() throws GameActionException{
        if(rc.getSoupCarrying() == RobotType.MINER.soupLimit) {
            System.out.println("I can't mine anymore!");
            // time to go back to the HQ or find a refinery
            getNearestRefinery();
            if(nearestRefinery != null){
                if(rc.getLocation().isAdjacentTo(nearestRefinery) && tryRefine(rc.getLocation().directionTo(nearestRefinery))) {
                    System.out.println("I refined soup!");
                }
                else{
                    if(nav.goTo(nearestRefinery)){
                        System.out.println("Heading to nearest refinery");
                    }
                }//Todo: Force Miners to Build Refinery if Wall around HQ
            }
        }
    }

    public boolean buildDesignSchool() throws GameActionException{
        if (rc.getRoundNum() > 200 && rc.isReady() && teamSoup > RobotType.DESIGN_SCHOOL.cost && numDesignSchools < 3){
            if(!hqLoc.isWithinDistanceSquared(rc.getLocation(), 8) && tryBuild(RobotType.DESIGN_SCHOOL, Util.randomDirection())) {
                System.out.println("created a design school");
                return true;
            }
        }
        return false;
    }

    public boolean buildFulFillmentCenter() throws GameActionException{
        if (rc.getRoundNum() > 300 && rc.isReady() && numDesignSchools > 1 && teamSoup > RobotType.FULFILLMENT_CENTER.cost && numFulFillmentCenters < 1){
            Direction directionToHQ = myLoc.directionTo(hqLoc);
            if(tryBuild(RobotType.FULFILLMENT_CENTER, directionToHQ.opposite())){
            System.out.println("created a fulfillment center");
            return true;
            }
        }
        return false;
    }

    public boolean buildNetgun() throws GameActionException {
        if (rc.isReady() && numNetGuns < 2 && teamSoup > RobotType.NET_GUN.cost) {
            RobotInfo[] robots = rc.senseNearbyRobots(24);
            for (RobotInfo robot : robots) {
                if (robot.type == RobotType.REFINERY && robot.team == rc.getTeam()) {
                    if(tryBuild(RobotType.NET_GUN, Util.randomDirection())) {
                        System.out.println("created a net gun near Refinery");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Direction getDirToMine() throws GameActionException {
        MapLocation myLoc = rc.getLocation();
        for (Direction dir : Util.directions) {
            MapLocation newLoc = myLoc.add(dir);
            if (rc.canSenseLocation(newLoc) && rc.senseSoup(newLoc) > 0 && rc.canMineSoup(dir)) {
                System.out.println("I should mine to the " + dir);
                if (!soupLocations.contains(newLoc)) {
                    comms.broadcastSoupLocation(newLoc);
                }
                return dir;
            }
        }
        return null;
    }

    boolean tryMine(Direction dir) throws GameActionException {
        if (dir != null && rc.isReady()) {
            rc.mineSoup(dir);
            System.out.println("I mined soup! " + rc.getSoupCarrying());
            return true;
        }
        if(dir == null){
            System.out.println("no direction to mine!");
        }else{ System.out.println("I'm not ready yet or can't mine.");}
        return false;
    }

    void updateSoupAndMove() throws GameActionException{

        if(!rc.isReady()){return;}
        if(soupLocations.size() == 0){
            soupLocations.addAll(Arrays.asList(rc.senseNearbySoup()));
        }
        if (soupLocations.size() > 0){
            buildRefinery();
            for(MapLocation soupLocation: soupLocations) {
                if(nearestSoup == null || soupLocation.distanceSquaredTo(myLoc) < nearestSoup.distanceSquaredTo(myLoc)){
                    nearestSoup = soupLocation;
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
        if(nav.stuckInPosition()) {
            nav.goTo(Util.randomDirection());
        }
        if(nearestSoup!=null && soupLocations.contains(nearestSoup)) {
            if (nav.goTo(nearestSoup)) {
                System.out.println("heading toward nearest soup " + nearestSoup);
            } else if (nav.goTo(myLoc.directionTo(nearestSoup).opposite())) {
                System.out.println("I'm trying to move around!");
            } else if (nav.goTo(Util.randomDirection())) {
                System.out.println("I moved randomly!");
            }
        }
        //Todo: Update navigation so miners aren't either 1) randomly moving 2) hardcoded to move in a specified way
    }


    boolean tryRefine(Direction dir) throws GameActionException {
        if (rc.isReady() && rc.canDepositSoup(dir)) {
            rc.depositSoup(dir, rc.getSoupCarrying());
            return true;
        } else return false;
    }

    void checkIfSoupGone() throws GameActionException {
        if (soupLocations.size() > 0 && soupLocations.contains(nearestSoup)) {
            if (rc.canSenseLocation(nearestSoup)
                    && rc.senseSoup(nearestSoup) == 0) {
                soupLocations.remove(nearestSoup);
                System.out.println("The soup is no longer here!");
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

        System.out.println("num refineries "+numRefineries);

    }
}