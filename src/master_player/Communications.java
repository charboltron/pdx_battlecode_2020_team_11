package master_player;
import battlecode.common.*;
import java.util.ArrayList;

import static battlecode.common.RobotType.DELIVERY_DRONE;

public class Communications {


    int numDesignSchools        = 0;
    int numRefineries           = 0;
    int numFulFillmentCenters   = 0;
    int numVaporators           = 0;
    int numNetGuns              = 0;
    int numDrones               = 0;
    int numLandscapers          = 0;
    int numMiners               = 0;
    int teamSoup                = 0;

    ArrayList<MapLocation> soupLocations;

    static final int ROBOT_INFORMATION = 1;
    static final int SOUP_LOCATION  = 2;
    RobotController rc;
    int lastBroadcastRound = 1;
    int[] robotCounts;


    // state related only to communications should go here

    // all messages from our team should start with this so we can tell them apart
    static final int teamSecret1 = 9890231;
    static final int teamSecret2 = 7432456;
    // the second entry in every message tells us what kind of message it is. e.g. 0 means it contains the HQ location
    static final String[] messageType = {
            "sending HQ loc",                           //100
            "design school created",                    //101
            "refinery created",                         //102
            "fulfillment center created",               //103
            "netgun created",                           //104
            "vaporator created",                        //105
            "built drone",                              //106
            "built landscaper",                         //107
            "built miner",                              //108
            "found enemy HQ loc",                       //200
            "found new enemy design school",            //201
            "found new enemy refinery",                 //202
            "found new enemy fulfillment center",       //203
            "found new enemy netgun",                   //204
            "found new enemy vaporator",                //205
            "saw enemy drone",                          //206
            "saw enemy landscaper",                     //207
            "saw enemy miner",                          //208
            "soup location",                            //1000
    };

    public boolean broadcastedCreation = false; //used only once per spawned Building


    public Communications(RobotController r) {

        rc = r;
        robotCounts = new int[8];
        soupLocations = new ArrayList<MapLocation>();
    }

    boolean onlyOneMessageToRead(){
        return lastBroadcastRound == rc.getRoundNum()-1;
    }

    boolean caughtUp(){ return lastBroadcastRound == rc.getRoundNum(); }

    public int getRobotCode(RobotType robotType, Team team) throws GameActionException {

        int RobotCode = -1;

        switch (robotType){
            case HQ:                     RobotCode = 0;   break;
            case DESIGN_SCHOOL:          RobotCode = 1;   break;
            case REFINERY:               RobotCode = 2;   break;
            case FULFILLMENT_CENTER:     RobotCode = 3;   break;
            case NET_GUN:                RobotCode = 4;   break;
            case VAPORATOR:              RobotCode = 5;   break;
            case DELIVERY_DRONE:         RobotCode = 6;   break;
            case LANDSCAPER:             RobotCode = 7;   break;
            case MINER:                  RobotCode = 8;   break;
            default: break;
        }
        if(rc.getTeam() != null) {
            if (team == rc.getTeam().opponent()) {
                RobotCode += 200;
            } else {
                RobotCode += 100;
            }
            return RobotCode;
        }
        return -1;
    }

    public MapLocation getHqLocFromBlockchain() throws GameActionException {
        for (int i = 1; i < rc.getRoundNum(); i++){
            for(Transaction tx : rc.getBlock(i)) {
                int[] mess = tx.getMessage();
                if(mess[0] == teamSecret1 && mess[1] == 100){
                    System.out.println("found the HQ!");
                    return new MapLocation(mess[2], mess[3]);
                }
            }
        }
        return null;
    }

    public void broadcastRobotCreation(MapLocation loc, RobotType robotType, Team team, int transactionBid) throws GameActionException {
        if(broadcastedCreation) return; //don't re-broadcast


        if(robotType == null){
            return; //testing
        }

        int robotCode = getRobotCode(robotType, team);

        if(transactionBid == 0) {
            transactionBid = 1; //TODO this should not be hardcoded eventually
        }
        if(robotCode > 0) {
            int[] message = new int[7];
            message[0] = teamSecret1;
            message[1] = robotCode;
            message[2] = loc.x; // x coord of Robot
            message[3] = loc.y; // y coord of Robot
            message[4] = ROBOT_INFORMATION;
            message[6] = teamSecret2;
            if (rc.canSubmitTransaction(message, transactionBid)) {
                rc.submitTransaction(message, transactionBid);
                broadcastedCreation = true;
                System.out.println(messageType[robotCode-100]);
                System.out.println("msg broadcast at round: "+rc.getRoundNum());
            }
        }
    }


    public void getMessages() throws GameActionException {

        int roundNum = rc.getRoundNum();
        while (lastBroadcastRound < roundNum && Clock.getBytecodesLeft() >= 300) {
            Transaction[] transactions = rc.getBlock(lastBroadcastRound);
            for (Transaction tx : transactions) {
                int[] msg = tx.getMessage();
                if (!(msg[0] == teamSecret1 && msg[6] == teamSecret2)) {
                    continue;
                }
                int messageCode = msg[4];
                switch (messageCode) {
                    case ROBOT_INFORMATION: {
                        int robotCode = msg[1];
                        System.out.println("heard about a new: " + messageType[robotCode - 100]);
                        if ((robotCode-101) >= 0 && (robotCode -101) <= 8) {
                            ++robotCounts[robotCode - 101];
                        }
                        break;
                    }
                    case SOUP_LOCATION: {

                        MapLocation newSoupLocation = new MapLocation(msg[2], msg[3]);
                        if (!soupLocations.contains(newSoupLocation)) {
                            System.out.println("heard about a tasty new soup location");
                            soupLocations.add(newSoupLocation);

                        }
                        break;
                    }
                }
            } lastBroadcastRound++;
        }
    }

//    public void updateSoupLocations() throws GameActionException{
//        for(Transaction tx : rc.getBlock(rc.getRoundNum() - 1)) {
//            int[] msg = tx.getMessage();
//            if(msg[0] == teamSecret1 && msg[1] == 10 && msg[6] == teamSecret2){
//                MapLocation newSoupLocation = new MapLocation(msg[2], msg[3]);
//                if(!soupLocations.contains(newSoupLocation)){
//                    System.out.println("heard about a tasty new soup location");
//                    soupLocations.add(newSoupLocation);
//                }
//            }
//        }
//    }

    public void broadcastSoupLocation(MapLocation loc) throws GameActionException {
        int[] message = new int[7];
        message[0] = teamSecret1;
        message[1] = 10;
        message[2] = loc.x; // x coord of HQ
        message[3] = loc.y; // y coord of HQ
        message[4] = SOUP_LOCATION;
        message[6] = teamSecret2;
        if (rc.canSubmitTransaction(message, 3)) {
            rc.submitTransaction(message, 3);
            System.out.println("new soup!" + loc);
        }
        soupLocations.add(loc);
    }

    public void updateRobotCounts() {
        numDesignSchools      = robotCounts[0];
        numRefineries         = robotCounts[1];
        numFulFillmentCenters = robotCounts[2];
        numNetGuns            = robotCounts[3];
        numVaporators         = robotCounts[4];
        numDrones             = robotCounts[5];
        numLandscapers        = robotCounts[6];
        numMiners             = robotCounts[7];

    }
}