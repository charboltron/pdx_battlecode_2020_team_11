package master_player;
import battlecode.common.*;


public class Unit extends Robot {

    Navigation nav;
    MapLocation hqLoc;
    int myX, myY;
    MapLocation myLoc;


    public Unit(RobotController r) {
        super(r);
        nav = new Navigation(rc);
        myLoc = r.getLocation();
        if (myLoc != null) {
            myX = myLoc.x;
            myY = myLoc.y;
        }
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        if (hqLoc == null) {
            findHQ();
        }
        myLoc = rc.getLocation();
        myX = myLoc.x;
        myY = myLoc.y;
    }

    public void findHQ() throws GameActionException {
        System.out.println("I\'m unit number: " + rc.getID());
        // search surroundings for HQ
        RobotInfo[] robots = rc.senseNearbyRobots();
        for (RobotInfo robot : robots) {
            if (robot.type == RobotType.HQ && robot.team == rc.getTeam()) {
                hqLoc = robot.location;
                System.out.println("Cool, found HQ!");
            }
        }
        if (hqLoc == null) {
            // if still null, search the blockchain
            hqLoc = comms.getHqLocFromBlockchain();
            if (hqLoc == null) { //if something wrong
                System.out.println("I can't find HQ!");
            } else {
                System.out.println("Cool, found HQ!");
            }
        }
    }
}