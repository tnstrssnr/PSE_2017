package edu.kit.pse17.go_app.ServiceLayer;


import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDaoImp;
import edu.kit.pse17.go_app.ServiceLayer.observer.*;
import edu.kit.pse17.go_app.TestData;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;

public class ObserverTest {

    private static final String GOEO_JSON = "{\"ID\":1,\"name\":\"lunch\",\"description\":\"test description\",\"start\":\"Aug 30, 3917 12:00:00 AM\",\"end\":\"Sep 1, 3917 12:00:00 AM\",\"lat\":0.0,\"lon\":0.0}";
    private static final String GORO_JSON = "{\"id\":\"1\"}";
    private static final String GEO_JSON = "{\"ID\":0,\"name\":\"Foo\",\"description\":\"Test Descritpion\"}";
    private static final String GRO_JSON = "{\"id\":\"0\"}";
    private static final String GRRO_JSON = "{\"ID\":0,\"name\":\"Foo\",\"description\":\"Test Descritpion\",\"members\":[{\"uid\":\"testid_1\",\"instanceId\":\"testInstance_1\",\"name\":\"Bob\",\"email\":\"bob@testmail.com\"},{\"uid\":\"testid_2\",\"instanceId\":\"testInstance_2\",\"name\":\"Alice\",\"email\":\"alice@testmail.com\"}],\"admins\":[{\"uid\":\"testid_1\",\"instanceId\":\"testInstance_1\",\"name\":\"Bob\",\"email\":\"bob@testmail.com\"}],\"requests\":[],\"gos\":[{\"ID\":1,\"owner\":{\"uid\":\"testid_1\",\"instanceId\":\"testInstance_1\",\"name\":\"Bob\",\"email\":\"bob@testmail.com\"},\"name\":\"lunch\",\"description\":\"test description\",\"start\":\"Aug 30, 3917 12:00:00 AM\",\"end\":\"Sep 1, 3917 12:00:00 AM\",\"lat\":0.0,\"lon\":0.0,\"goingUsers\":[],\"notGoingUsers\":[],\"goneUsers\":[]},{\"ID\":2,\"owner\":{\"uid\":\"testid_2\",\"instanceId\":\"testInstance_2\",\"name\":\"Alice\",\"email\":\"alice@testmail.com\"},\"name\":\"dinner\",\"description\":\"test description\",\"start\":\"Aug 30, 3917 12:00:00 AM\",\"end\":\"Sep 1, 3917 12:00:00 AM\",\"lat\":0,\"lon\":0,\"goingUsers\":[{\"uid\":\"testid_2\",\"instanceId\":\"testInstance_2\",\"name\":\"Alice\",\"email\":\"alice@testmail.com\"}],\"notGoingUsers\":[{\"uid\":\"testid_1\",\"instanceId\":\"testInstance_1\",\"name\":\"Bob\",\"email\":\"bob@testmail.com\"}],\"goneUsers\":[]}]}";
    private static final String MAO_JSON = "{\"uid\":\"testid_1\",\"instanceId\":\"testInstance_1\",\"name\":\"Bob\",\"email\":\"bob@testmail.com\"}";
    private static final String MRO_JSON = "{\"user_id\":\"testid_1\",\"group_id\":0}";
    private static final String SCO_JSON = "{\"user_id\":\"testid_1\",\"go_id\":1,\"status\":0}";

    private static Set<UserEntity> allGroupMembers;
    private static Set<UserEntity> adminsPlusNewMember;


    private FcmClient mockMessenger;
    private GroupService mockGroupService;
    private GoService mockGoService;
    private UserService mockUserService;
    private UserDaoImp mockUserDao;
    private GroupEntity testGroup;
    private GoEntity testGo;
    private UserEntity testUser;

    private GroupEditedObserver geo;
    private AdminAddedObserver aao;
    private GoRemovedObserver goro;
    private GoEditedObserver goeo;
    private GroupRemovedObserver gro;
    private GroupRequestReceivedObserver grro;
    private MemberAddedObserver mao;
    private MemberRemovedObserver mro;
    private StatusChangedObserver sco;


    private String resultData;
    private EventArg resultArg;
    private Set<UserEntity> resultReceiver;

    @Before
    public void setUp() {
        this.testGroup = TestData.getTestGroupFoo();
        this.testGo = TestData.getTestGoLunch();
        this.testUser = TestData.getTestUserBob();
        allGroupMembers = TestData.getTestGroupFoo().getMembers();

        this.mockGroupService = Mockito.mock(GroupService.class);
        Mockito.when(mockGroupService.getGroupById(anyLong())).thenReturn(testGroup);

        this.mockGoService = Mockito.mock(GoService.class);
        Mockito.when(mockGoService.getGoById(anyLong())).thenReturn(testGo);

        this.mockUserDao = Mockito.mock(UserDaoImp.class);
        Mockito.when(mockUserDao.get(anyString())).thenReturn(this.testUser);

        this.mockMessenger = Mockito.mock(FcmClient.class);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                System.out.println(invocationOnMock.getArguments().length);
                resultData = (String) invocationOnMock.getArguments()[0];
                resultArg = (EventArg) invocationOnMock.getArguments()[1];
                resultReceiver = (Set<UserEntity>) invocationOnMock.getArguments()[2];
                return null;
            }
        }).when(mockMessenger).send(anyString(), any(EventArg.class), any(Set.class));

        this.aao = new AdminAddedObserver(mockMessenger, mockGroupService);
        this.geo = new GroupEditedObserver(mockMessenger, mockGroupService);
        this.goro = new GoRemovedObserver(mockMessenger, mockGoService);
        this.goeo = new GoEditedObserver(mockMessenger, mockGoService);
        this.gro = new GroupRemovedObserver(mockMessenger, mockGroupService);
        this.grro = new GroupRequestReceivedObserver(mockGroupService);
        this.mao = new MemberAddedObserver(mockMessenger, mockGroupService);
        this.mro = new MemberRemovedObserver(mockMessenger, mockGroupService);
        this.sco = new StatusChangedObserver(mockMessenger, mockGoService);
    }

    @After
    public void tearDown() {
        this.geo = null;
        this.aao = null;
        this.goro = null;
        this.goeo = null;
        this.gro = null;
        this.grro = null;
        this.mao = null;
        this.mro = null;
        this.sco = null;
        this.testGroup = null;
        this.mockMessenger = null;
        this.mockGroupService = null;
        this.mockGoService = null;
        this.resultArg = null;
        this.resultData = null;
        this.resultReceiver = null;
        this.testGo = null;
        this.testGroup = null;
        this.testUser = null;
        allGroupMembers = null;

    }

    public void checkResults(String expectedJson, EventArg expectedArg, Set<UserEntity> expectedReceiver) {
        assertEquals(expectedJson, resultData);
        assertEquals(expectedArg, resultArg);
        Assert.assertNotEquals(resultReceiver, null);
        if (resultReceiver != null) {
            Assert.assertArrayEquals(expectedReceiver.toArray(), resultReceiver.toArray());
        }
    }

    @Test
    public void goEditedObserverTest() {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(testGo.getID()));

        goeo.update(entity_ids);
        checkResults(GOEO_JSON, EventArg.GO_EDITED_EVENT, allGroupMembers);
    }

    @Test
    public void goRemovedObserverTest() {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(testGo.getID()));

        goro.update(entity_ids);
        checkResults(GORO_JSON, EventArg.GO_REMOVED_EVENT, allGroupMembers);
    }

    @Test
    public void groupEditedObserverTest() {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(testGroup.getID()));

        geo.update(entity_ids);
        checkResults(GEO_JSON, EventArg.GROUP_EDITED_EVENT, allGroupMembers);

    }

    @Test
    public void groupRemovedObserverTest() {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(testGroup.getID()));

        gro.update(entity_ids);
        checkResults(GRO_JSON, EventArg.GROUP_REMOVED_EVENT, allGroupMembers);
    }

    @Test
    public void groupRequestReceivedObserverTest() {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(testUser.getUid());
        entity_ids.add(String.valueOf(testGroup.getID()));

        grro.update(entity_ids);
        checkResults(GRRO_JSON, EventArg.GROUP_REQUEST_RECEIVED_EVENT, adminsPlusNewMember);
    }

    @Test
    public void memberAddedObserverTest() {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(testUser.getUid());
        entity_ids.add(String.valueOf(testGroup.getID()));
        System.out.println("Length" + String.valueOf(entity_ids.size()));

        mao.update(entity_ids);
        checkResults(MAO_JSON, EventArg.MEMBER_ADDED_EVENT, allGroupMembers);
    }

    @Test
    public void memberRemovedObserverTest() {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(testUser.getUid());
        entity_ids.add(String.valueOf(testGroup.getID()));

        mro.update(entity_ids);
        checkResults(MRO_JSON, EventArg.MEMBER_REMOVED_EVENT, allGroupMembers);
    }

    @Test
    public void statusChangedObserverTest() {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(testUser.getUid());
        entity_ids.add(String.valueOf(testGo.getID()));

        sco.update(entity_ids);
        checkResults(SCO_JSON, EventArg.STATUS_CHANGED_EVENT, allGroupMembers);
    }


}
