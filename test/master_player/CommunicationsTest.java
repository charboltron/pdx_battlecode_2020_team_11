//package master_player;
//
//import battlecode.common.*;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnit;
//import org.mockito.junit.MockitoRule;
//
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//
//import static org.junit.Assert.*;
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.anyInt;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class CommunicationsTest {
//
//    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
//    private final PrintStream originalOut = System.out;
//    private final PrintStream originalErr = System.err;
//
//
//    @Rule
//    public MockitoRule mockitoRule = MockitoJUnit.rule();
//    @Mock
//    RobotController rcMock              = mock(RobotController.class);
//    HQ hqMock                           = mock(HQ.class);
//    DesignSchool designSchoolMock       = mock(DesignSchool.class);
//    Refinery     refineryMock           = mock(Refinery.class);
//    FulfillmentCenter fCenterMock       = mock(FulfillmentCenter.class);
//    NetGun  netGunMock                  = mock(NetGun.class);
//    Vaporator vaporatorMock             = mock(Vaporator.class);
//    Drone     droneMock                 = mock(Drone.class);
//    Landscaper landscaperMock           = mock(Landscaper.class);
//    Miner       minerMock               = mock(Miner.class);
//
//    @InjectMocks
//    Communications commsMock = new Communications(rcMock);
//
//    int msg1 [] = {commsMock.teamSecret1, 100, 15, 18, 0, 0, commsMock.teamSecret2};
//    int msg2 [] = {commsMock.teamSecret1, 200, 15, 18, 0, 0, commsMock.teamSecret2};
//    Transaction tx1 = new Transaction(3, msg1, 1);
//    Transaction[] txs = {tx1};
//
//
//    @Before
//    public void setUpStreams() {
//        System.setOut(new PrintStream(outContent));
//        System.setErr(new PrintStream(errContent));
//    }
//
//    @Before
//    public void create() throws GameActionException {
//        when(rcMock.getTeam()).thenReturn(Team.A);
//        when(rcMock.getTeam().opponent()).thenReturn(Team.B);
//        when(rcMock.getRoundNum()).thenReturn(5);
//        when(rcMock.getBlock(1)).thenReturn(txs);
//        when(rcMock.getType()).thenReturn(RobotType.HQ);
//        when(rcMock.canSubmitTransaction(any(int[].class), anyInt())).thenReturn(true);
////        when(commsMock.getHqLocFromBlockchain()).thenReturn(new MapLocation(15, 18));
//
//        hqMock.myLoc = new MapLocation(15, 18);
//        designSchoolMock.myLoc = new MapLocation(15, 18);
//        refineryMock.myLoc = new MapLocation(15, 18);
//        fCenterMock.myLoc = new MapLocation(15, 18);
//        netGunMock.myLoc  = new MapLocation(15, 18);
//        vaporatorMock.myLoc =new MapLocation(15, 18);
//        droneMock.myLoc = new MapLocation(15, 18);
//        landscaperMock.myLoc = new MapLocation(15, 18);
//        minerMock.myLoc = new MapLocation(15, 18);
//
//
//    }
//
//    @Test
//    public void updateRobotCounts() throws GameActionException{
//        String result = commsMock.updateRobotCounts();
//        assertEquals("updated Robot Counts", result);
//    }
//
//    @Test
//    public void getRobotCode() throws GameActionException{
//
//        int code;
//        Robot robot = new Robot(rcMock);
//        code = robot.comms.getRobotCode(RobotType.HQ, rcMock.getTeam());
//        assertEquals(code, 100);
//        code = robot.comms.getRobotCode(RobotType.DESIGN_SCHOOL, rcMock.getTeam());
//        assertEquals(code, 101);
//        code = robot.comms.getRobotCode(RobotType.REFINERY, rcMock.getTeam());
//        assertEquals(code, 102);
//        code = robot.comms.getRobotCode(RobotType.FULFILLMENT_CENTER, rcMock.getTeam());
//        assertEquals(code, 103);
//        code = robot.comms.getRobotCode(RobotType.NET_GUN, rcMock.getTeam());
//        assertEquals(code, 104);
//        code = robot.comms.getRobotCode(RobotType.VAPORATOR, rcMock.getTeam());
//        assertEquals(code, 105);
//        code = robot.comms.getRobotCode(RobotType.DELIVERY_DRONE, rcMock.getTeam());
//        assertEquals(code, 106);
//        code = robot.comms.getRobotCode(RobotType.LANDSCAPER, rcMock.getTeam());
//        assertEquals(code, 107);
//        code = robot.comms.getRobotCode(RobotType.MINER, rcMock.getTeam());
//        assertEquals(code, 108);
//        code = robot.comms.getRobotCode(RobotType.HQ, rcMock.getTeam().opponent());
//        assertEquals(code, 200);
//        code = robot.comms.getRobotCode(RobotType.DESIGN_SCHOOL, rcMock.getTeam().opponent());
//        assertEquals(code, 201);
//        code = robot.comms.getRobotCode(RobotType.REFINERY, rcMock.getTeam().opponent());
//        assertEquals(code, 202);
//        code = robot.comms.getRobotCode(RobotType.FULFILLMENT_CENTER, rcMock.getTeam().opponent());
//        assertEquals(code, 203);
//        code = robot.comms.getRobotCode(RobotType.NET_GUN, rcMock.getTeam().opponent());
//        assertEquals(code, 204);
//        code = robot.comms.getRobotCode(RobotType.VAPORATOR, rcMock.getTeam().opponent());
//        assertEquals(code, 205);
//        code = robot.comms.getRobotCode(RobotType.DELIVERY_DRONE, rcMock.getTeam().opponent());
//        assertEquals(code, 206);
//        code = robot.comms.getRobotCode(RobotType.LANDSCAPER, rcMock.getTeam().opponent());
//        assertEquals(code, 207);
//        code = robot.comms.getRobotCode(RobotType.MINER, rcMock.getTeam().opponent());
//        assertEquals(code, 208);
//
//    }
//
//    @Test
//    public void getHqLocFromBlockchainReturnsCorrectLocation() throws GameActionException {
//
//        MapLocation hqLoc = commsMock.getHqLocFromBlockchain();
//        assertEquals(15, hqLoc.x);
//        assertEquals(18, hqLoc.y);
//
//    }
//
//    @Test
//    public void broadcastHQCreation() throws GameActionException {
//
//        commsMock.broadcastRobotCreation(hqMock.myLoc, RobotType.HQ, rcMock.getTeam(), 3);
//        assertEquals("sending HQ loc\n", outContent.toString());
//
//    }
//
//    @Test
//    public void broadcastDesignSchoolCreation() throws GameActionException {
//
//        commsMock.broadcastRobotCreation(designSchoolMock.myLoc, RobotType.DESIGN_SCHOOL, rcMock.getTeam(), 3);
//        assertEquals("design school created\n", outContent.toString());
//
//    }
//
//    @Test
//    public void broadcastRefineryCreation() throws GameActionException {
//
//        commsMock.broadcastRobotCreation(refineryMock.myLoc, RobotType.REFINERY, rcMock.getTeam(), 3);
//        assertEquals("refinery created\n", outContent.toString());
//
//    }
//    @Test
//    public void broadcastFulfillmentCenterCreation() throws GameActionException {
//
//        commsMock.broadcastRobotCreation(fCenterMock.myLoc, RobotType.FULFILLMENT_CENTER, rcMock.getTeam(), 3);
//        assertEquals("fulfillment center created\n", outContent.toString());
//
//    }
//    @Test
//    public void broadcastNetGunCreation() throws GameActionException {
//
//        commsMock.broadcastRobotCreation(netGunMock.myLoc, RobotType.NET_GUN, rcMock.getTeam(), 3);
//        assertEquals("netgun created\n", outContent.toString());
//
//    }
//    @Test
//    public void broadcastVaporatorCreation() throws GameActionException {
//
//        commsMock.broadcastRobotCreation(vaporatorMock.myLoc, RobotType.VAPORATOR, rcMock.getTeam(), 3);
//        assertEquals("vaporator created\n", outContent.toString());
//
//    }
//    @Test
//    public void broadcastDroneCreation() throws GameActionException {
//
//        commsMock.broadcastRobotCreation(droneMock.myLoc, RobotType.DELIVERY_DRONE, rcMock.getTeam(), 3);
//        assertEquals("built drone\n", outContent.toString());
//
//    }
//    @Test
//    public void broadcastLandscaperCreation() throws GameActionException {
//
//        commsMock.broadcastRobotCreation(landscaperMock.myLoc, RobotType.LANDSCAPER, rcMock.getTeam(), 3);
//        assertEquals("built landscaper\n", outContent.toString());
//
//    }
//    @Test
//    public void broadcastMinerCreation() throws GameActionException {
//
//        commsMock.broadcastRobotCreation(minerMock.myLoc, RobotType.MINER, rcMock.getTeam(), 3);
//        assertEquals("built miner\n", outContent.toString());
//
//    }
//
//    @Test
//    public void onlyOneMessage() throws GameActionException {
//
//        commsMock.lastBroadcastRound = 4;
//        assertEquals(commsMock.onlyOneMessageToRead(), true);
//
//        commsMock.lastBroadcastRound = 3;
//        assertEquals(commsMock.onlyOneMessageToRead(), false);
//    }
//
//}
//
//
