package master_player;

import battlecode.common.*;

public class Drone extends Unit {

    MapLocation enemyHQ;

    public Drone(RobotController r) {
        super(r);
        nav = new Navigation(rc);
    }

    public void takeTurn() throws GameActionException{
        super.takeTurn();



        Team enemy = rc.getTeam().opponent();
        RobotInfo[] enemiesInRange = rc.senseNearbyRobots(GameConstants.NET_GUN_SHOOT_RADIUS_SQUARED, enemy);

        /*rc.move(Util.randomDirection());*/
        rc.move(rc.getLocation().directionTo(new MapLocation(5, 11)));


        /*System.out.println("I picked up"+ enemiesInRange[0].getID());*/


        /*if (enemiesInRange.length > 0) {
            // Pick up a first robot within range
            rc.pickUpUnit(enemiesInRange[0].getID());
            System.out.println("I picked up " + enemiesInRange[0].getID() + "!");
        }*/



        /*if (!rc.isCurrentlyHoldingUnit()) {
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
        }*/
    }

    public void reportEnemyHQ() throws GameActionException {

        //I wrote this method for when/if we have a way to report the enemyHQ location -chaz

    }
}
