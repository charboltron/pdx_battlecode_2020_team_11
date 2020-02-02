package master_player;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

public class Drone extends Unit {

    MapLocation enemyHQ;

    public Drone(RobotController r) {
        super(r);
        nav = new Navigation(rc);
    }

    public void takeTurn() throws GameActionException{
        super.takeTurn();

        //SOWMY This is what you commented out on the most recent version of the Drone class

        /* Team enemy = rc.getTeam().opponent();
        RobotInfo[] enemiesInRange = rc.senseNearbyRobots(GameConstants.NET_GUN_SHOOT_RADIUS_SQUARED, enemy);

        rc.move(rc.getLocation().directionTo(new MapLocation(7, 7)));
        rc.pickUpUnit(0);
        System.out.println("I picked up"+ enemiesInRange[0].getID());*/

        /*if (enemiesInRange.length > 0) {
            // Pick up a first robot within range
            rc.pickUpUnit(enemiesInRange[0].getID());
            System.out.println("I picked up " + enemiesInRange[0].getID() + "!");
        }*/
    }


    //HEY SOWMY, this is code that I think you might have written? You can delete it if you don't need it I just
    //wasn't sure whether this was useful.


//        if (!rc.isCurrentlyHoldingUnit()) {
//            // See if there are any enemy robots within capturing range
//            RobotInfo[] robots = rc.senseNearbyRobots(GameConstants.DELIVERY_DRONE_PICKUP_RADIUS_SQUARED, enemy);
//
//            if (robots.length > 0) {
//                // Pick up a first robot within range
//                rc.pickUpUnit(robots[0].getID());
//                System.out.println("I picked up " + robots[0].getID() + "!");
//            }
//        } else {
//            // No close robots, so search for robots within sight radius
//            nav.goTo(Util.randomDirection());
//        }
//    }

    public void reportEnemyHQ() throws GameActionException {

        //I wrote this method for when/if we have a way to report the enemyHQ location -chaz

    }
}
