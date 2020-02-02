package master_player;
import battlecode.common.*;
import java.util.ArrayList;

public class Communications {
    RobotController rc;

    // state related only to communications should go here

    // all messages from our team should start with this so we can tell them apart
    static final int teamSecret = 333333333; //TODO think of a better team secret lol
    // the second entry in every message tells us what kind of message it is. e.g. 0 means it contains the HQ location
    static final String[] messageType = {
            "sending HQ loc",                           //100
            "design school created",                    //101
            "refinery created",                         //102
            "fulfillment center created",               //103
            "netgun created",                           //104
            "vaporator created",                        //105
            "found enemy HQ loc",                       //200
            "found new enemy design school",            //201
            "found new enemy refinery",                 //202
            "found new enemy fulfillment center",       //203
            "found new enemy netgun",                   //204
            "found new enemy vaporator",                //205
            "soup location",                            //1000
    };

    public boolean broadcastedCreation = false; //used only once per spawned Building


    public Communications(RobotController r) {
        rc = r;
    }

    public int getBuildingCode(RobotType buildingType, Team team) throws GameActionException { //TODO possible method for unit testing

        int buildingCode = -1;

        switch (buildingType){
            case HQ:                     buildingCode = 0;   break;
            case DESIGN_SCHOOL:          buildingCode = 1;   break;
            case REFINERY:               buildingCode = 2;   break;
            case FULFILLMENT_CENTER:     buildingCode = 3;   break;
            case NET_GUN:                buildingCode = 4;   break;
            case VAPORATOR:              buildingCode = 5;   break;
            default: break;
        }
        if (team == rc.getTeam().opponent()){
            buildingCode += 200;
        } else {
            buildingCode += 100;
        }
        return buildingCode;
    }

    public MapLocation getHqLocFromBlockchain() throws GameActionException {
        for (int i = 1; i < rc.getRoundNum(); i++){
            for(Transaction tx : rc.getBlock(i)) {
                int[] mess = tx.getMessage();
                if(mess[0] == teamSecret && mess[1] == 100){
                    System.out.println("found the HQ!");
                    return new MapLocation(mess[2], mess[3]);
                }
            }
        }
        return null;
    }

    public void broadcastBuildingCreation(MapLocation loc, RobotType buildingType, Team team, int transactionBid) throws GameActionException {
        if(broadcastedCreation) return; //don't re-broadcast

        int buildingCode = getBuildingCode(buildingType, team);

        if(transactionBid == 0) {
            transactionBid = 3; //TODO this should not be hardcoded eventually
        }
        if(buildingCode > 0) {
            int[] message = new int[7];
            message[0] = teamSecret;
            message[1] = buildingCode;
            message[2] = loc.x; // x coord of Robot
            message[3] = loc.y; // y coord of Robot
            if (rc.canSubmitTransaction(message, transactionBid)) {
                rc.submitTransaction(message, transactionBid);
                broadcastedCreation = true;
                System.out.println(messageType[buildingCode-100]);
            }
        }
    }

    public int getBuildingCount(RobotType buildingType, Team team) throws GameActionException{

        int count = 0;
        int buildingCode = getBuildingCode(buildingType, team);
        for(Transaction tx : rc.getBlock(rc.getRoundNum() - 1)) {
            int[] mess = tx.getMessage();
            if(mess[0] == teamSecret && mess[1] == buildingCode){
                System.out.println("heard about a new: "+team.toString()+" "+buildingType.toString());
                count += 1;
            }
        }
        return count;
    }

    public void updateSoupLocations(ArrayList<MapLocation> soupLocations) throws GameActionException {
        for(Transaction tx : rc.getBlock(rc.getRoundNum() - 1)) {
            int[] mess = tx.getMessage();
            if(mess[0] == teamSecret && mess[1] == 10){
                // TODO: don't add duplicate locations
                System.out.println("heard about a tasty new soup location");
                soupLocations.add(new MapLocation(mess[2], mess[3]));
            }
        }
    }

    public void broadcastSoupLocation(MapLocation loc ) throws GameActionException {
        int[] message = new int[7];
        message[0] = teamSecret;
        message[1] = 10;
        message[2] = loc.x; // x coord of HQ
        message[3] = loc.y; // y coord of HQ
        if (rc.canSubmitTransaction(message, 3)) {
            rc.submitTransaction(message, 3);
            System.out.println("new soup!" + loc);
        }
    }
}

//    public void sendHqLoc(MapLocation loc) throws GameActionException {
//        int[] message = new int[7];
//        message[0] = teamSecret;
//        message[1] = 0;
//        message[2] = loc.x; // x coord of HQ
//        message[3] = loc.y; // y coord of HQ
//        if (rc.canSubmitTransaction(message, 3))
//            rc.submitTransaction(message, 3);
//    }


//    public reportEnemyHQ(MapLocation loc) throws GameActionException {
//
//
//    }



//    public void broadcastDesignSchoolCreation(MapLocation loc) throws GameActionException {
//        if(broadcastedCreation) return; // don't re-broadcast
//
//        int[] message = new int[7];
//        message[0] = teamSecret;
//        message[1] = 2;
//        message[2] = loc.x; // x coord of HQ
//        message[3] = loc.y; // y coord of HQ
//        if (rc.canSubmitTransaction(message, 3)) {
//            rc.submitTransaction(message, 3);
//            broadcastedCreation = true;
//        }
//    }

    // check the latest block for unit creation messages
//    public int getNewDesignSchoolCount() throws GameActionException {
//        int count = 0;
//        for(Transaction tx : rc.getBlock(rc.getRoundNum() - 1)) {
//            int[] mess = tx.getMessage();
//            if(mess[0] == teamSecret && mess[1] == 2){
//                System.out.println("heard about a new design school");
//                count += 1;
//            }
//        }
//        return count;
//    }
//
//    public void broadcastRefineryCreation(MapLocation loc) throws GameActionException {
//        if(broadcastedCreation) return; // don't re-broadcast
//
//        int[] message = new int[7];
//        message[0] = teamSecret;
//        message[1] = 3;
//        message[2] = loc.x; // x coord of HQ
//        message[3] = loc.y; // y coord of HQ
//        if (rc.canSubmitTransaction(message, 3)) {
//            rc.submitTransaction(message, 3);
//            broadcastedCreation = true;
//        }
//    }
//
//    // check the latest block for unit creation messages
//    public int getNewRefineryCount() throws GameActionException {
//        int count = 0;
//        for(Transaction tx : rc.getBlock(rc.getRoundNum() - 1)) {
//            int[] mess = tx.getMessage();
//            if(mess[0] == teamSecret && mess[1] == 3){
//                System.out.println("heard about a new refinery");
//                count += 1;
//            }
//        }
//        return count;
//    }


//    public void broadcastFulfillmentCenterCreation(MapLocation loc) throws GameActionException {
//        if(broadcastedCreation) return; // don't re-broadcast
//
//        int[] message = new int[7];
//        message[0] = teamSecret;
//        message[1] = 4;
//        message[2] = loc.x; // x coord of HQ
//        message[3] = loc.y; // y coord of HQ
//        if (rc.canSubmitTransaction(message, 3)) {
//            rc.submitTransaction(message, 3);
//            broadcastedCreation = true;
//        }
//    }

    // check the latest block for unit creation messages
//    public int getNewFulfillmentCenterCount() throws GameActionException {
//        int count = 0;
//        for(Transaction tx : rc.getBlock(rc.getRoundNum() - 1)) {
//            int[] mess = tx.getMessage();
//            if(mess[0] == teamSecret && mess[1] == 4){
//                System.out.println("heard about a new fulfillment center");
//                count += 1;
//            }
//        }
//        return count;
//    }


