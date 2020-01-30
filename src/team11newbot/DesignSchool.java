package team11newbot;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotType;

public class DesignSchool extends Building {
    static int numLandscapers = 0;
    public DesignSchool(RobotController r) {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        // will only actually happen if we haven't already broadcasted the creation
        comms.broadcastDesignSchoolCreation(rc.getLocation());

        if (numLandscapers < 4){
            if(tryBuild(RobotType.LANDSCAPER, Util.randomDirection())) {
                System.out.println("made a landscaper");
                numLandscapers++;
            }

        }
        else{}
    }
}
