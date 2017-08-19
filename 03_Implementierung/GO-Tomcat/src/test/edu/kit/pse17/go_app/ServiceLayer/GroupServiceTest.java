package edu.kit.pse17.go_app.ServiceLayer;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Group;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDaoImp;
import edu.kit.pse17.go_app.ServiceLayer.observer.*;
import edu.kit.pse17.go_app.TestData;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class GroupServiceTest {

    private static final String TEST_ID_USER = "test_id_user";
    private static final long TEST_ID_GROUP = 1l;

    private GroupService testService;
    private GroupDaoImp mockGroupDao;
    private GroupEntity testGroup;
    private Group testcGroup;

    @Before
    public void setUp() {
        mockGroupDao = Mockito.mock(GroupDaoImp.class);
        testService = new GroupService(mockGroupDao);
        testGroup = TestData.getTestGroupFoo();
        testcGroup = TestData.getTestcFoo();
    }

    @After
    public void tearDown() {
        mockGroupDao = null;
        testService = null;
        testGroup = null;
        testcGroup = null;
    }

    @Test
    public void editGroupForJsonTest() throws Exception {
        for (UserEntity usr : testGroup.getAdmins()) {
            UserService.editUserForJson(usr);
        }
        for (UserEntity usr : testGroup.getMembers()) {
            UserService.editUserForJson(usr);
        }
        for (GoEntity go : testGroup.getGos()) {
            GoService.editGoForJson(go, false);
        }

        GroupEntity result = TestData.getTestGroupFoo();
        GroupService.editGroupForJson(result);

        Assert.assertEquals(testGroup, result);

    }

    @Test
    public void createGroupTest() throws Exception {
        Mockito.when(mockGroupDao.get(Mockito.anyLong())).thenReturn(testGroup);
        long result = testService.createGroup(testcGroup);
        Mockito.verify(mockGroupDao, Mockito.times(1)).persist(testGroup);
        Assert.assertEquals(testGroup.getID(), result);
    }

    @Test
    public void editGroupTest() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(testGroup.getID()));

        GroupEditedObserver mockObserver = Mockito.mock(GroupEditedObserver.class);
        Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));

        testService.getObserverMap().remove(EventArg.GROUP_EDITED_EVENT);
        testService.getObserverMap().put(EventArg.GROUP_EDITED_EVENT, mockObserver);

        testService.editGroup(testcGroup);
        Mockito.verify(mockGroupDao, Mockito.times(1)).update(testGroup);
        Mockito.verify(mockObserver, Mockito.times(1)).update(entity_ids);
    }

    @Test
    public void deleteGroupTest() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(testGroup.getID()));

        GroupRemovedObserver mockObserver = Mockito.mock(GroupRemovedObserver.class);
        Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));

        testService.getObserverMap().remove(EventArg.GROUP_REMOVED_EVENT);
        testService.getObserverMap().put(EventArg.GROUP_REMOVED_EVENT, mockObserver);

        testService.deleteGroup(testGroup.getID());
        Mockito.verify(mockGroupDao).delete(testGroup.getID());
        Mockito.verify(mockObserver).update(entity_ids);
    }

    @Test
    public void acceptRequestTest() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(TEST_ID_USER);
        entity_ids.add(String.valueOf(TEST_ID_GROUP));

        MemberAddedObserver mockObserver = Mockito.mock(MemberAddedObserver.class);
        Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));

        testService.getObserverMap().remove(EventArg.MEMBER_ADDED_EVENT);
        testService.getObserverMap().put(EventArg.MEMBER_ADDED_EVENT, mockObserver);

        testService.acceptRequest(1l, TEST_ID_USER);
        Mockito.verify(mockGroupDao).addGroupMember(TEST_ID_USER, TEST_ID_GROUP);
        Mockito.verify(mockObserver).update(entity_ids);
    }

    @Test
    public void removeGroupMemberTest() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(TEST_ID_USER);
        entity_ids.add(String.valueOf(TEST_ID_GROUP));

        MemberRemovedObserver mockObserver = Mockito.mock(MemberRemovedObserver.class);
        Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));

        testService.getObserverMap().remove(EventArg.MEMBER_REMOVED_EVENT);
        testService.getObserverMap().put(EventArg.MEMBER_REMOVED_EVENT, mockObserver);

        testService.removeGroupMember(TEST_ID_USER, 1l);
        Mockito.verify(mockGroupDao).removeGroupMember(TEST_ID_USER, TEST_ID_GROUP);
        Mockito.verify(mockObserver).update(entity_ids);
    }

    @Test
    public void addGroupRequestTest() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(TEST_ID_USER);
        entity_ids.add(String.valueOf(TEST_ID_GROUP));

        GroupRequestReceivedObserver mockObserver = Mockito.mock(GroupRequestReceivedObserver.class);
        Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));

        testService.getObserverMap().remove(EventArg.GROUP_REQUEST_RECEIVED_EVENT);
        testService.getObserverMap().put(EventArg.GROUP_REQUEST_RECEIVED_EVENT, mockObserver);

        testService.addGroupRequest(TEST_ID_USER, 1l);
        Mockito.verify(mockGroupDao).addGroupRequest(TEST_ID_USER, TEST_ID_GROUP);
        Mockito.verify(mockObserver).update(entity_ids);
    }

    @Test
    public void removeGroupRequestTest() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(TEST_ID_USER);
        entity_ids.add(String.valueOf(TEST_ID_GROUP));

        RequestDeniedObserver mockObserver = Mockito.mock(RequestDeniedObserver.class);
        Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));

        testService.getObserverMap().remove(EventArg.GROUP_REQUEST_DENIED_EVENT);
        testService.getObserverMap().put(EventArg.GROUP_REQUEST_DENIED_EVENT, mockObserver);

        testService.removeGroupRequest(TEST_ID_USER, 1l);
        Mockito.verify(mockGroupDao).removeGroupRequest(TEST_ID_USER, TEST_ID_GROUP);
        Mockito.verify(mockObserver).update(entity_ids);
    }

    @Test
    public void addAdminTest() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(TEST_ID_USER);
        entity_ids.add(String.valueOf(TEST_ID_GROUP));

        AdminAddedObserver mockObserver = Mockito.mock(AdminAddedObserver.class);
        Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));

        testService.getObserverMap().remove(EventArg.ADMIN_ADDED_EVENT);
        testService.getObserverMap().put(EventArg.ADMIN_ADDED_EVENT, mockObserver);

        testService.addAdmin(TEST_ID_USER, 1l);
        Mockito.verify(mockGroupDao).addAdmin(TEST_ID_USER, TEST_ID_GROUP);
        Mockito.verify(mockObserver).update(entity_ids);
    }

    @Test
    public void getGroupByIdTest() throws Exception {
        Mockito.when(mockGroupDao.get(testGroup.getID())).thenReturn(testGroup);
        GroupEntity result = testService.getGroupById(testGroup.getID());
        Mockito.verify(mockGroupDao, Mockito.times(1)).get(testGroup.getID());
        Assert.assertEquals(testGroup, result);
    }

}