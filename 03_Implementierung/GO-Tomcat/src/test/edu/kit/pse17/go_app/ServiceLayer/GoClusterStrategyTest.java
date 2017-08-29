package edu.kit.pse17.go_app.ServiceLayer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.*;

public class GoClusterStrategyTest {

    private GoClusterStrategy testStrategy;
    private List<UserLocation> userLocationList;
    private UserLocation loc1;
    private UserLocation loc2;
    private UserLocation loc3;
    private UserLocation loc4;

    private Cluster testCluster;
    private  List<Cluster> testClusterList;

    private Vector<List> scanTestResults;
    private Vector<UserLocation> testLocations;

    private List<UserLocation> mockList;
    private DBScan mockScan;

    @Before
    public void setUp() throws Exception {
        this.testStrategy = new GoClusterStrategy();
        this.userLocationList = new ArrayList();
        this.scanTestResults = new Vector<List>();
        this.testLocations = new Vector<UserLocation>();

        this.testCluster = new Cluster(4,5, 5, 0);

        this.testClusterList = new ArrayList<Cluster>();

        this.mockList = Mockito.mock(List.class);


        this.loc1 = new UserLocation("loc1", 3, 3);
        this.loc2 = new UserLocation("loc2", 4, 2);
        this.loc3 = new UserLocation("loc3", 4, 4);
        this.loc4 = new UserLocation("loc4", 5, 3);
    }



    @Test
    public void calculateCluster() throws Exception {

        mockScan = Mockito.mock(DBScan.class);
        Mockito.when(mockScan.applyDbscan(4, 3, mockList)).thenReturn(scanTestResults);

        assertNull(testStrategy.calculateCluster(userLocationList));

        userLocationList.add(loc1);
        userLocationList.add(loc2);
        userLocationList.add(loc3);
        userLocationList.add(loc4);

        testLocations.addAll(userLocationList);
        scanTestResults.add(testLocations);
        testClusterList.add(testCluster);

        List<Cluster> testCalculations = testStrategy.calculateCluster(userLocationList);

        assertEquals(testCalculations.size(), testClusterList.size());
        assertEquals(testCalculations.get(0).getLat(), 4, 0);
        assertEquals(testCalculations.get(0).getLon(), 4, 0);
        assertEquals(testCalculations.get(0).getRadius(), 1, 0);
    }

    @After
    public void tearDown() throws Exception {
    }
}