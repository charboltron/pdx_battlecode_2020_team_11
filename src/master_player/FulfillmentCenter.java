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


        if (turnCount % 10 ==0 && numDrones <2) {
            if (tryBuild(RobotType.DELIVERY_DRONE,Direction.NORTH)) {
                System.out.println("drone built");
                numDrones++;

            }

        }
    }
}
