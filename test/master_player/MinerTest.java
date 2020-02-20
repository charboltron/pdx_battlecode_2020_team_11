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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class MinerTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    RobotController rcMock = mock(RobotController.class);

    @InjectMocks
    Miner minerMock = new Miner(rcMock);
    @Before
    public void setup() {
        when(rcMock.getTeam()).thenReturn(Team.A);
        when(rcMock.getType()).thenReturn(RobotType.HQ);
    }

    @Test
    public void tryRefine() throws GameActionException {
        when(rcMock.isReady()).thenReturn(true);
        when(rcMock.canDepositSoup(Direction.CENTER)).thenReturn(true);
        when(rcMock.getSoupCarrying()).thenReturn(1);

        boolean result = minerMock.tryRefine(Direction.CENTER);
        assertTrue(result);
    }
    @Test
    public void tryMine() throws GameActionException{
        when(rcMock.isReady()).thenReturn(true);
        when(rcMock.canMineSoup(Direction.CENTER)).thenReturn(true);
        boolean result = minerMock.tryMine(Direction.CENTER);
        assertTrue(result);
    }
    /*@Test
    public void getNearestRefinery(){
        when(rcMock.senseNearbyRobots()).thenReturn(new RobotInfo[]{new RobotInfo(1,Team.A,RobotType.REFINERY,0,false,0,0,0, new MapLocation(1,1))});
        minerMock.getNearestRefinery();
        verify(rcMock).senseNearbyRobots();
        verify(rcMock).getTeam();
    }*/
    /*@Test
    public void buildRefinery() throws GameActionException{
        when(rcMock.isReady()).thenReturn(true);
        minerMock.buildRefinery();
    }
    @Test
    public void getDirToMine() throws GameActionException {
        when(rcMock.getLocation()).thenReturn(new MapLocation(6, 6));
        when(rcMock.canSenseLocation(new MapLocation(6, 6))).thenReturn(true);
        when(rcMock.senseSoup()).thenReturn(new MapLocation(6, 6));
        when(rcMock.canMineSoup(Direction.CENTER)).thenReturn(true);
        minerMock.getDirToMine();
        verify(rcMock).getLocation();
        verify(rcMock).canSenseLocation(new MapLocation(6, 6));


    }
*/


}