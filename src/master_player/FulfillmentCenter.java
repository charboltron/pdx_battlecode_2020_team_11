package master_player;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotType;

public class FulfillmentCenter extends Building {
    static int numDrones = 0;

    public FulfillmentCenter(RobotController r) throws GameActionException {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        if(comms.onlyOneMessageToRead()){comms.getMessages();}

        if (turnCount % 10 ==0 && numDrones < 3) {
            if (tryBuild(RobotType.DELIVERY_DRONE,Direction.SOUTH)) {
                System.out.println("drone built");
                comms.broadcastRobotCreation(myLoc, RobotType.DELIVERY_DRONE, rc.getTeam(), 0);
                numDrones++;
                if(comms.numDrones > numDrones){
                    numDrones = comms.numDrones;
                }

            }

        }
        comms.getMessages();
    }
}
