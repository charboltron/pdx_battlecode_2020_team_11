package master_player;

import battlecode.common.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class HQTest {


    RobotController rcMock;
    HQ hqMock;

    RobotController rc = new RobotController() {
        @Override
        public int getRoundNum() {
            return 0;
        }

        @Override
        public int getTeamSoup() {
            return 0;
        }

        @Override
        public int getMapWidth() {
            return 0;
        }

        @Override
        public int getMapHeight() {
            return 0;
        }

        @Override
        public int getID() {
            return 0;
        }

        @Override
        public Team getTeam() {
            return null;
        }

        @Override
        public RobotType getType() {
            return null;
        }

        @Override
        public MapLocation getLocation() {
            return null;
        }

        @Override
        public int getSoupCarrying() {
            return 0;
        }

        @Override
        public int getDirtCarrying() {
            return 0;
        }

        @Override
        public boolean isCurrentlyHoldingUnit() {
            return false;
        }

        @Override
        public int getCurrentSensorRadiusSquared() {
            return 0;
        }

        @Override
        public boolean onTheMap(MapLocation mapLocation) {
            return false;
        }

        @Override
        public boolean canSenseLocation(MapLocation mapLocation) {
            return false;
        }

        @Override
        public boolean canSenseRadiusSquared(int i) {
            return false;
        }

        @Override
        public boolean isLocationOccupied(MapLocation mapLocation) throws GameActionException {
            return false;
        }

        @Override
        public RobotInfo senseRobotAtLocation(MapLocation mapLocation) throws GameActionException {
            return null;
        }

        @Override
        public boolean canSenseRobot(int i) {
            return false;
        }

        @Override
        public RobotInfo senseRobot(int i) throws GameActionException {
            return null;
        }

        @Override
        public RobotInfo[] senseNearbyRobots() {
            return new RobotInfo[0];
        }

        @Override
        public RobotInfo[] senseNearbyRobots(int i) {
            return new RobotInfo[0];
        }

        @Override
        public RobotInfo[] senseNearbyRobots(int i, Team team) {
            return new RobotInfo[0];
        }

        @Override
        public RobotInfo[] senseNearbyRobots(MapLocation mapLocation, int i, Team team) {
            return new RobotInfo[0];
        }

        @Override
        public MapLocation[] senseNearbySoup() {
            return new MapLocation[0];
        }

        @Override
        public MapLocation[] senseNearbySoup(int i) {
            return new MapLocation[0];
        }

        @Override
        public MapLocation[] senseNearbySoup(MapLocation mapLocation, int i) {
            return new MapLocation[0];
        }

        @Override
        public int senseSoup(MapLocation mapLocation) throws GameActionException {
            return 0;
        }

        @Override
        public int sensePollution(MapLocation mapLocation) throws GameActionException {
            return 0;
        }

        @Override
        public int senseElevation(MapLocation mapLocation) throws GameActionException {
            return 0;
        }

        @Override
        public boolean senseFlooding(MapLocation mapLocation) throws GameActionException {
            return false;
        }

        @Override
        public MapLocation adjacentLocation(Direction direction) {
            return null;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public float getCooldownTurns() {
            return 0;
        }

        @Override
        public boolean canMove(Direction direction) {
            return false;
        }

        @Override
        public void move(Direction direction) throws GameActionException {

        }

        @Override
        public boolean canBuildRobot(RobotType robotType, Direction direction) {
            return false;
        }

        @Override
        public void buildRobot(RobotType robotType, Direction direction) throws GameActionException {

        }

        @Override
        public boolean canMineSoup(Direction direction) {
            return false;
        }

        @Override
        public void mineSoup(Direction direction) throws GameActionException {

        }

        @Override
        public boolean canDepositSoup(Direction direction) {
            return false;
        }

        @Override
        public void depositSoup(Direction direction, int i) throws GameActionException {

        }

        @Override
        public boolean canDigDirt(Direction direction) {
            return false;
        }

        @Override
        public void digDirt(Direction direction) throws GameActionException {

        }

        @Override
        public boolean canDepositDirt(Direction direction) {
            return false;
        }

        @Override
        public void depositDirt(Direction direction) throws GameActionException {

        }

        @Override
        public boolean canPickUpUnit(int i) {
            return false;
        }

        @Override
        public void pickUpUnit(int i) throws GameActionException {

        }

        @Override
        public boolean canDropUnit(Direction direction) {
            return false;
        }

        @Override
        public void dropUnit(Direction direction) throws GameActionException {

        }

        @Override
        public boolean canShootUnit(int i) {
            return false;
        }

        @Override
        public void shootUnit(int i) throws GameActionException {

        }

        @Override
        public void disintegrate() {

        }

        @Override
        public void resign() {

        }

        @Override
        public boolean canSubmitTransaction(int[] ints, int i) {
            return false;
        }

        @Override
        public void submitTransaction(int[] ints, int i) throws GameActionException {

        }

        @Override
        public Transaction[] getBlock(int i) throws GameActionException {
            return new Transaction[0];
        }

        @Override
        public void setIndicatorDot(MapLocation mapLocation, int i, int i1, int i2) {

        }

        @Override
        public void setIndicatorLine(MapLocation mapLocation, MapLocation mapLocation1, int i, int i1, int i2) {

        }
    };
    HQ hq = new HQ(rc);

    public HQTest() throws GameActionException {
    }

    @Before
    public void create() throws GameActionException {

        rcMock = mock(RobotController.class);
        hqMock = mock(HQ.class);
        hqMock.comms = mock(Communications.class);

        when(rcMock.getTeam()).thenReturn(Team.A);
        when(rcMock.getType()).thenReturn(RobotType.HQ);

    }


    @Test
    public void takeTurnWorksAsExpected() throws GameActionException {

        hq.turnCount = 0;
        hq.takeTurn();
        assertEquals(1, hq.turnCount);

    }

    @Test
    public void getSoupInRadiusDoesntThrowAnyErrors() throws GameActionException {

        hq.getSoupInRadius();
    }

}