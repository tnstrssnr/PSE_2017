package edu.kit.pse17.go_app.ServiceLayer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.*;

public class UtilityTest {

    private Utility testUtility;
    private List<UserLocation> userLocationList;
    private UserLocation loc1;
    private UserLocation loc2;
    private UserLocation loc3;

    @Before
    public void setUp() throws Exception {
        this.testUtility = new Utility();
        this.userLocationList = new ArrayList<>();
        this.loc1 = new UserLocation("loc1", 3, 5);
        this.loc2 = new UserLocation("loc2", 6, 1);
        this.loc3 = new UserLocation("loc3", 3, 5);
    }


    @Test
    public void getDistance() throws Exception {
        assertEquals(testUtility.getDistance(loc1, loc2), 5, 0);
    }

    @Test
    public void getNeighbours() throws Exception {
        userLocationList.add(loc1);
        userLocationList.add(loc2);
        userLocationList.add(loc3);
        Iterator<UserLocation> mockIter = Mockito.mock(Iterator.class);
        DBScan.pointList = Mockito.mock(Vector.class);
        Mockito.when(DBScan.pointList.iterator()).thenReturn(userLocationList.iterator());
        testUtility.getNeighbours(loc1, 4);
        //verify(mockIter);

    }

    @Test
    public void visited() throws Exception {
        testUtility.VisitList.clear();
        testUtility.visited(loc1);
        assertSame(testUtility.VisitList.get(0), loc1);
    }

    @Test
    public void isVisited() throws Exception {
        testUtility.VisitList.clear();
        testUtility.visited(loc1);
        assertTrue(testUtility.isVisited(loc1));
        testUtility.VisitList.clear();
        assertFalse(testUtility.isVisited(loc1));
    }

    @Test
    public void merge() throws Exception {
        Vector<UserLocation> testVector1 = new Vector<>();
        Vector<UserLocation> testVector2 = new Vector<>();
        testVector1.add(loc1);
        testVector2.add(loc2);
        testVector2.add(loc3);
        Vector<UserLocation> testVector3 = testUtility.merge(testVector1, testVector2);
        assertSame(testVector3.get(0), loc1);
        assertSame(testVector3.get(1), loc2);
        assertSame(testVector3.get(2), loc3);

    }

    @Test
    public void getList() throws Exception {
        userLocationList.add(loc1);
        userLocationList.add(loc2);
        userLocationList.add(loc3);
        Vector<UserLocation> testVector = testUtility.getList(userLocationList);
        assertSame(testVector.get(0), loc1);
        assertSame(testVector.get(1), loc2);
        assertSame(testVector.get(2), loc3);
    }

    @Test
    public void equalPoints() throws Exception {
        assertTrue(testUtility.equalPoints(loc1, loc3));
        assertFalse(testUtility.equalPoints(loc1, loc2));
    }

    @After
    public void tearDown() throws Exception {
        this.testUtility = null;
        this.userLocationList = null;
        this.loc1 = null;
        this.loc2 = null;
        this.loc3 = null;
    }
}