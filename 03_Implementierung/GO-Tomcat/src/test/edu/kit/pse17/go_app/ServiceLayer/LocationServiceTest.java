package edu.kit.pse17.go_app.ServiceLayer;

import org.junit.Before;
import org.junit.Test;
import java.util.Map;

import static org.junit.Assert.*;

public class LocationServiceTest {

    private LocationService testLocationService;

    private Map<Long, LocationService> testMap;


    @Before
    public void setUp() throws Exception {
        this.testLocationService = new LocationService();

    }

    @Test
    public void allMethods() throws Exception {


        testLocationService.setUserLocation(5, "Jerold", 3, 6);
        testLocationService.setUserLocation(5, "Panther", 5, 5);
        testLocationService.setUserLocation(6, "Jerold", 3, 6);
        testMap = testLocationService.getActiveServices();
        assertTrue(testMap.size() == 2);
        assertTrue(testMap.get((long)5).activeUsers.size() == 2);

        assertEquals(testMap.get((long)5).activeUsers.get(0).getLat(), 3, 0);
        assertEquals(testMap.get((long)5).activeUsers.get(0).getLon(), 6, 0);
        assertEquals(testMap.get((long)5).activeUsers.get(0).getUserId(), "Jerold");
        assertEquals(testMap.get((long)5).activeUsers.get(1).getLat(), 5, 0);
        assertEquals(testMap.get((long)5).activeUsers.get(1).getLon(), 5, 0);
        assertEquals(testMap.get((long)5).activeUsers.get(1).getUserId(), "Panther");
        testLocationService.setUserLocation(5, "Jerold",4, 5);
        testMap = testLocationService.getActiveServices();

        assertTrue(testMap.get((long)6).activeUsers.size() == 1);
        assertEquals(testMap.get((long)6).activeUsers.get(0).getLat(), 3, 0);
        assertEquals(testMap.get((long)6).activeUsers.get(0).getLon(), 6, 0);
        assertEquals(testMap.get((long)6).activeUsers.get(0).getUserId(), "Jerold");
        assertTrue(testMap.get((long)5).activeUsers.size() == 2);
        assertEquals(testMap.get((long)5).activeUsers.get(0).getLat(), 4, 0);
        assertEquals(testMap.get((long)5).activeUsers.get(0).getLon(), 5, 0);
        assertEquals(testMap.get((long)5).activeUsers.get(0).getUserId(), "Jerold");

        testLocationService.removeGo((long)6);
        testMap = testLocationService.getActiveServices();
        assertNull(testMap.get((long)6));

        testLocationService.removeUser("Jerold", (long)5);
        testMap = testLocationService.getActiveServices();
        assertEquals(testMap.get((long)5).activeUsers.size(), 1);

    }

}