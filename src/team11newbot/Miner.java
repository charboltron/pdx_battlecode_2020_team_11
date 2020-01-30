package team11newbot;
import battlecode.common.*;

import java.util.ArrayList;

public class Miner extends Unit {

    int numDesignSchools = 0;
    int numFCenter =0;
    int numVaporator =0;
    ArrayList<MapLocation> soupLocations = new ArrayList<MapLocation>();

    public Miner(RobotController r) {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        numDesignSchools += comms.getNewDesignSchoolCount();
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


        /*if (numDesignSchools < 3){
            if(tryBuild(RobotType.DESIGN_SCHOOL, Util.randomDirection()))
                System.out.println("created a design school");
                numDesignSchools++;
        }*/


        if (numDesignSchools < 2) {
            if (tryBuild(RobotType.DESIGN_SCHOOL, Util.randomDirection())) {
                System.out.println("build a Design School");
            }
         else{}
        }

        numVaporator = 0;
        RobotInfo [] nearbyVaporators = rc.senseNearbyRobots();
        for (RobotInfo r : nearbyVaporators) {
            if (r.type == RobotType.VAPORATOR) {
                numVaporator++;
            }
        }
        if (rc.getTeamSoup() > 500 && rc.getRoundNum() > 300 && numVaporator < 1) {
            if (tryBuild(RobotType.VAPORATOR, Util.randomDirection())) {
                System.out.println("Teamsoup: " + rc.getTeamSoup() + ", RoundNum: " + rc.getRoundNum() + " build a Vaporator");
            }
        }
        if (numFCenter < 1){
            if(tryBuild(RobotType.FULFILLMENT_CENTER,Direction.EAST)){
                System.out.println("made fulfiilment center");
                numFCenter++;
            }
        }

        if (rc.getSoupCarrying() == RobotType.MINER.soupLimit) {
            // time to go back to the HQ
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
