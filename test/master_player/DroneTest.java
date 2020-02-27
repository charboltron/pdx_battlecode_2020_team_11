package master_player;

import battlecode.common.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Objects;

import static org.junit.Assert.*;
import static org.mockito.Matchers.booleanThat;
import static org.mockito.Mockito.*;

public class DroneTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    RobotController rcMock = mock(RobotController.class);

    @InjectMocks
    Drone droneMock = new Drone(rcMock);
    @Before
    public void create() throws GameActionException{
        droneMock = mock(Drone.class);
        droneMock.comms = mock(Communications.class);

        when(rcMock.getType()).thenReturn(RobotType.DELIVERY_DRONE);
        when(rcMock.getTeam()).thenReturn(Team.A);
    }
    @Test
    public void takeTurnAsExpected() throws GameActionException{
        droneMock.takeTurn();
        assertEquals(0,droneMock.turnCount);
    }
//    @Test
//    public void scoutSoup() throws GameActionException{
//        when(rcMock.canSenseLocation(new MapLocation(5,5))).thenReturn(true);
//        when(rcMock.senseSoup(new MapLocation(6,5))).thenReturn(5);
//        droneMock.scoutSoup();
//        verify(rcMock).canSenseLocation(new MapLocation(5,5));
//
//    }
//    @Test
//    public void checkIfFirstDrone() throws GameActionException{
//        when(rcMock.isReady()).thenReturn(true);
//        boolean result = droneMock.checkIfFirstDrone();
//        assertFalse(result);
//
//
//    }

}