package team11newbot;

import battlecode.common.*;

public class Drone extends Unit {
    public Drone(RobotController r) { super(r);}

    public void takeTurn() throws GameActionException{
        super.takeTurn();

        Team enemy = rc.getTeam().opponent();
        RobotInfo[] enemiesInRange = rc.senseNearbyRobots(GameConstants.NET_GUN_SHOOT_RADIUS_SQUARED, enemy);

        rc.move(rc.getLocation().directionTo(new MapLocation(7, 7)));
        rc.pickUpUnit(0);
        System.out.println("I picked up"+ enemiesInRange[0].getID());

        if (enemiesInRange.length > 0) {
            // Pick up a first robot within range
            rc.pickUpUnit(enemiesInRange[0].getID());
            System.out.println("I picked up " + enemiesInRange[0].getID() + "!");
        }
    }

}
