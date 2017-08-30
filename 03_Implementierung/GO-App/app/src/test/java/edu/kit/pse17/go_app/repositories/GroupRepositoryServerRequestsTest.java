package edu.kit.pse17.go_app.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import edu.kit.pse17.go_app.model.entities.Cluster;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.model.entities.GroupMembership;
import edu.kit.pse17.go_app.model.entities.User;
import edu.kit.pse17.go_app.model.entities.UserGoStatus;
import edu.kit.pse17.go_app.viewModel.livedata.GroupListLiveData;

import static org.junit.Assert.assertEquals;

/**
 * Created by Сеня on 27.08.2017.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GroupRepositoryServerRequestsTest {
    private GroupRepository groupRepo;
    private User user;
    private GroupListLiveData data;
    private long groupId = 37;

    @Before
    public void setUp() {
        groupRepo = GroupRepository.getInstance();
        user = getMockedUser();

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
        groupRepo = null;
        user = null;
        data = null;
    }

    @Test
    public void a_getDataTest() throws InterruptedException {
        groupRepo.getData(user.getUid(), user.getEmail(), user.getInstanceId(), user.getName());
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        /* 200 is HTTP OK status code */
        assertEquals(groupRepo.getResponseStatus(), 200);
    }

    @Test
    public void b_registerDeviceTest() throws InterruptedException {
        groupRepo.registerDevice(user.getUid(), user.getInstanceId());
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        /* 200 is HTTP OK status code */
        assertEquals(groupRepo.getResponseStatus(), 200);
    }

    @Test
    public void c_createGroupTest() throws InterruptedException {
        String groupName = "TestGroup";
        String description = "TestDescription";

        groupRepo.createGroup(groupName, description, user);
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        /* 200 is HTTP OK status code */
        assertEquals(groupRepo.getResponseStatus(), 200);
    }

    @Test
    public void d_editGroupTest() throws InterruptedException {
        String name = "EditedTestGroup";
        String description = "EditedTestDescription";

        groupRepo.editGroup(groupId, name, description);
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        /* 200 is HTTP OK status code */
        assertEquals(groupRepo.getResponseStatus(), 200);
    }

    @Test
    public void j_deleteGroupTest() throws InterruptedException {
        groupRepo.deleteGroup(groupId);
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        /* 200 is HTTP OK status code */
        assertEquals(groupRepo.getResponseStatus(), 200);
    }

    @Test
    public void f_answerGroupRequestTrueTest() throws InterruptedException {
        String userId = "MLsXON00mUPSQvKi7DggE6lTAsq1";
        groupRepo.answerGroupRequest(groupId, userId, true);
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        /* 200 is HTTP OK status code */
        assertEquals(groupRepo.getResponseStatus(), 200);
    }

    @Test
    public void h_answerGroupRequestFalseTest() throws InterruptedException {
        e_inviteMemberTest();
        String userId = "MLsXON00mUPSQvKi7DggE6lTAsq1";
        groupRepo.answerGroupRequest(groupId, userId, false);
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        /* 200 is HTTP OK status code */
        assertEquals(groupRepo.getResponseStatus(), 200);
    }

    @Test
    public void g_removeMemberTest() throws InterruptedException {
        String userId = "MLsXON00mUPSQvKi7DggE6lTAsq1";
        groupRepo.removeMember(groupId, userId);
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        /* 200 is HTTP OK status code */
        assertEquals(groupRepo.getResponseStatus(), 200);
    }

    @Test
    public void e_inviteMemberTest() throws InterruptedException {
        String email = "vovaspilka1@gmail.com";

        groupRepo.inviteMember(groupId, email);
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        /* 200 is HTTP OK status code */
        assertEquals(groupRepo.getResponseStatus(), 200);
    }

    @Ignore
    @Test
    public void x_addAdminTest() throws InterruptedException {
        String userId = "MLsXON00mUPSQvKi7DggE6lTAsq1";

        groupRepo.addAdmin(groupId, userId);
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        /* 200 is HTTP OK status code */
        assertEquals(groupRepo.getResponseStatus(), 200);
    }

    @Test
    public void i_createGoTest() throws InterruptedException {
        Go go = getMockedGo();

        groupRepo.createGo(go.getName(), go.getDescription(), go.getStart(),
                go.getEnd(), go.getDesLat(), go.getDesLon(), -1, go.getGroup(),
                go.getOwner(), go.getOwnerName());
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        /* 200 is HTTP OK status code */
        assertEquals(groupRepo.getResponseStatus(), 200);
    }

    private User getMockedUser() {
        String userId = "c1S8oa3nARUDexOfL1fTdhgeB5V2";
        String email = "psepse2017@gmail.com";
        String instanceId = "dPYeX9lZAtw:APA91bEZrNP7gV8wMRsPvvj1FYwCbTLXcToAqMKILKlMkLM4hanLBlGfyYkqS__n0Ff2RJt8Si3UFQG4DpFRi5Zfbi3jtfSD8Iz4iNOr9bWpP_3EwSVImQjsGp04uLUuf1ms8geOUZCb";
        String userName = "Gruppe3 PSE";
        User user = new User(userId, instanceId, userName, email);
        return user;
    }

    private Group getMockedGroup() {
        String groupName = "TestGroup";
        String description = "TestDescription";
        Group group = new Group(-123, groupName, description, 1, null, new ArrayList<GroupMembership>(), new ArrayList<Go>());
        return group;
    }

    private Go getMockedGo() {
        String goName = "TestGo";
        String description = "TestDescriptionGo";
        String start = "25.12.2017, 12:00";
        String end = "25.12.2017, 13:00";
        Group group = getMockedGroup();
        group.setId(groupId);
        double lat = 0.00000;
        double lon = 14.00000;
        String userId = "c1S8oa3nARUDexOfL1fTdhgeB5V2";
        String userName = "Gruppe3 PSE";
        Go go = new Go(-321, goName, description, start, end, group, lat, lon, userId, userName, new ArrayList<UserGoStatus>(), new ArrayList<Cluster>());
        return go;
    }
}
