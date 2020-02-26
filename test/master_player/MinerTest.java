package master_player;

import battlecode.common.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;


import java.util.ArrayList;

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
    @Spy
    ArrayList<MapLocation> soupLocations;
    MapLocation nearestSoup;
    @InjectMocks
    Miner minerMock = new Miner(rcMock);

    @Before
    public void setup() {
        when(rcMock.getTeam()).thenReturn(Team.A);
        when(rcMock.getType()).thenReturn(RobotType.HQ);
        nearestSoup = new MapLocation(6,6);
        soupLocations.add(nearestSoup);
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
    @Test
    public void getNearestRefinery(){
        minerMock.numRefineries = 1;
        when(rcMock.senseNearbyRobots()).thenReturn(new RobotInfo[]{new RobotInfo(1,Team.A,RobotType.REFINERY,0,false,0,0,0, new MapLocation(1,1))});
        minerMock.getNearestRefinery();
        verify(rcMock).senseNearbyRobots();
        verify(rcMock).getTeam();
    }

//    @Test
//    public void buildRefinery() throws GameActionException{
//        minerMock.teamSoup = 500;
//        minerMock.numRefineries = 1;
//        when(rcMock.isReady()).thenReturn(true);
//        when(hqLoc.isWithinDistanceSquared(rcMock.getLocation(), 30)).thenReturn(true);
//        when(!minerMock.nearestRefinery.isWithinDistanceSquared(rcMock.getLocation(), 25)).thenReturn(true);
//        when(minerMock.tryBuild(RobotType.REFINERY, Util.randomDirection())).thenReturn(true);
//        minerMock.buildRefinery();
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

    @Test
    public void checkIfSoupGone() throws GameActionException{
        minerMock.nearestSoup = new MapLocation(6,6);
        when(soupLocations.size()).thenReturn(1);
        when(soupLocations.contains(nearestSoup)).thenReturn(true);
        when(rcMock.canSenseLocation(new MapLocation(6, 6))).thenReturn(true);
        when(rcMock.senseSoup(new MapLocation(6,6))).thenReturn(0);
        when(soupLocations.remove(nearestSoup)).thenReturn(true);
        minerMock.checkIfSoupGone();
        verify(rcMock).senseSoup(new MapLocation(6,6));
        verify(rcMock).canSenseLocation(new MapLocation(6,6));
    }

//    @Test
//    public void buildVaporator() throws GameActionException {
//        minerMock.numRefineries = 1;
//        minerMock.mustBuildRefinery = false;
//        minerMock.numLandscapers = 6;
//        minerMock.numVaporators = 1;
//        when(rcMock.getTeamSoup()).thenReturn(550);
//        when(rcMock.isReady()).thenReturn(true);
//        when(rcMock.canBuildRobot(RobotType.VAPORATOR, Util.randomDirection())).thenReturn(true);
//        when(rcMock.getRoundNum()).thenReturn(400);
////        when(rcMock.buildRobot(RobotType.VAPORATOR, Util.randomDirection())).then(
////        doThrow(new GameActionException(GameActionExceptionType.CANT_DO_THAT, "I think")).when(rcMock).buildRobot(RobotType.VAPORATOR, Util.randomDirection());
////        doAnswer(new Answer() {
////            @Override
////            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
////                return null;
////            }
////        }).when(rcMock).buildRobot(RobotType.VAPORATOR, Util.randomDirection());
////        when(minerMock.tryBuild(RobotType.VAPORATOR, Util.randomDirection())).thenReturn(true);
//        boolean result = minerMock.buildVaporator();
//        assertFalse(result);
//    }

}