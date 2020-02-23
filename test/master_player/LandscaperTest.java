package master_player;
import battlecode.common.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.omg.PortableInterceptor.DISCARDING;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LandscaperTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    RobotController rcMock = mock(RobotController.class);
    @InjectMocks
    Landscaper lsMock = new Landscaper(rcMock);

    @Before
    public void setup() throws GameActionException {
        when(rcMock.isReady()).thenReturn(true);
        when(rcMock.canDigDirt(Direction.CENTER)).thenReturn(true);
        when(rcMock.canMove(Direction.CENTER)).thenReturn(true);
        when(rcMock.getLocation()).thenReturn(new MapLocation(5, 5));
        when(rcMock.senseFlooding(new MapLocation(5, 5))).thenReturn(false);
    }

    @Test
    public void tryDig() throws GameActionException {
        boolean result = lsMock.tryDig(new MapLocation(5,5));
        assertEquals(false, result);

    }


}
