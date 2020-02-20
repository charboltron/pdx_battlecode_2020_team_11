package master_player;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;


public class Building extends Robot {


    int myX, myY;

    MapLocation myLoc;

    public Building(RobotController r) throws GameActionException{
        super(r);
        myLoc = r.getLocation();
        if(myLoc != null){
            myX = myLoc.x; myY = myLoc.y;
        }
        // building specific setup here
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        comms.broadcastRobotCreation(rc.getLocation(), rc.getType(), rc.getTeam(), 3); //only happens on creation
        //Todo Figure out how much we should bid on each building for each round
    }
}