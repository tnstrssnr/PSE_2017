package edu.kit.pse17.go_app.repositories;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.kit.pse17.go_app.model.Status;
import edu.kit.pse17.go_app.model.entities.Cluster;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.model.entities.User;
import edu.kit.pse17.go_app.model.entities.UserGoStatus;
import edu.kit.pse17.go_app.viewModel.livedata.GroupListLiveData;

import static org.junit.Assert.assertEquals;

/**
 * Created by Сеня on 28.08.2017.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GoRepositoryServerRequestsTest {
    private static GoRepository goRepo;
    private static GroupRepository groupRepo;
    private static GroupListLiveData data;
    private static User user;
    private static List<Group> list;
    private static long GROUP_ID;
    private static long GO_ID;

    @BeforeClass
    public static void setUpBeforeClass() throws InterruptedException {
        groupRepo = GroupRepository.getInstance();
        goRepo = GoRepository.getInstance();
        user = getMockedUser();

        list = new ArrayList<>();
        data = Mockito.spy(new GroupListLiveData());
        Mockito.when(data.getValue()).thenReturn(list);
        groupRepo.setData(data);

        createTestGroupOnServer();
        createTestGoOnServer();
    }

    /*@Before
    public void setUp() throws InterruptedException {
        groupRepo = GroupRepository.getInstance();
        goRepo = GoRepository.getInstance();
        user = getMockedUser();

        list = new ArrayList<>();
        data = Mockito.spy(new GroupListLiveData());
        Mockito.when(data.getValue()).thenReturn(list);
        groupRepo.setData(data);
    }*/

    /*@After
    public void tearDown() {
        data = null;
        groupRepo = null;
        goRepo = null;
        user = null;
    }*/

    @AfterClass
    public static void tearDownAfterClass() throws InterruptedException {
        deleteTestGroupOnServer();

        list = null;
        data = null;
        groupRepo = null;
        goRepo = null;
        user = null;

        GROUP_ID = 0;
        GO_ID = 0;
    }

    @Test
    public void a_changeStatusTest() throws InterruptedException {
        String userId = "MrHCqabe6MOCsRLwUgVOkjOpPGf1";
        goRepo.changeStatus(userId, GO_ID, Status.GOING);
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        // 200 is HTTP OK status code
        assertEquals(goRepo.getResponseStatus(), 200);
        goRepo.setResponseStatus(0);
    }

    @Test
    public void b_editGoTest() throws InterruptedException {
        Go go = getMockedGo();
        go.setName("EditedTestGo");
        go.setDescription("EditedTestDescriptionGo");
        go.setDesLat(107.00000);
        double[] latLon = {go.getDesLat(), go.getDesLon()};
        goRepo.editGo(GO_ID, GROUP_ID, user.getUid(), go.getName(), go.getDescription(),
                go.getStart(), go.getEnd(), latLon, -1);
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        // 200 is HTTP OK status code
        assertEquals(goRepo.getResponseStatus(), 200);
        goRepo.setResponseStatus(0);
    }

    @Test
    public void c_getLocationTest() throws InterruptedException {
        goRepo.getLocation(user.getUid(), GO_ID, 27.000, 57.000);
        TimeUnit.SECONDS.sleep(3); // wait for the response of the server

        // 200 is HTTP OK status code
        assertEquals(goRepo.getResponseStatus(), 200);
        goRepo.setResponseStatus(0);
    }

    @Test
    public void d_deleteGoTest() throws InterruptedException {
        goRepo.deleteGo(GO_ID);
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        // 200 is HTTP OK status code
        assertEquals(goRepo.getResponseStatus(), 200);
        goRepo.setResponseStatus(0);
    }

    private static void createTestGroupOnServer() throws InterruptedException {
        String groupName = "TestGroupForGo";
        String description = "TestDescriptionForGo";

        groupRepo.createGroup(groupName, description, user);
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        list = groupRepo.getList();
        GROUP_ID = list.get(0).getId();

        /* Add new member to the group */
        String userId = "MrHCqabe6MOCsRLwUgVOkjOpPGf1";
        String email = "gruppe3.pse@gmail.com";
        groupRepo.inviteMember(GROUP_ID, email);
        TimeUnit.SECONDS.sleep(1);
        groupRepo.answerGroupRequest(GROUP_ID, userId, true);
        TimeUnit.SECONDS.sleep(1);
    }

    private static void createTestGoOnServer() throws InterruptedException {
        Go go = getMockedGo();

        groupRepo.createGo(go.getName(), go.getDescription(), go.getStart(),
                go.getEnd(), go.getDesLat(), go.getDesLon(), -1, go.getGroup(),
                go.getOwner(), go.getOwnerName());
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        GO_ID = groupRepo.getGoWithoutId().getId();
    }

    private static void deleteTestGroupOnServer() throws InterruptedException {
        groupRepo.deleteGroup(GROUP_ID);
        TimeUnit.SECONDS.sleep(1);
    }

    private static User getMockedUser() {
        String userId = "c1S8oa3nARUDexOfL1fTdhgeB5V2";
        String email = "psepse2017@gmail.com";
        String instanceId = "dPYeX9lZAtw:APA91bEZrNP7gV8wMRsPvvj1FYwCbTLXcToAqMKILKlMkLM4hanLBlGfyYkqS__n0Ff2RJt8Si3UFQG4DpFRi5Zfbi3jtfSD8Iz4iNOr9bWpP_3EwSVImQjsGp04uLUuf1ms8geOUZCb";
        String userName = "Gruppe3 PSE";
        User user = new User(userId, email, userName, instanceId);
        return user;
    }

    private static Go getMockedGo() {
        String goName = "TestGo";
        String description = "TestDescriptionGo";
        String start = "25.12.2017, 12:00";
        String end = "25.12.2017, 13:00";
        Group group = new Group();
        group.setId(GROUP_ID);
        double lat = 0.00000;
        double lon = 14.00000;
        String userId = "c1S8oa3nARUDexOfL1fTdhgeB5V2";
        String userName = "Gruppe3 PSE";
        Go go = new Go(-321, goName, description, start, end, group, lat, lon, userId, userName, new ArrayList<UserGoStatus>(), new ArrayList<Cluster>());
        return go;
    }
}
