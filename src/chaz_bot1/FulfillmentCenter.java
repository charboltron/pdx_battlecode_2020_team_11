package chaz_bot1;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotType;

public class FulfillmentCenter extends Unit {


    public FulfillmentCenter(RobotController r) throws GameActionException {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        for (Direction dir : Util.directions) {
            tryBuild(RobotType.DELIVERY_DRONE, dir);
            //TODO: make drones try to pick up cow.
        }
    }
}
