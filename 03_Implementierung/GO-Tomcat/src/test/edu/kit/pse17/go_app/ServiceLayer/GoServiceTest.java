package edu.kit.pse17.go_app.ServiceLayer;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.Status;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GoDaoImp;
import edu.kit.pse17.go_app.ServiceLayer.observer.GoAddedObserver;
import edu.kit.pse17.go_app.ServiceLayer.observer.GoEditedObserver;
import edu.kit.pse17.go_app.ServiceLayer.observer.GoRemovedObserver;
import edu.kit.pse17.go_app.ServiceLayer.observer.StatusChangedObserver;
import edu.kit.pse17.go_app.TestData;
import org.junit.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

@Ignore
public class GoServiceTest {

    private static final String testStatusChangedContext = "\"test_id_user BESTÄTIGT\"";
    private static final String TEST_ID_USER = "test_id_user";
    private static final long TEST_ID_GO = 1l;
    private GoService testService;
    private GoDaoImp mockDao;
    private GoEntity testGo;

    @Before
    public void setUp() throws Exception {
        mockDao = Mockito.mock(GoDaoImp.class);
        testService = new GoService(mockDao);
        testGo = TestData.getTestGoLunch();
    }

    @After
    public void tearDown() throws Exception {
        mockDao = null;
        testService = null;
        testGo = null;
    }

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

    @Test
    public void createGoTest() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(testGo.getID()));
        Mockito.when(mockDao.persist(Mockito.any(GoEntity.class))).thenReturn(testGo.getID());

        GoAddedObserver mockObserver = Mockito.mock(GoAddedObserver.class);
        Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));

        testService.getObserverMap().remove(EventArg.GO_ADDED_EVENT);
        testService.getObserverMap().put(EventArg.GO_ADDED_EVENT, mockObserver);

        testService.createGo(testGo);
        Mockito.verify(mockDao, Mockito.times(1)).persist(testGo);
        Mockito.verify(mockObserver, Mockito.times(1)).update(entity_ids);
    }

    @Test
    public void changeStatusTest() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(TEST_ID_USER);
        entity_ids.add(String.valueOf(TEST_ID_GO));

        StatusChangedObserver mockObserver = Mockito.mock(StatusChangedObserver.class);
        Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));

        testService.getObserverMap().remove(EventArg.STATUS_CHANGED_COMMAND);
        testService.getObserverMap().put(EventArg.STATUS_CHANGED_COMMAND, mockObserver);

        testService.changeStatus(testStatusChangedContext, TEST_ID_GO);
        Mockito.verify(mockDao, Mockito.times(1)).changeStatus(TEST_ID_USER, TEST_ID_GO, Status.BESTÄTIGT);
        Mockito.verify(mockObserver, Mockito.times(1)).update(entity_ids);

    }

    @Test
    public void deleteTest() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(TEST_ID_GO));

        GoRemovedObserver mockObserver = Mockito.mock(GoRemovedObserver.class);
        Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));

        testService.getObserverMap().remove(EventArg.GO_REMOVED_EVENT);
        testService.getObserverMap().put(EventArg.GO_REMOVED_EVENT, mockObserver);

        testService.delete(TEST_ID_GO);
        Mockito.verify(mockDao, Mockito.times(1)).delete(TEST_ID_GO);
        Mockito.verify(mockObserver, Mockito.times(1)).update(entity_ids);
    }

    @Test
    public void updateTest() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(testGo.getID()));

        GoEditedObserver mockObserver = Mockito.mock(GoEditedObserver.class);
        Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));

        testService.getObserverMap().remove(EventArg.GO_EDITED_COMMAND);
        testService.getObserverMap().put(EventArg.GO_EDITED_COMMAND, mockObserver);

        testService.update(testGo);
        Mockito.verify(mockDao, Mockito.times(1)).update(testGo);
        Mockito.verify(mockObserver, Mockito.times(1)).update(entity_ids);

    }

    @Test
    public void getGoByIdTest() throws Exception {
        testService.getGoById(TEST_ID_GO);
        Mockito.verify(mockDao, Mockito.times(1)).get(TEST_ID_GO);
    }

}