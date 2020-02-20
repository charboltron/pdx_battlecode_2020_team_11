package master_player;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotType;
import battlecode.common.Team;
import org.junit.*;
import org.mockito.*;
import org.mockito.junit.*;

public class RobotPlayerTest {

        @Rule
        public MockitoRule mockitoRule = MockitoJUnit.rule();
        @Mock
        RobotController rcMock = mock(RobotController.class);
        @InjectMocks
        RobotPlayer rpMock = new RobotPlayer();

        @Before
        public void setUp() {
            when(rcMock.getTeam()).thenReturn(Team.A);
            when(rcMock.getType()).thenReturn(RobotType.HQ);

        }

        @Test
        public void getTeamTest() {
            assertEquals(rcMock.getTeam(), Team.A);
        }

        @Test
        public void getTypeTest() {
            assertEquals(rcMock.getType(), RobotType.HQ);
        }


    }