package master_player;

import battlecode.common.*;

import java.util.WeakHashMap;

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

        int r = rc.getRoundNum();
        nav.lastThreeSpots[r%3] = myLoc;

        myLoc = rc.getLocation();
        comms.broadcastRobotCreation(myLoc, rc.getType(), rc.getTeam(), 0); //only happens on creation
        if(comms.onlyOneMessageToRead()) {comms.getMessages();}

        if(!checkedIfFirst){ checkIfFirstDrone();}
        firstDroneCircleHQ();
        if(!firstDrone){
            nav.goTo(Util.randomDirection());
        }


//        Team enemy = rc.getTeam().opponent();
//        RobotInfo[] enemiesInRange = rc.senseNearbyRobots(GameConstants.NET_GUN_SHOOT_RADIUS_SQUARED, enemy);


        /*rc.move(Util.randomDirection());*/
//        rc.move(rc.getLocation().directionTo(new MapLocation(33, 29)));


//        System.out.println("I picked up"+ enemiesInRange[0].getID());
//
//
//        if (enemiesInRange.length > 0) {
//            // Pick up a first robot within range
//            rc.pickUpUnit(enemiesInRange[0].getID());
//            System.out.println("I picked up " + enemiesInRange[0].getID() + "!");
//        }
//
//
//
//        if (!rc.isCurrentlyHoldingUnit()) {
//           // See if there are any enemy robots within capturing range
//            RobotInfo[] robots = rc.senseNearbyRobots(GameConstants.DELIVERY_DRONE_PICKUP_RADIUS_SQUARED, enemy);
//            if (robots.length > 0) {
//                // Pick up a first robot within range
//                rc.pickUpUnit(robots[0].getID());
//                System.out.println("I picked up " + robots[0].getID() + "!");
//            }
//        } else {
//            // No close robots, so search for robots within sight radius
//            nav.goTo(Util.randomDirection());
//        }

        comms.getMessages();
        comms.updateRobotCounts();
    }

    private void checkIfFirstDrone() throws GameActionException {
        if(rc.isReady()){

            System.out.println(comms.numDrones);
            if(comms.numDrones == 1) {
                System.out.println("I'm the first drone!");
                firstDrone = true;
            }else{
                System.out.println("I'm not the first drone!");
            }
            checkedIfFirst = true;
        }
    }

    public void reportEnemyHQ() throws GameActionException {

        //I wrote this method for when/if we have a way to report the enemyHQ location -chaz

    }

    public void firstDroneCircleHQ() throws GameActionException {
        if(firstDrone && rc.isReady()) {
            int distanceToHq = myLoc.distanceSquaredTo(hqLoc);
            Direction directionToHQ = myLoc.directionTo(hqLoc);
            System.out.println("D to HQ: "+directionToHQ);
            if(distanceToHq > 30){
                nav.droneMove(directionToHQ);
            }else if(distanceToHq < 15){
                nav.droneMove(directionToHQ.opposite());
            }
            else {
                switch (directionToHQ) {
                    case SOUTH:
                        if(distanceToHq < 25){
                            nav.droneMove(Direction.NORTHWEST);
                        }
                        nav.droneMove(Direction.WEST);
                    case SOUTHEAST:
                        if(distanceToHq < 25){
                            nav.droneMove(Direction.WEST);
                        }
                        else {
                            nav.droneMove(Direction.SOUTH);
                        }
                    case EAST:
                        if(distanceToHq < 25){
                            nav.droneMove(Direction.SOUTHWEST);
                        }
                        else {
                            nav.droneMove(Direction.SOUTH);
                        }
                    case NORTHEAST:
                        if(distanceToHq < 25){
                            nav.droneMove(Direction.SOUTH);
                        }
                        else {
                            nav.droneMove(Direction.EAST);
                        }
                    case NORTH:
                        if(distanceToHq < 25){
                            nav.droneMove(Direction.SOUTHEAST);
                        }
                        else {
                            nav.droneMove(Direction.EAST);
                        }
                    case NORTHWEST:
                        if(distanceToHq < 25){
                            nav.droneMove(Direction.EAST);
                        }
                        else {
                            nav.droneMove(Direction.NORTH);
                        }
                    case WEST:
                        if(distanceToHq < 25){
                            nav.droneMove(Direction.NORTHEAST);
                        }
                        else {
                            nav.droneMove(Direction.NORTH);
                        }
                    case SOUTHWEST:
                        if(distanceToHq < 25){
                            nav.droneMove(Direction.NORTH);
                        }
                        else {
                            nav.droneMove(Direction.WEST);
                        }
                }
            }
        }

    }
}