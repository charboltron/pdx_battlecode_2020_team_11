package chaz_bot1;
import battlecode.common.*;

import java.util.ArrayList;

public class Miner extends Unit {

    int numDesignSchools = 0;
    int numRefineries = 0;

    MapLocation nearestRefinery = null;

    ArrayList<MapLocation> soupLocations = new ArrayList<MapLocation>();

    public Miner(RobotController r) {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        numDesignSchools += comms.getNewDesignSchoolCount();
        numRefineries    += comms.getNewRefineryCount();
        System.out.println("Number of Refineries = "+numRefineries);
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

        if (numRefineries < 3){
            if(!hqLoc.isWithinDistanceSquared(rc.getLocation(), 30) && tryBuild(RobotType.REFINERY, Util.randomDirection()))
                System.out.println("created a refinery");
        }

        else if (numDesignSchools < 3){
            if(tryBuild(RobotType.DESIGN_SCHOOL, Util.randomDirection()))
                System.out.println("created a design school");
        }

        if (rc.getSoupCarrying() == RobotType.MINER.soupLimit) {
            // time to go back to the HQ or find a refinery
            if(numRefineries > 0){
                if(nearestRefinery != null && rc.getLocation().isAdjacentTo(nearestRefinery)){
                    if(tryRefine(rc.getLocation().directionTo(nearestRefinery))){
                        System.out.println("=========================refined at actual refinery");
                    }

                } else {
                    RobotInfo[] robots = rc.senseNearbyRobots();
                    for (RobotInfo robot : robots) {
                        if (robot.type == RobotType.REFINERY && robot.team == rc.getTeam()) {
                            nearestRefinery = robot.location;
                        }
                    }
                }
            }
            if(nav.goTo(hqLoc))
                System.out.println("moved towards HQ");
        } else if (soupLocations.size() > 0) {
            nav.goTo(soupLocations.get(0));
            rc.setIndicatorLine(rc.getLocation(), soupLocations.get(0), 255, 255, 0);
        } else if (nav.goTo(Util.randomDirection())) {
            // otherwise, move randomly as usual
            System.out.println("I moved randomly!");
        }
    }

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
