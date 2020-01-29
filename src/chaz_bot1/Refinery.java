package chaz_bot1;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotType;

public class Refinery extends Building {
    public Refinery(RobotController r) throws GameActionException {
        super(r);
        // will only actually happen if we haven't already broadcasted the creation
        comms.broadcastRefineryCreation(rc.getLocation());

    }
    public void takeTurn() throws GameActionException {
        super.takeTurn();



        }

}

