package edu.kit.pse17.go_app.ServiceLayer;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.Status;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Go;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GoDaoImp;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDaoImp;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDaoImp;
import edu.kit.pse17.go_app.ServiceLayer.observer.GoAddedObserver;
import edu.kit.pse17.go_app.ServiceLayer.observer.GoEditedObserver;
import edu.kit.pse17.go_app.ServiceLayer.observer.GoRemovedObserver;
import edu.kit.pse17.go_app.ServiceLayer.observer.StatusChangedObserver;
import edu.kit.pse17.go_app.TestData;
import org.junit.After;
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

    @Before
    public void setUp() throws Exception {
        mockDao = Mockito.mock(GoDaoImp.class);
        mockGroupDao = Mockito.mock(GroupDaoImp.class);
        mockUserDao = Mockito.mock(UserDaoImp.class);
        testService = new GoService(mockDao);
        testGo = TestData.getTestGoLunch();
        testcGo = TestData.getTestcLunch();

    }

    @After
    public void tearDown() throws Exception {
        mockDao = null;
        testService = null;
        testGo = null;
        testcGo = null;
    }

    /*
    @Test
    public void editGoForJsonTest() throws Exception {
        testGo.setOwner(null);
        for (UserEntity usr : testGo.getGoneUsers()) {
            usr.setRequests(null);
            usr.setGroups(null);
            usr.setGos(null);
        }
        for (UserEntity usr : testGo.getGoingUsers()) {
            usr.setRequests(null);
            usr.setGroups(null);
            usr.setGos(null);
        }
        for (UserEntity usr : testGo.getNotGoingUsers()) {
            usr.setRequests(null);
            usr.setGroups(null);
            usr.setGos(null);
        }
        GoEntity result = TestData.getTestGoLunch();
        GoService.editGoForJson(result, false);
        Assert.assertEquals(testGo, result);
    }
    */

    @Test
    public void createGoTest() throws Exception {
        testService.setUserDao(mockUserDao);
        testService.setGroupDao(mockGroupDao);
        Mockito.when(mockGroupDao.get(Mockito.anyLong())).thenReturn(TestData.getTestGroupFoo());
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(testGo.getID()));
        Mockito.when(mockDao.persist(any(GoEntity.class))).thenReturn(testGo.getID());

        GoAddedObserver mockObserver = Mockito.mock(GoAddedObserver.class);
        //Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));

        testService.getObserverMap().remove(EventArg.GO_ADDED_EVENT);
        testService.getObserverMap().put(EventArg.GO_ADDED_EVENT, mockObserver);

        testService.createGo(testcGo);
        //Mockito.verify(mockDao, Mockito.times(1)).persist(Mockito.any(GoEntity.class));
        //Mockito.verify(mockObserver, Mockito.times(1)).update(entity_ids);
    }

    @Test
    public void changeStatusTest() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(TEST_ID_USER);
        entity_ids.add(String.valueOf(TEST_ID_GO));

        Map<String, String> testStatusChangedContext = new HashMap<>();
        testStatusChangedContext.put("userId", TEST_ID_USER);
        testStatusChangedContext.put("status", "GOING");

        StatusChangedObserver mockObserver = Mockito.mock(StatusChangedObserver.class);
        //Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));

        testService.getObserverMap().remove(EventArg.STATUS_CHANGED_EVENT);
        testService.getObserverMap().put(EventArg.STATUS_CHANGED_EVENT, mockObserver);

        testService.changeStatus(testStatusChangedContext, TEST_ID_GO);
        Mockito.verify(mockDao, Mockito.times(1)).changeStatus(TEST_ID_USER, TEST_ID_GO, Status.GOING);
        //Mockito.verify(mockObserver, Mockito.times(1)).update(entity_ids);

    }

    @Test
    public void deleteTest() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(TEST_ID_GO));

        GoRemovedObserver mockObserver = Mockito.mock(GoRemovedObserver.class);
        //Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));

        testService.getObserverMap().remove(EventArg.GO_REMOVED_EVENT);
        testService.getObserverMap().put(EventArg.GO_REMOVED_EVENT, mockObserver);

        testService.delete(TEST_ID_GO);
        Mockito.verify(mockDao, Mockito.times(1)).delete(TEST_ID_GO);
        //Mockito.verify(mockObserver, Mockito.times(1)).update(entity_ids);
    }

    @Test
    public void updateTest() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(testGo.getID()));

        GoEditedObserver mockObserver = Mockito.mock(GoEditedObserver.class);
        //Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));

        testService.getObserverMap().remove(EventArg.GO_EDITED_EVENT);
        testService.getObserverMap().put(EventArg.GO_EDITED_EVENT, mockObserver);

        testService.update(testcGo);
        Mockito.verify(mockDao, Mockito.times(1)).update(testGo);
        //Mockito.verify(mockObserver, Mockito.times(1)).update(entity_ids);

    }

    @Test
    public void getGoByIdTest() throws Exception {
        testService.getGoById(TEST_ID_GO);
        Mockito.verify(mockDao, Mockito.times(1)).get(TEST_ID_GO);
    }

}