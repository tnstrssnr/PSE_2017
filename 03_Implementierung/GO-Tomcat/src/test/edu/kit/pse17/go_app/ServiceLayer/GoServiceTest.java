package edu.kit.pse17.go_app.ServiceLayer;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.Status;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Go;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.UserGoStatus;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GoDaoImp;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDaoImp;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDaoImp;
import edu.kit.pse17.go_app.ServiceLayer.observer.GoAddedObserver;
import edu.kit.pse17.go_app.ServiceLayer.observer.GoEditedObserver;
import edu.kit.pse17.go_app.ServiceLayer.observer.GoRemovedObserver;
import edu.kit.pse17.go_app.ServiceLayer.observer.StatusChangedObserver;
import edu.kit.pse17.go_app.TestData;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.any;

public class GoServiceTest {

    private static final String TEST_ID_USER = "test_id_user";
    private static final long TEST_ID_GO = 1;
    private GoService testService;
    private GoDaoImp mockDao;
    private UserDaoImp mockUserDao;
    private GoEntity testGo;
    private Go testcGo;
    private GroupDaoImp mockGroupDao;
    private GroupService mockGroupService;


    @Before
    public void setUp() throws Exception {
        mockDao = Mockito.mock(GoDaoImp.class);
        mockGroupDao = Mockito.mock(GroupDaoImp.class);
        mockUserDao = Mockito.mock(UserDaoImp.class);
        testService = new GoService(mockDao);
        testGo = TestData.getTestGoLunch();
        testcGo = TestData.getTestcLunch();
        mockGroupService = Mockito.mock(GroupService.class);
        testService.setGroupService(mockGroupService);

    }

    @After
    public void tearDown() throws Exception {
        mockDao = null;
        testService = null;
        testGo = null;
        testcGo = null;
    }

    @Test
    public void createGoTest() throws Exception {
        testService.setUserDao(mockUserDao);
        testService.setGroupDao(mockGroupDao);
        Mockito.when(mockGroupDao.get(Mockito.anyLong())).thenReturn(TestData.getTestGroupFoo());
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(testGo.getID()));
        Mockito.when(mockDao.persist(any(GoEntity.class))).thenReturn(testGo.getID());

        GoAddedObserver mockObserver = Mockito.mock(GoAddedObserver.class);
        Mockito.doNothing().when(mockObserver).update(Mockito.anyList(), Mockito.anyList());

        Mockito.when(mockGroupService.prepareReceiverList(Mockito.anyLong())).thenReturn(entity_ids);

        testService.getObserverMap().remove(EventArg.GO_ADDED_EVENT);
        testService.getObserverMap().put(EventArg.GO_ADDED_EVENT, mockObserver);

        testService.createGo(testcGo);
        Mockito.verify(mockDao, Mockito.times(1)).persist(Mockito.any(GoEntity.class));
        Mockito.verify(mockObserver, Mockito.times(1)).update(Mockito.anyList(), Mockito.anyList());
    }

    @Test
    public void changeStatusTest_Going() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(TEST_ID_USER);
        entity_ids.add(String.valueOf(TEST_ID_GO));
        Mockito.when(testService.getGoById(Mockito.anyLong())).thenReturn(testGo);

        Map<String, String> testStatusChangedContext = new HashMap<>();
        testStatusChangedContext.put("userId", TEST_ID_USER);
        testStatusChangedContext.put("status", "GOING");

        StatusChangedObserver mockObserver = Mockito.mock(StatusChangedObserver.class);

        testService.getObserverMap().remove(EventArg.STATUS_CHANGED_EVENT);
        testService.getObserverMap().put(EventArg.STATUS_CHANGED_EVENT, mockObserver);

        testService.changeStatus(testStatusChangedContext, TEST_ID_GO);
        Mockito.verify(mockDao, Mockito.times(1)).changeStatus(TEST_ID_USER, TEST_ID_GO, Status.GOING);
        Mockito.verify(mockObserver, Mockito.times(1)).update(Mockito.anyList(), Mockito.anyList());

    }

    @Test
    public void changeStatusTest_Gone() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(TEST_ID_USER);
        entity_ids.add(String.valueOf(TEST_ID_GO));
        Mockito.when(testService.getGoById(Mockito.anyLong())).thenReturn(testGo);

        Map<String, String> testStatusChangedContext = new HashMap<>();
        testStatusChangedContext.put("userId", TEST_ID_USER);
        testStatusChangedContext.put("status", "GONE");

        StatusChangedObserver mockObserver = Mockito.mock(StatusChangedObserver.class);

        testService.getObserverMap().remove(EventArg.STATUS_CHANGED_EVENT);
        testService.getObserverMap().put(EventArg.STATUS_CHANGED_EVENT, mockObserver);

        testService.changeStatus(testStatusChangedContext, TEST_ID_GO);
        Mockito.verify(mockDao, Mockito.times(1)).changeStatus(TEST_ID_USER, TEST_ID_GO, Status.GONE);
        Mockito.verify(mockObserver, Mockito.times(1)).update(Mockito.anyList(), Mockito.anyList());

    }

    @Test
    public void changeStatusTest_NotGoing() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(TEST_ID_USER);
        entity_ids.add(String.valueOf(TEST_ID_GO));
        Mockito.when(testService.getGoById(Mockito.anyLong())).thenReturn(testGo);

        Map<String, String> testStatusChangedContext = new HashMap<>();
        testStatusChangedContext.put("userId", TEST_ID_USER);
        testStatusChangedContext.put("status", "NOT_GOING");

        StatusChangedObserver mockObserver = Mockito.mock(StatusChangedObserver.class);

        testService.getObserverMap().remove(EventArg.STATUS_CHANGED_EVENT);
        testService.getObserverMap().put(EventArg.STATUS_CHANGED_EVENT, mockObserver);

        testService.changeStatus(testStatusChangedContext, TEST_ID_GO);
        Mockito.verify(mockDao, Mockito.times(1)).changeStatus(TEST_ID_USER, TEST_ID_GO, Status.NOT_GOING);
        Mockito.verify(mockObserver, Mockito.times(1)).update(Mockito.anyList(), Mockito.anyList());
    }

    @Test
    public void changeStatusTest_Fail() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(TEST_ID_USER);
        entity_ids.add(String.valueOf(TEST_ID_GO));
        Mockito.when(testService.getGoById(Mockito.anyLong())).thenReturn(testGo);

        Map<String, String> testStatusChangedContext = new HashMap<>();
        testStatusChangedContext.put("userId", TEST_ID_USER);
        testStatusChangedContext.put("status", "");

        StatusChangedObserver mockObserver = Mockito.mock(StatusChangedObserver.class);

        testService.getObserverMap().remove(EventArg.STATUS_CHANGED_EVENT);
        testService.getObserverMap().put(EventArg.STATUS_CHANGED_EVENT, mockObserver);

        boolean result = testService.changeStatus(testStatusChangedContext, TEST_ID_GO);
        Assert.assertFalse(result);
        Mockito.verify(mockDao, Mockito.times(0)).changeStatus(TEST_ID_USER, TEST_ID_GO, Status.GONE);
        Mockito.verify(mockObserver, Mockito.times(0)).update(Mockito.anyList(), Mockito.anyList());
    }

    @Test
    public void deleteTest() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(TEST_ID_GO));

        Mockito.when(mockDao.get(Mockito.anyLong())).thenReturn(testGo);
        GoRemovedObserver mockObserver = Mockito.mock(GoRemovedObserver.class);

        testService.getObserverMap().remove(EventArg.GO_REMOVED_EVENT);
        testService.getObserverMap().put(EventArg.GO_REMOVED_EVENT, mockObserver);

        testService.delete(TEST_ID_GO);
        Mockito.verify(mockDao, Mockito.times(1)).delete(TEST_ID_GO);
        Mockito.verify(mockObserver, Mockito.times(1)).update(Mockito.anyList(), Mockito.anyList());
    }

    @Test
    public void updateTest() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(testGo.getID()));
        Mockito.when(mockDao.get(Mockito.anyLong())).thenReturn(testGo);

        GoEditedObserver mockObserver = Mockito.mock(GoEditedObserver.class);
        //Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));

        testService.getObserverMap().remove(EventArg.GO_EDITED_EVENT);
        testService.getObserverMap().put(EventArg.GO_EDITED_EVENT, mockObserver);

        testService.update(testcGo);
        Mockito.verify(mockDao, Mockito.times(1)).update(testGo);
        Mockito.verify(mockObserver, Mockito.times(1)).update(Mockito.anyList(), Mockito.anyList());

    }

    @Test
    public void getGoByIdTest() throws Exception {
        testService.getGoById(TEST_ID_GO);
        Mockito.verify(mockDao, Mockito.times(1)).get(TEST_ID_GO);
    }

    @Test
    public void constructorTest() throws Exception {
        GoService goService = new GoService();
        Assert.assertTrue(goService.isObserverInitialized());
        Assert.assertEquals(4, goService.getObserverMap().size());
    }

    @Test
    public void goEntityToGoTest() throws Exception {
        testGo.getGoneUsers().add(TestData.getTestUserBob());
        testGo.getNotGoingUsers().add(TestData.getTestUserAlice());
        Go result = GoService.goEntityToGo(testGo);
        Assert.assertEquals(testGo.getID(), result.getId());
        int goMembers = testGo.getGoingUsers().size() + testGo.getNotGoingUsers().size() + testGo.getGoneUsers().size();
        Assert.assertEquals(goMembers, result.getParticipantsList().size());

        int goingMembers = 0;
        for (UserGoStatus ugs : result.getParticipantsList()) {
            if (ugs.getStatus() == Status.GOING) {
                goingMembers++;
            }
        }
        Assert.assertEquals(testGo.getGoingUsers().size(), goingMembers);

        int goneMembers = 0;
        for (UserGoStatus ugs : result.getParticipantsList()) {
            if (ugs.getStatus() == Status.GONE) {
                goneMembers++;
            }
        }
        Assert.assertEquals(testGo.getGoneUsers().size(), goneMembers);

        int notGoingMembers = 0;
        for (UserGoStatus ugs : result.getParticipantsList()) {
            if (ugs.getStatus() == Status.NOT_GOING) {
                notGoingMembers++;
            }
        }
        Assert.assertEquals(testGo.getNotGoingUsers().size(), notGoingMembers);
    }

    @Test
    public void makeJsonableUGSTest() throws Exception {
        UserGoStatus testStatus = new UserGoStatus(TestData.getTestcBob(), TestData.getTestcLunch(), Status.GOING);
        GoService.makeJsonable(testStatus);
        Assert.assertNull(testStatus.getGo().getLocations());
        Assert.assertNull(testStatus.getGo().getGroup());
        Assert.assertEquals(0, testStatus.getGo().getParticipantsList().size());
    }

    @Test
    public void makeJsonableGoTest() {
        GoService.makeJsonable(testcGo, true);
        Assert.assertNotNull(testcGo.getGroup());
        for (UserGoStatus ugs : testcGo.getParticipantsList()) {
            Assert.assertNull(ugs.getGo());
        }
    }

    @Test
    public void makeJsonableGoTest_NoParticipants() {
        testcGo.setParticipantsList(null);
        GoService.makeJsonable(testcGo, true);
        Assert.assertNotNull(testcGo.getGroup());
        Assert.assertEquals(new ArrayList<>(), testcGo.getParticipantsList());
    }

    @Test
    public void unregisterTest() throws Exception {
        int originalSize = testService.getObserverMap().size();
        testService.getObserverMap().put(EventArg.USER_DELETED_EVENT, null);
        testService.unregister(EventArg.USER_DELETED_EVENT);

        Assert.assertEquals(originalSize, testService.getObserverMap().size());
        for (EventArg key : testService.getObserverMap().keySet()) {
            Assert.assertNotEquals(EventArg.USER_DELETED_EVENT, key);
        }
    }

}