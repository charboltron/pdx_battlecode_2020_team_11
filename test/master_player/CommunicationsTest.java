//package master_player;
//
//import battlecode.common.GameActionException;
//import battlecode.common.RobotController;
//import battlecode.common.RobotType;
//import battlecode.common.Team;
//import org.junit.Before;
//import org.junit.Test;
//import sun.security.krb5.internal.crypto.Des;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class CommunicationsTest {
//
//
//    RobotController rcMock;
//    FulfillmentCenter fulfillmentCenter;
//    Communications commsMock;
//
//
//    @Before
//    public void create() throws GameActionException {
//
//        rcMock = mock(RobotController.class);
//        commsMock  = mock(Communications.class);
//
//        when(rcMock.getTeam()).thenReturn(Team.A);
//        when(rcMock.getType()).thenReturn(RobotType.HQ);
//        when(rcMock.buildRobot(RobotType.FULFILLMENT_CENTER)).thenCallRealMethod();
//
//    }
//
//
//    @Test
//    public void whenFulFillmentCenterIsCreatedItsBuildingCountIsIncremented() throws GameActionException {
//
//        assertEquals(1, commsMock.numFulFillmentCenters);
//
//
//    }
//

//    @Test
//    public void whenDesignSchoolIsCreatedItsBuildingCountIsIncremented() throws GameActionException {
//
//        designSchoolMock.comms.getMessages();
//        int myCount = designSchoolMock.comms.numDesignSchools;
//        assertEquals(1, myCount);
//
//    }
//
//    @Test
//    public void whenVaporatorIsCreatedItsBuildingCountIsIncremented() throws GameActionException {
//
//        int myCount = fulfillmentCenterMock.comms.numFulFillmentCenters;
//        assertEquals(1, myCount);
//
//    }
//
//    @Test
//    public void whenNetGunIsCreatedItsBuildingCountIsIncremented() throws GameActionException {
//
//        int myCount = netGunMock.comms.numNetGuns;
//        assertEquals(1, myCount);
//
//    }
//
//    @Test
//    public void whenRefineryIsCreatedItsBuildingCountIsIncremented() throws GameActionException {
//
//        int myCount = refineryMock.comms.numRefineries;
//        assertEquals(1, myCount);
//
//    }
//
//
//}