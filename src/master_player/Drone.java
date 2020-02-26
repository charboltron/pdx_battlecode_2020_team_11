package master_player;

import battlecode.common.*;

import java.util.WeakHashMap;

public class Drone extends Unit {

    MapLocation enemyHQ;
    boolean firstDrone;
    int roundCreated;
    boolean checkedIfFirst;
    int droneCount = 0;
    boolean cow_present = false;

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
        comms.getMessages();
        comms.updateRobotCounts();
    }

    public void scoutSoup() throws GameActionException {
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
        if(!rc.isReady()) return;
        //Todo: make movement for other drones.
        if (!cow_present&&isCowAround()) {
            pickUpCow();
        }
        rc.move(rc.getLocation().directionTo(new MapLocation(33, 33)));

    }
    public boolean isCowAround() throws GameActionException {
        RobotInfo[] robots = rc.senseNearbyRobots(rc.getCurrentSensorRadiusSquared());
        for (RobotInfo e : robots) {
            if (e.type == RobotType.COW) {
                //Found cow
                return true;
            }
        }
        return false;
    }

    public boolean pickUpCow() throws GameActionException {
        //find cow
        MapLocation cowloc=null;
        int cow_id=-1;
        RobotInfo[] robots = rc.senseNearbyRobots(rc.getCurrentSensorRadiusSquared());
        for (RobotInfo e : robots) {
            if (e.type == RobotType.COW) {
                cow_id=e.getID();
                cowloc = new MapLocation(e.getLocation().x, e.getLocation().y);
                break;
            }
        }

        if(cow_id>=0) {
            if (rc.getLocation().distanceSquaredTo(cowloc) <= 2) {
                //pick up cow
                if (rc.canPickUpUnit(cow_id)) {
                    rc.pickUpUnit(cow_id);
                    cow_present = true;
                    return true;
                }
            } else {
                rc.move(Util.randomDirection());
                return false;
            }
        }
        return false;

    }


    public boolean checkIfFirstDrone() throws GameActionException {
        if(rc.isReady()){

            System.out.println(comms.numDrones);
            if(comms.sentinelDrone == rc.getID()){
                System.out.println("I'm the first drone!");
                firstDrone = true;
            }else{
                System.out.println("I'm not the first drone!");
            }
            checkedIfFirst = true;
            return true;
        }
        return false;
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