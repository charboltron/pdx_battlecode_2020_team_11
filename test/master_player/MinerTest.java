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

    @Test
    public void tryMineDirNull() throws GameActionException{
        when(rcMock.isReady()).thenReturn(true);
//        when(rcMock.canMineSoup(null)).thenReturn(false);
        boolean result = minerMock.tryMine(null);
        assertFalse(result);
    }

    @Test
    public void updateRobotCounts() throws GameActionException{
        String result = minerMock.updateRobotsCounts();
        assertEquals("updated robot counts", result);
    }
//    @Test
//    public void getNearestRefinery(){
//        when(rcMock.senseNearbyRobots()).thenReturn(new RobotInfo[]{new RobotInfo(1,Team.A,RobotType.REFINERY,0,false,0,0,0, new MapLocation(1,1))});
//        minerMock.getNearestRefinery();
//        verify(rcMock).senseNearbyRobots();
//        verify(rcMock).getTeam();
//    }
//    @Test
//    public void buildRefinery() throws GameActionException{
//        when(rcMock.isReady()).thenReturn(true);
//        when(!minerMock.hqLoc.isWithinDistanceSquared(rcMock.getLocation(), 30)).thenReturn(true);
//        when(!minerMock.nearestRefinery.isWithinDistanceSquared(rcMock.getLocation(), 25)).thenReturn(true);
//        when(minerMock.tryBuild(RobotType.REFINERY, Util.randomDirection())).thenReturn(true);
//        when(minerMock.teamSoup).thenReturn(500);
//        when(minerMock.numRefineries).thenReturn(0);
//        boolean result = minerMock.buildRefinery();
//        assertTrue(result);
//
//    }
    @Test
    public void getDirToMine() throws GameActionException {
        when(rcMock.getLocation()).thenReturn(new MapLocation(6, 6));
        when(rcMock.canSenseLocation(new MapLocation(6, 7))).thenReturn(true);
        when(rcMock.senseSoup(new MapLocation(6, 6))).thenReturn(1);
        when(rcMock.canMineSoup(Direction.CENTER)).thenReturn(true);
        minerMock.getDirToMine();
        verify(rcMock).getLocation();
        verify(rcMock).canSenseLocation(new MapLocation(6, 7));
    }

//    @Test
//    public void checkIfSoupGone() throws GameActionException{
//        MapLocation soupLocation = new MapLocation(6,6);
//        when(minerMock.soupLocations.size()).thenReturn(1);
//        when(minerMock.soupLocations.contains(soupLocation)).thenReturn(true);
//        when(rcMock.canSenseLocation(soupLocation)).thenReturn(true);
//        when(rcMock.senseSoup(soupLocation)).thenReturn(0);
//        when(minerMock.soupLocations.remove(soupLocation)).thenReturn(true);
//        minerMock.checkIfSoupGone();
//        verify(rcMock).senseSoup(soupLocation);
//        verify(rcMock).canSenseLocation(soupLocation);
//
//    }


}