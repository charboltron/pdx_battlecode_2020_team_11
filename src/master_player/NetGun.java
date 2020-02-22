package master_player;

import battlecode.common.*;

public class NetGun extends Shooter{
    public NetGun(RobotController r) throws GameActionException {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
//        if (rc.getTeam() != null) {
//            Team enemy = rc.getTeam().opponent();
//            RobotInfo[] enemiesInRange = rc.senseNearbyRobots(GameConstants.NET_GUN_SHOOT_RADIUS_SQUARED, enemy);
//            for (RobotInfo e : enemiesInRange) {
//                if (e.type == RobotType.DELIVERY_DRONE) {
//                    if (rc.canShootUnit(e.ID)) {
//                        rc.shootUnit(e.ID);
//                        break;
//                    }
//                }
//            }
    }
}
