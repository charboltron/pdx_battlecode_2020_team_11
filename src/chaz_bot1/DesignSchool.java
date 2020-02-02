package chaz_bot1;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotType;

public class DesignSchool extends Building {
    public DesignSchool(RobotController r) {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        
        // will only actually happen if we haven't already broadcasted the creation
        //        comms.broadcastBuildingCreation(rc.getLocation(), RobotType.DESIGN_SCHOOL, rc.getTeam(), 0);

        //moved comms to building class leaving here for now to see if it works as intended


        for (Direction dir : Util.directions) {
            if(tryBuild(RobotType.LANDSCAPER, dir)) {
                System.out.println("made a landscaper");
            }
        }
    }
}
