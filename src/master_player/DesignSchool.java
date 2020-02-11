package master_player;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotType;

public class DesignSchool extends Building {

    static int numLandscapers = 0;

    public DesignSchool(RobotController r) throws GameActionException {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        
        // will only actually happen if we haven't already broadcasted the creation
        //        comms.broadcastBuildingCreation(rc.getLocation(), RobotType.DESIGN_SCHOOL, rc.getTeam(), 0);

        //moved comms to building class leaving here for now to see if it works as intended

        if(numLandscapers < 4){
            if(tryBuild(RobotType.LANDSCAPER, Util.randomDirection())) {
                System.out.println("made a landscaper");
                numLandscapers++;
            }
        }
    }
}
