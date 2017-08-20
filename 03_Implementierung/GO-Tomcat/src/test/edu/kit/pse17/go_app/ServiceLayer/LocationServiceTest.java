package edu.kit.pse17.go_app.ServiceLayer;

import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Go;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.Assert.*;

public class LocationServiceTest {

    private LocationService testLocationService;

    private GoEntity testEntity;

    private GoService mockGoService;

    private Map<Long, LocationService> mockMap;

    private Long testLat;
    private Long testLon;
    private Long testGoId;
    private String testUserId;

    private Go testGo;

    @Before
    public void setUp() throws Exception {
        this.testGoId = new Long(3);
        this.testLon = new Long(5);
        this.testLat = new Long(5);
        this.testUserId = new String ("testUser");

        this.testGo = new Go();
        this.testEntity = new GoEntity();
        this.mockGoService = Mockito.mock(GoService.class);
        this.testLocationService = new LocationService(testEntity);
        this.mockMap = Mockito.mock(Map.class);
        Mockito.when(GoService.getGoById(testGoId)).thenReturn(testEntity);


    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void setUserLocation() throws Exception {


        testLocationService.setUserLocation(testGoId, testUserId, testLat, testLon);
    }

    @Test
    public void getGroupLocation() throws Exception {



    }

    @Test
    public void removeGo() throws Exception {
        //Mockito.when(testLocationService.activeServices.get(testGoId)).then(true);
        //Mockito.when(testLocationService.activeServices.remove(testGoId)).then(true);
    }

}