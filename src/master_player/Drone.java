package master_player;

import battlecode.common.*;

public class Drone extends Unit {

    MapLocation enemyHQ;
    boolean firstDrone;
    int roundCreated;
    boolean checkedIfFirst;
    int droneCount = 0;

    public Drone(RobotController r) {
        super(r);
        nav = new Navigation(rc);
        firstDrone = false;
        roundCreated = rc.getRoundNum();
        checkedIfFirst = false;

    }

    public void takeTurn() throws GameActionException{
        super.takeTurn();

        comms.broadcastRobotCreation(myLoc, rc.getType(), rc.getTeam(), 0); //only happens on creation
        if(comms.onlyOneMessageToRead()) {comms.getMessages();}

        if(!checkedIfFirst){ checkIfFirstDrone();}


        Team enemy = rc.getTeam().opponent();
        RobotInfo[] enemiesInRange = rc.senseNearbyRobots(GameConstants.NET_GUN_SHOOT_RADIUS_SQUARED, enemy);

        /*rc.move(Util.randomDirection());*/
//        rc.move(rc.getLocation().directionTo(new MapLocation(33, 29)));


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
        comms.getMessages();
        comms.updateRobotCounts();
    }

    private void checkIfFirstDrone() throws GameActionException {
        if(rc.isReady()){

            System.out.println(comms.numDrones);
            if(comms.numDrones == 1) {
                System.out.println("I'm the first drone!");
            }else{
                System.out.println("I'm not the first drone!");
            }
            checkedIfFirst = true;
        }
    }

    public void reportEnemyHQ() throws GameActionException {

        //I wrote this method for when/if we have a way to report the enemyHQ location -chaz

    }
}