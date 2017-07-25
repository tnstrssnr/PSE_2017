package edu.kit.pse17.go_app.ServiceLayer.observer;


import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GoDaoImp;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDaoImp;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDaoImp;
import edu.kit.pse17.go_app.TestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

public class OberverTest {

    private static final String GEO_JSON = "";
    private static final String AAO_JSON = "";
    private static final String GORO_JSON = "";
    private static final String GOEO_JSON = "";
    private static final String GRO_JSON = "";
    private static final String GRRO_JSON = "";
    private static final String MAO_JSON = "";
    private static final String MRO_JSON = "";
    private static final String SCO_JSON = "";

    private static Set<UserEntity> allGroupMembers;
    private static Set<UserEntity> adminsPlusNewMember;


    private FcmClient mockMessenger;
    private GroupDaoImp mockGroupDao;
    private GoDaoImp mockGoDao;
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
    private void setUp() {
        this.testGroup = TestData.getTestGroupFoo();
        this.testGo = TestData.getTestGoLunch();
        this.testUser = TestData.getTestUserBob();

        this.mockGroupDao = Mockito.mock(GroupDaoImp.class);
        Mockito.when(mockGroupDao.get(testGroup.getID())).thenReturn(testGroup);

        this.mockGoDao = Mockito.mock(GoDaoImp.class);
        Mockito.when(mockGoDao.get(testGo.getID())).thenReturn(testGo);

        this.mockUserDao = Mockito.mock(UserDaoImp.class);
        Mockito.when(mockUserDao.get(testUser.getUid())).thenReturn(this.testUser);

        this.mockMessenger = Mockito.mock(FcmClient.class);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                resultData = (String) invocationOnMock.getArguments()[0];
                resultArg = (EventArg) invocationOnMock.getArguments()[1];
                resultReceiver = (Set<UserEntity>) invocationOnMock.getArguments()[2];
                return null;
            }
        }).when(mockMessenger).send(anyString(), any(EventArg.class), any(Set.class));

        this.aao = new AdminAddedObserver(mockMessenger, mockGroupDao);
        this.geo = new GroupEditedObserver(mockMessenger, mockGroupDao);
        this.goro = new GoRemovedObserver(mockMessenger, mockGoDao);
        this.goeo = new GoEditedObserver(mockMessenger, mockGoDao);
        this.gro = new GroupRemovedObserver(mockMessenger, mockGroupDao);
        this.grro = new GroupRequestReceivedObserver(mockMessenger, mockGroupDao, mockUserDao);
        this.mao = new MemberAddedObserver(mockMessenger, mockUserDao);
        this.mro = new MemberRemovedObserver(mockMessenger, mockGroupDao);
    }

    @After
    private void tearDown() {
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
        this.mockGroupDao = null;
        this.resultArg = null;
        this.resultData = null;
        this.resultReceiver = null;

    }

    private void checkResults(String expectedJson, EventArg expectedArg, Set<UserEntity> expectedReceiver) {
        assertEquals(expectedJson, resultData);
        assertEquals(expectedArg, resultArg);
        assertArrayEquals(expectedReceiver.toArray(), resultReceiver.toArray());
    }

    @Test
    public void adminAddedObserverTest() {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(this.testUser.getUid());
        entity_ids.add(String.valueOf(this.testGroup.getID()));

        this.aao.update(entity_ids);

        checkResults(AAO_JSON, EventArg.ADMIN_ADDED_EVENT, allGroupMembers);
    }

    @Test
    private void goEditedObserverTest() {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(testGo.getID()));

        goeo.update(entity_ids);
        checkResults(GOEO_JSON, EventArg.GO_EDITED_COMMAND, allGroupMembers);
    }

    @Test
    private void goRemovedObserverTest() {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(testGo.getID()));

        goro.update(entity_ids);
        checkResults(GORO_JSON, EventArg.GO_REMOVED_EVENT, allGroupMembers);
    }

    @Test
    private void groupEditedObserverTest() {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(testGroup.getID()));

        geo.update(entity_ids);
        checkResults(GEO_JSON, EventArg.GROUP_EDITED_COMMAND, allGroupMembers);

    }

    @Test
    private void groupRemovedObserverTest() {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(testGroup.getID()));

        gro.update(entity_ids);
        checkResults(GRO_JSON, EventArg.GROUP_REMOVED_EVENT, allGroupMembers);
    }

    @Test
    private void groupRequestReceivedObserverTest() {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(testUser.getUid());
        entity_ids.add(String.valueOf(testGroup.getID()));

        grro.update(entity_ids);
        checkResults(GRRO_JSON, EventArg.GROUP_REQUEST_RECEIVED_EVENT, adminsPlusNewMember);
    }

    @Test
    private void memberAddedObserverTest() {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(testUser.getUid());
        entity_ids.add(String.valueOf(testGroup.getID()));

        mao.update(entity_ids);
        checkResults(MAO_JSON, EventArg.MEMBER_ADDED_EVENT, allGroupMembers);
    }

    @Test
    private void memberRemovedObserverTest() {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(testUser.getUid());
        entity_ids.add(String.valueOf(testGroup.getID()));

        mro.update(entity_ids);
        checkResults(MRO_JSON, EventArg.MEMBER_REMOVED_EVENT, allGroupMembers);
    }

    @Test
    private void statusChangedObserverTest() {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(testUser.getUid());
        entity_ids.add(String.valueOf(testGo.getID()));

        sco.update(entity_ids);
        checkResults(SCO_JSON, EventArg.STATUS_CHANGED_COMMAND, allGroupMembers);
    }



}
