package master_player;

import battlecode.common.*;

public class Landscaper extends Unit {

    public Landscaper(RobotController r) {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        MapLocation myLoc = rc.getLocation();
        // first, save HQ by trying to remove dirt from it
        if (hqLoc != null && hqLoc.isAdjacentTo(rc.getLocation())) {
            Direction dirtohq = myLoc.directionTo(hqLoc);
            if(rc.canDigDirt(dirtohq)){
                rc.digDirt(dirtohq);
            }
        }

        if(rc.getDirtCarrying() == 0){
            tryDig(myLoc);
        }

        MapLocation bestPlaceToBuildWall = null;
        // find best place to build
        if(hqLoc != null) {
            int lowestElevation = 9999999;
            for (Direction dir : Util.directions) {
                MapLocation tileToCheck = hqLoc.add(dir);
                if(myLoc.distanceSquaredTo(tileToCheck) < 4
                        && rc.canDepositDirt(myLoc.directionTo(tileToCheck))) {
                    if (rc.senseElevation(tileToCheck) < lowestElevation) {
                        lowestElevation = rc.senseElevation(tileToCheck);
                        bestPlaceToBuildWall = tileToCheck;
                    }
                }
            }
        }

        if(myLoc.distanceSquaredTo(hqLoc) <= 4) {
            RobotInfo[] robots = rc.senseNearbyRobots();
            for (RobotInfo robot : robots) {
                for(RobotType robotType : Util.spawnedByMiner){
                    if(robot.getType() == robotType){
                        nav.goTo(hqLoc);
                    }
                }
            }
        }


        if (Math.random() < 0.8){ //TODO: investigate changing this random number
            // build the wall
            if (rc.isReady() && bestPlaceToBuildWall != null) {
                rc.depositDirt(myLoc.directionTo(bestPlaceToBuildWall));
                rc.setIndicatorDot(bestPlaceToBuildWall, 0, 255, 0);
                System.out.println("building a wall");
            }
        }

        // otherwise try to get to the hq
        if(hqLoc != null){
            nav.goTo(hqLoc);
        } else {
            nav.goTo(Util.randomDirection());
        }
    }

    boolean tryDig(MapLocation myLoc) throws GameActionException {
        Direction dir;
        if(hqLoc == null){
            dir = Util.randomDirection();
        } else {
            dir = hqLoc.directionTo(myLoc);
        }
        if(rc.canDigDirt(dir)){
            rc.digDirt(dir);
            rc.setIndicatorDot(myLoc.add(dir), 255, 0, 0);
            return true;
        }
        return false;
    }
}