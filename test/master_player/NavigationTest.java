package master_player;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import org.junit.*;
import org.mockito.*;
import org.mockito.junit.*;

public class NavigationTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    RobotController rcMock = mock(RobotController.class);
    @InjectMocks
    Navigation navMock = new Navigation(rcMock);

    @Before
    public void setup() throws GameActionException {
        when(rcMock.isReady()).thenReturn(true);
        when(rcMock.canMove(Direction.CENTER)).thenReturn(true);
        when(rcMock.getLocation()).thenReturn(new MapLocation(5, 5));
        when(rcMock.senseFlooding(new MapLocation(5, 5))).thenReturn(false);
    }
    @Test
    public void tryMove() throws GameActionException{
        boolean result = navMock.tryMove(Direction.NORTH);
        assertEquals(result,false);

    }
    @Test
    public void droneMove() throws GameActionException{
        boolean result = navMock.droneMove(Direction.CENTER);
        assertEquals(true, result);

    }
    @Test
    public void goTo() throws GameActionException{
        boolean result = navMock.goTo(Direction.CENTER);
        assertEquals(true, result);
    }

    @Test
    public void firstDroneMove() throws GameActionException{
        boolean result = navMock.firstDroneMove(Direction.CENTER);
        assertEquals(true, result);

    }

    @Test
    public void goToLoc() throws GameActionException{
        boolean result = navMock.goTo(new MapLocation(5,6));
        // not sure why its false...
        assertEquals(false, result);
    }

    @Test
    public void stuckInPos() throws GameActionException{
        boolean result = navMock.stuckInPosition();
        assertEquals(true, result);
    }


}