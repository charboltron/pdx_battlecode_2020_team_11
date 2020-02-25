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
//        if(nav.stuckInPosition()){nav.goTo((Util.randomDirection()));}

        myLoc = rc.getLocation();
        comms.broadcastRobotCreation(myLoc, rc.getType(), rc.getTeam(), 0); //only happens on creation
        if(comms.onlyOneMessageToRead()) {comms.getMessages();}

        if(!checkedIfFirst){checkIfFirstDrone();}

        scoutSoup();
        firstDroneCircleHQ();
        if(!firstDrone){
            otherDroneActions();
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

    private void scoutSoup() throws GameActionException {
        if(!rc.isReady()){return;}
        for (Direction dir : Util.directions) {
            MapLocation newLoc = myLoc.add(dir);
            if (rc.canSenseLocation(newLoc) && rc.senseSoup(newLoc) > 0) {
                System.out.println("I spot soup: " + dir);
                comms.broadcastSoupLocation(newLoc);
            }
        }
    }

    private void otherDroneActions() throws GameActionException {
        if(!rc.isReady() || firstDrone) return;
            //Todo: make movement for other drones.
        nav.droneMove(Util.randomDirection());


    }

    private void checkIfFirstDrone() throws GameActionException {
        if(rc.isReady()){

            System.out.println(comms.numDrones);
            if(comms.sentinelDrone == rc.getID()){
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
                nav.firstDroneMove(directionToHQ);
            }else if(distanceToHq < 15){
                nav.firstDroneMove(directionToHQ.opposite());
            }
            else {
                switch (directionToHQ) {
                    case SOUTH:
                        if(distanceToHq < 20){
                            nav.firstDroneMove(Direction.NORTHWEST);
                        }
                        nav.firstDroneMove(Direction.WEST);
                    case SOUTHEAST:
                        if(distanceToHq < 20){
                            nav.firstDroneMove(Direction.WEST);
                        }
                        else {
                            nav.firstDroneMove(Direction.SOUTH);
                        }
                    case EAST:
                        if(distanceToHq < 20){
                            nav.firstDroneMove(Direction.SOUTHWEST);
                        }
                        else {
                            nav.firstDroneMove(Direction.SOUTH);
                        }
                    case NORTHEAST:
                        if(distanceToHq < 20){
                            nav.firstDroneMove(Direction.SOUTH);
                        }
                        else {
                            nav.firstDroneMove(Direction.EAST);
                        }
                    case NORTH:
                        if(distanceToHq < 20){
                            nav.firstDroneMove(Direction.SOUTHEAST);
                        }
                        else {
                            nav.firstDroneMove(Direction.EAST);
                        }
                    case NORTHWEST:
                        if(distanceToHq < 20){
                            nav.firstDroneMove(Direction.EAST);
                        }
                        else {
                            nav.firstDroneMove(Direction.NORTH);
                        }
                    case WEST:
                        if(distanceToHq < 20){
                            nav.firstDroneMove(Direction.NORTHEAST);
                        }
                        else {
                            nav.firstDroneMove(Direction.NORTH);
                        }
                    case SOUTHWEST:
                        if(distanceToHq < 20){
                            nav.firstDroneMove(Direction.NORTH);
                        }
                        else {
                            nav.firstDroneMove(Direction.WEST);
                        }
                }
            }
        }
    }
}