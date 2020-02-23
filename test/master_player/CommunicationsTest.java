package master_player;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotType;
import battlecode.common.Team;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommunicationsTest {


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    RobotController rcMock = mock(RobotController.class);
    @InjectMocks
    Communications commsMock = new Communications(rcMock);


    @Before
    public void create() throws GameActionException {
        when(rcMock.getTeam()).thenReturn(Team.A);
        when(rcMock.getType()).thenReturn(RobotType.HQ);
//        when(rcMock.buildRobot(RobotType.FULFILLMENT_CENTER)).thenCallRealMethod();

    }

    @Test
    public void updateRobotCounts() throws GameActionException{
        String result = commsMock.updateRobotCounts();
        assertEquals("updated Robot Counts", result);
    }

//    @Test
//    public void whenFulFillmentCenterIsCreatedItsBuildingCountIsIncremented() throws GameActionException {
//        assertEquals(0, commsMock.numFulFillmentCenters);
//    }


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
}


