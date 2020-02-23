package master_player;

import battlecode.common.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UnitTest {
        @Rule
        public MockitoRule mockitoRule = MockitoJUnit.rule();
        @Mock
        RobotController rcMock = mock(RobotController.class);
        @InjectMocks
        Unit unitMock = new Unit(rcMock);

        @Before
        public void setup() {
            when(rcMock.getTeam()).thenReturn(Team.A);
            when(rcMock.getType()).thenReturn(RobotType.HQ);
        }

        @Test
        public void takeTurn() throws GameActionException{
            when(rcMock.senseNearbyRobots()).thenReturn(new RobotInfo[]{new RobotInfo(1, Team.A, RobotType.HQ, 0, false, 0, 0, 0, new MapLocation(5, 5))});
            when(rcMock.getLocation()).thenReturn(new MapLocation(5,5));
            unitMock.takeTurn();
            verify(rcMock).senseNearbyRobots();
            verify(rcMock).senseNearbyRobots();
            verify(rcMock).getLocation();

        }

        @Test
        public void findHQ() throws GameActionException {
            when(rcMock.senseNearbyRobots()).thenReturn(new RobotInfo[]{new RobotInfo(1, Team.A, RobotType.HQ, 0, false, 0, 0, 0, new MapLocation(5, 5))});
            unitMock.findHQ();
            verify(rcMock).senseNearbyRobots();
            verify(rcMock).getTeam();

        }


}