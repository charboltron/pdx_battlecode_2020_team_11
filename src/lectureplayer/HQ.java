package lectureplayer;
import battlecode.common.*;

public class HQ extends Shooter {
    static int numMiners = 0;

    public HQ(RobotController r) throws GameActionException {
        super(r);

        comms.sendHqLoc(rc.getLocation());
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        if(numMiners < 5) {
            for (Direction dir : Util.directions)
                if(tryBuild(RobotType.MINER, dir)){
                    numMiners++;
                }
        }
        // shoot nearby enemies
        Team enemy = rc.getTeam().opponent();
        RobotInfo[] enemiesInRange = rc.senseNearbyRobots(GameConstants.NET_GUN_SHOOT_RADIUS_SQUARED, enemy);

        for (RobotInfo e : enemiesInRange) {
            if (e.type == RobotType.DELIVERY_DRONE) {
                if (rc.canShootUnit(e.ID)){
                    rc.shootUnit(e.ID);
                    break;
                }
            }
        }
    }
}