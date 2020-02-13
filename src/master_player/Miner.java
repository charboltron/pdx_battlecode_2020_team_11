package master_player;
import battlecode.common.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Miner extends Unit {

    int numDesignSchools = 0;
    int numRefineries = 0;
    int numFulFillmentCenters = 0;
    int numVaporators = 0;

    MapLocation nearestRefinery = null;

    ArrayList<MapLocation> soupLocations = new ArrayList<MapLocation>();

    public Miner(RobotController r) {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        numDesignSchools      += comms.getBuildingCount(RobotType.DESIGN_SCHOOL,        rc.getTeam());
        numRefineries         += comms.getBuildingCount(RobotType.REFINERY,             rc.getTeam());
        numFulFillmentCenters += comms.getBuildingCount(RobotType.FULFILLMENT_CENTER,   rc.getTeam());
        numVaporators         += comms.getBuildingCount(RobotType.VAPORATOR,            rc.getTeam());
        comms.updateSoupLocations(soupLocations);
        checkIfSoupGone();

        for (Direction dir : Util.directions)
            if (tryMine(dir)) {
                System.out.println("I mined soup! " + rc.getSoupCarrying());
                MapLocation soupLoc = rc.getLocation().add(dir);
                if (!soupLocations.contains(soupLoc)) {
                    comms.broadcastSoupLocation(soupLoc);
                }
            }
        // mine first, then when full, deposit
        for (Direction dir : Util.directions)
            if (tryRefine(dir))
                System.out.println("I refined soup! " + rc.getTeamSoup());

        if (numDesignSchools < 1 && rc.getRoundNum() > 100){
            if(!hqLoc.isWithinDistanceSquared(rc.getLocation(), 8) && tryBuild(RobotType.DESIGN_SCHOOL, Util.randomDirection()))
                System.out.println("created a design school");
        }

        /*if (rc.getTeamSoup() > 500 && rc.getRoundNum() > 300&& numVaporators < 1) {
            if (rc.getLocation().distanceSquaredTo(hqLoc) > 8) {
                if (tryBuild(RobotType.VAPORATOR, Util.randomDirection())) {
                    System.out.println("Teamsoup: " + rc.getTeamSoup() + ", RoundNum: " + rc.getRoundNum() + " build a Vaporator");
                }
            }
        }*/
      
        if (numFulFillmentCenters < 1){

            if(tryBuild(RobotType.FULFILLMENT_CENTER, Util.randomDirection())){
                System.out.println("created a fulfillment center");
                numFulFillmentCenters++;

            }
        }

        // if miner carrying maximum amount of soup
        if (rc.getSoupCarrying() == RobotType.MINER.soupLimit) {
            // time to go back to the HQ or find a refinery
            // if refineries = 0
            if(numRefineries > 0){
                // if there is a nearby refinary, and adjacent to robot, try refine
                if(nearestRefinery != null){
                    if(rc.getLocation().isAdjacentTo(nearestRefinery)){
                        if (tryRefine(rc.getLocation().directionTo(nearestRefinery))) {
                            System.out.println("refined at actual refinery");
                        }
                    }
                    else{
                        nav.goTo(nearestRefinery);
                    }
                // if there is not a nearby refinary, or refinery adjacent to robot, try to sense for a nearby refinery
                } else {
                    RobotInfo[] robots = rc.senseNearbyRobots();
                    for (RobotInfo robot : robots) {
                        if (robot.type == RobotType.REFINERY && robot.team == rc.getTeam()) {
                            nearestRefinery = robot.location;
                        }
                    }
                }
            }
            // if we have no refineries, go to HQ
            if(nav.goTo(hqLoc))
                System.out.println("moved towards HQ");
        // if there is a soup location, go to it
        } else if (soupLocations.size() > 0) {
            nav.goTo(soupLocations.get(0));
            rc.setIndicatorLine(rc.getLocation(), soupLocations.get(0), 255, 255, 0);
        // if there is no soup locations, try sensing for it. If cannot find any soup locations nearby, move around
        } else {
            if(soupLocations.size() == 0){
            soupLocations.addAll(Arrays.asList(rc.senseNearbySoup()));
            }
            if (soupLocations.size() > 0){
                if (numRefineries < 3){
                    if(!hqLoc.isWithinDistanceSquared(rc.getLocation(), 15))
                        if(tryBuild(RobotType.REFINERY, Util.randomDirection())) {
                            System.out.println("created a refinery");
                        }
                }
            } else {
//                if(nav.goTo(Util.randomDirection())) {
//                    System.out.println("I moved randomly!");
//                }
                if(rc.canMove(Direction.SOUTHEAST)){
                    nav.goTo(Direction.SOUTHEAST);
                } else if(rc.canMove(Direction.SOUTHWEST)){
                    nav.goTo(Direction.SOUTHWEST);
                } else if (rc.canMove(Direction.NORTHWEST)){
                    nav.goTo(Direction.NORTHWEST);
                } else if (rc.canMove(Direction.NORTHEAST)){
                    nav.goTo(Direction.NORTHEAST);
                }

            }
        }
    }

//    boolean checkNearByRobotType(RobotType type){
//        RobotInfo [] nearbyRobots= rc.senseNearbyRobots();
//        for (RobotInfo r : nearbyRobots) {
//            if (r.type == type) {
//                return true;
//            }
//        }
//        return false;
//    }


    /**
     * Attempts to mine soup in a given direction.
     *
     * @param dir The intended direction of mining
     * @return true if a move was performed
     * @throws GameActionException
     */
    boolean tryMine(Direction dir) throws GameActionException {
        if (rc.isReady() && rc.canMineSoup(dir)) {
            rc.mineSoup(dir);
            return true;
        } else return false;
    }

    /**
     * Attempts to refine soup in a given direction.
     *
     * @param dir The intended direction of refining
     * @return true if a move was performed
     * @throws GameActionException
     */
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
}
