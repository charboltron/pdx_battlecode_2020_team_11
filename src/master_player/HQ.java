package master_player;
import battlecode.common.*;

import java.util.ArrayList;
import java.util.Map;

public class HQ extends Shooter {


    static int numMiners = 0;
    Direction dirToNearestSoup;
    ArrayList<MapLocation> nearbySoupSpots;

    public HQ(RobotController r) throws GameActionException {
        super(r);
        nearbySoupSpots = new ArrayList<MapLocation>();

    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        if (rc.getRoundNum() == 1) {
            getSoupInRadius();
        }

        if (numMiners < 5) {
            for (Direction dir : Util.directions)
                if (tryBuild(RobotType.MINER, dir)) {
                    numMiners++;
                }
        }
        // shoot nearby enemies
        if (rc.getTeam() != null) {

            Team enemy = rc.getTeam().opponent();
            RobotInfo[] enemiesInRange = rc.senseNearbyRobots(GameConstants.NET_GUN_SHOOT_RADIUS_SQUARED, enemy);

            for (RobotInfo e : enemiesInRange) {
                if (e.type == RobotType.DELIVERY_DRONE) {
                    if (rc.canShootUnit(e.ID)) {
                        rc.shootUnit(e.ID);
                        break;
                    }
                }
            }
        }
    }

    void getSoupInRadius() throws GameActionException {

        int soup = 0;
        dirToNearestSoup = null;
        MapLocation loc = null;

        MapLocation[] nearbySoups = rc.senseNearbySoup(myLoc, rc.getCurrentSensorRadiusSquared());
        if (nearbySoups.length != 0) {
            for (MapLocation s : nearbySoups) {
                if (!nearbySoupSpots.contains(s)) {
                    nearbySoupSpots.add(s);
                    comms.broadcastSoupLocation(s);
                }
            }
        }
    }
}