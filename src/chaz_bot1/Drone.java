package chaz_bot1;

import battlecode.common.*;

public class Drone extends Unit {

    MapLocation enemyHQ;

    public Drone(RobotController r) {
        super(r);
        nav = new Navigation(rc);
    }

    public void takeTurn() throws GameActionException{
        super.takeTurn();

        nav.goTo(Util.directions[8]);
        Team enemy = rc.getTeam().opponent();
        if (!rc.isCurrentlyHoldingUnit()) {
            // See if there are any enemy robots within capturing range
            RobotInfo[] robots = rc.senseNearbyRobots(GameConstants.DELIVERY_DRONE_PICKUP_RADIUS_SQUARED, enemy);

            if (robots.length > 0) {
                // Pick up a first robot within range
                rc.pickUpUnit(robots[0].getID());
                System.out.println("I picked up " + robots[0].getID() + "!");
            }
        } else {
            // No close robots, so search for robots within sight radius
            nav.goTo(Util.randomDirection());
        }
    }

    public void reportEnemyHQ() throws GameActionException {


    }
}
