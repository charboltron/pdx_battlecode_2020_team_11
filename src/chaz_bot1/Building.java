package chaz_bot1;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotType;

public class Building extends Robot {

    public Building(RobotController r) {
        super(r);
        // building specific setup here
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        comms.broadcastBuildingCreation(rc.getLocation(), rc.getType(), rc.getTeam(), 0); //only happens on creation

    }
}