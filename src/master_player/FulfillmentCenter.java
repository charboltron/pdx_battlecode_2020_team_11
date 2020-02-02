package master_player;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class FulfillmentCenter extends Unit {
    static int numDrones = 0;
    public FulfillmentCenter(RobotController r) { super(r); }

    public void takeTurn() throws GameActionException{
        super.takeTurn();
        /*if (numDrones <2) {
            if (tryBuild(RobotType.DELIVERY_DRONE, Direction.EAST)) {
                System.out.println("drone built");
                numDrones++;
            }
        }*/
    }
}
