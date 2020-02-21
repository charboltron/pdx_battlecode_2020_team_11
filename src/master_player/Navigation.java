package master_player;

import battlecode.common.*;

public class Navigation {
    RobotController rc;

    MapLocation [] lastThreeSpots;

    // state related only to navigation should go here

    public Navigation(RobotController r) {
        rc = r;
        lastThreeSpots = new MapLocation[3];
    }
    
    /**
     * Attempts to move in a given direction.
     *
     * @param dir The intended direction of movement
     * @return true if a move was performed
     * @throws GameActionException
     */
    boolean tryMove(Direction dir) throws GameActionException {
        if (rc.isReady() && rc.canMove(dir) && !rc.senseFlooding(rc.getLocation().add(dir))) {
            rc.move(dir);
            return true;
        } else return false;
    }

    boolean droneMove(Direction dir) throws GameActionException {
        Direction[] toTry =
                {
                        dir,
                        dir.rotateRight(),
                        dir.rotateRight().rotateRight(),
                        dir.rotateLeft(),
                        dir.rotateLeft().rotateLeft(),

                };

        for (Direction d : toTry) {
            if (rc.isReady() && rc.canMove(d)) {
                System.out.println("moving "+d);
                rc.move(d);
                return true;
            }
        }
        return false;
    }


    boolean firstDroneMove(Direction dir) throws GameActionException {
        Direction[] toTry =
                {
                        dir,
                        dir.rotateRight(),
                        dir.rotateRight().rotateRight(),
                        dir.rotateRight().rotateRight().rotateRight(),
                        dir.opposite().rotateRight(),
                        dir.opposite().rotateRight().rotateRight(),
                        dir.opposite().rotateRight().rotateRight().rotateRight()

                };

        for (Direction d : toTry) {
            System.out.println("first drone trying to move: "+d);
            if (rc.isReady() && rc.canMove(d)) {
                System.out.println("moving "+d);
                rc.move(d);
                return true;
            }
        }
        return false;
    }

    // tries to move in the general direction of dir
    boolean goTo(Direction dir) throws GameActionException {
        Direction[] toTry = {dir, dir.rotateLeft(), dir.rotateRight(), dir.rotateLeft().rotateLeft(), dir.rotateRight().rotateRight()};
        for (Direction d : toTry){
            if(tryMove(d))
                return true;
        }
        return false;
    }

    // navigate towards a particular location
    boolean goTo(MapLocation destination) throws GameActionException {
        return goTo(rc.getLocation().directionTo(destination));
    }

    boolean stuckInPosition(){
        return lastThreeSpots[0] == lastThreeSpots[2] || lastThreeSpots [1] == lastThreeSpots [0] || lastThreeSpots[2] == lastThreeSpots[1];
    }
}