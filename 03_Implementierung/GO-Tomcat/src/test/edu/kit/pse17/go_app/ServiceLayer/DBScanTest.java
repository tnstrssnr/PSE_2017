package edu.kit.pse17.go_app.ServiceLayer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.*;

public class DBScanTest {

    private DBScan newScan;
    private List<UserLocation> userLocationList;
    private UserLocation loc1;
    private UserLocation loc2;
    private UserLocation loc3;
    private UserLocation loc4;


    private Utility mockUtility;
    private List<UserLocation> mockList;
    private Vector<UserLocation> mockVector;

    private Vector<UserLocation> testVector;


    @Before
    public void setUp() throws Exception {

        this.mockUtility = Mockito.mock(Utility.class);
        this.mockVector = Mockito.mock(Vector.class);
        this.mockList = Mockito.mock(List.class);

        this.userLocationList = new ArrayList<UserLocation>();
        this.testVector = new Vector<UserLocation>();

        this.loc1 = new UserLocation("loc1", 4, 4);
        this.loc2 = new UserLocation("loc2", 5, 5);
        this.loc3 = new UserLocation("loc3", 6, 6);
        this.loc4 = new UserLocation("loc4", 7, 7);


    }

    @Test
    public void applyDbscan() throws Exception {


        userLocationList.add(loc1);
        userLocationList.add(loc2);
        userLocationList.add(loc3);
        userLocationList.add(loc4);

        Vector<UserLocation> testNeighbours1 = new Vector<UserLocation>();
        testNeighbours1.add(loc2);
        testNeighbours1.add(loc3);
        testNeighbours1.add(loc4);

        Vector<UserLocation> testNeighbours2 = new Vector<UserLocation>();
        testNeighbours1.add(loc1);
        testNeighbours1.add(loc3);
        testNeighbours1.add(loc4);

        Vector<UserLocation> testNeighbours3 = new Vector<UserLocation>();
        testNeighbours1.add(loc1);
        testNeighbours1.add(loc2);
        testNeighbours1.add(loc4);

        Vector<UserLocation> testNeighbours4 = new Vector<UserLocation>();
        testNeighbours1.add(loc1);
        testNeighbours1.add(loc2);
        testNeighbours1.add(loc3);

        Vector<UserLocation> testUserList = new Vector<UserLocation>();
        testUserList.addAll(userLocationList);


        Mockito.when(mockUtility.getList(mockList)).thenReturn(testUserList);

        newScan = new DBScan(userLocationList);

        Mockito.when(Utility.isVisited(loc1)).thenReturn(false).thenReturn(true);
        Mockito.when(Utility.isVisited(loc2)).thenReturn(false).thenReturn(true);
        Mockito.when(Utility.isVisited(loc3)).thenReturn(false).thenReturn(true);
        Mockito.when(Utility.isVisited(loc4)).thenReturn(false).thenReturn(true);

        Mockito.when(Utility.getNeighbours(loc1, 3)).thenReturn(testNeighbours1);
        Mockito.when(Utility.getNeighbours(loc2, 3)).thenReturn(testNeighbours2);
        Mockito.when(Utility.getNeighbours(loc3, 3)).thenReturn(testNeighbours3);
        Mockito.when(Utility.getNeighbours(loc4, 3)).thenReturn(testNeighbours4);

        Vector<List> testResults = newScan.applyDbscan(3, 3, testUserList);

        Vector<List> expectedResults = new Vector<List>();
        expectedResults.add(testUserList);

        assertSame(testResults, expectedResults);
    }

    @After
    public void tearDown() throws Exception {
    }

}