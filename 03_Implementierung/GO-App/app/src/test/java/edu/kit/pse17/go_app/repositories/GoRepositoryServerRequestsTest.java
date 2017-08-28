package edu.kit.pse17.go_app.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import edu.kit.pse17.go_app.model.Status;
import edu.kit.pse17.go_app.model.entities.Cluster;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.model.entities.GroupMembership;
import edu.kit.pse17.go_app.model.entities.UserGoStatus;
import edu.kit.pse17.go_app.viewModel.livedata.GroupListLiveData;

import static org.junit.Assert.assertEquals;

/**
 * Created by Сеня on 28.08.2017.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GoRepositoryServerRequestsTest {
    private GoRepository goRepo;
    private GroupRepository groupRepo;
    private GroupListLiveData data;
    private long goId = 32;
    private long groupId = 38;

    @Before
    public void setUp() throws InterruptedException {
        groupRepo = GroupRepository.getInstance();
        goRepo = GoRepository.getInstance();

        ArrayList<Group> list = new ArrayList<>();
        list.add(getMockedGroup());
        ArrayList<Go> goList = new ArrayList<>();
        goList.add(getMockedGo());
        list.get(0).setCurrentGos(goList);
        data = Mockito.spy(new GroupListLiveData());
        Mockito.when(data.getValue()).thenReturn(list);
        groupRepo.setData(data);
    }

    @After
    public void tearDown() {
        data = null;
        groupRepo = null;
        goRepo = null;
    }

    @Test
    public void a_changeStatusTest() throws InterruptedException {
        Go go = getMockedGo();
        groupRepo.createGo(go.getName(), go.getDescription(), go.getStart(),
                go.getEnd(), go.getDesLat(), go.getDesLon(), -1, go.getGroup(),
                go.getOwner(), go.getOwnerName());
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        String userId = "MLsXON00mUPSQvKi7DggE6lTAsq1";
        goRepo.changeStatus(userId, goId, Status.GOING);
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        /* 200 is HTTP OK status code */
        assertEquals(goRepo.getResponseStatus(), 200);
    }

    @Test
    public void b_editGoTest() throws InterruptedException {
        String userId = "c1S8oa3nARUDexOfL1fTdhgeB5V2";
        Go go = getMockedEditedGo();
        double[] latLon = {go.getDesLat(), go.getDesLon()};
        goRepo.editGo(goId, groupId, userId, go.getName(), go.getDescription(),
                go.getStart(), go.getEnd(), latLon, -1);
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        /* 200 is HTTP OK status code */
        assertEquals(goRepo.getResponseStatus(), 200);
    }

    @Test
    public void c_getLocationTest() throws InterruptedException {
        String userId = "c1S8oa3nARUDexOfL1fTdhgeB5V2";
        goRepo.getLocation(userId, goId, 27.000, 137.000);
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        /* 200 is HTTP OK status code */
        assertEquals(goRepo.getResponseStatus(), 200);
    }

    @Test
    public void d_deleteGoTest() throws InterruptedException {
        goRepo.deleteGo(goId);
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        /* 200 is HTTP OK status code */
        assertEquals(goRepo.getResponseStatus(), 200);
    }

    private Go getMockedEditedGo() {
        String goName = "EditedTestGo";
        String description = "EditedTestDescriptionGo";
        String start = "25.12.2017, 12:00";
        String end = "25.12.2017, 13:00";
        Group group = getMockedGroup();
        group.setId(groupId);
        double lat = 0.00000;
        double lon = 107.00000;
        String userId = "c1S8oa3nARUDexOfL1fTdhgeB5V2";
        String userName = "Gruppe3 PSE";
        Go go = new Go(goId, goName, description, start, end, group, lat, lon, userId, userName, new ArrayList<UserGoStatus>(), new ArrayList<Cluster>());
        return go;
    }

    private Group getMockedGroup() {
        String groupName = "TestGroup";
        String description = "TestDescription";
        Group group = new Group(groupId, groupName, description, 1, null, new ArrayList<GroupMembership>(), new ArrayList<Go>());
        return group;
    }

    private Go getMockedGo() {
        String goName = "TestGo";
        String description = "TestDescriptionGo";
        String start = "25.12.2017, 12:00";
        String end = "25.12.2017, 13:00";
        Group group = getMockedGroup();
        double lat = 0.00000;
        double lon = 14.00000;
        String userId = "c1S8oa3nARUDexOfL1fTdhgeB5V2";
        String userName = "Gruppe3 PSE";
        Go go = new Go(goId, goName, description, start, end, group, lat, lon, userId, userName, new ArrayList<UserGoStatus>(), new ArrayList<Cluster>());
        return go;
    }
}
