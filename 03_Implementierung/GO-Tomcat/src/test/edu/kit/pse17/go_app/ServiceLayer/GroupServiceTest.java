package edu.kit.pse17.go_app.ServiceLayer;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Group;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.GroupMembership;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDaoImp;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDaoImp;
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

    private static final String TEST_ID_USER = "testid_1";
    private static final long TEST_ID_GROUP = 1l;

    private GroupService testService;
    private GroupDaoImp mockGroupDao;
    private UserDaoImp mockUserDao;
    private GroupEntity testGroup;
    private Group testcGroup;

    @Before
    public void setUp() {
        mockGroupDao = Mockito.mock(GroupDaoImp.class);
        mockUserDao = Mockito.mock(UserDaoImp.class);
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
    public void createGroupTest() throws Exception {
        UserDaoImp mockUserDao = Mockito.mock(UserDaoImp.class);
        testService.setUserDao(mockUserDao);
        Mockito.when(mockUserDao.get(Mockito.anyString())).thenReturn(TestData.getTestUserBob());
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
        Mockito.doReturn(testGroup).when(mockGroupDao).get(Mockito.anyLong());

        testService.getObserverMap().remove(EventArg.GROUP_EDITED_EVENT);
        testService.getObserverMap().put(EventArg.GROUP_EDITED_EVENT, mockObserver);

        testService.editGroup(testcGroup);
        Mockito.verify(mockGroupDao, Mockito.times(1)).update(testGroup);
    }

    @Test
    public void deleteGroupTest() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(testGroup.getID()));

        GroupRemovedObserver mockObserver = Mockito.mock(GroupRemovedObserver.class);
        //Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));
        Mockito.doReturn(testGroup).when(mockGroupDao).get(Mockito.anyLong());

        testService.getObserverMap().remove(EventArg.GROUP_REMOVED_EVENT);
        testService.getObserverMap().put(EventArg.GROUP_REMOVED_EVENT, mockObserver);

        testService.deleteGroup(testGroup.getID());
        Mockito.verify(mockGroupDao).delete(testGroup.getID());
        //Mockito.verify(mockObserver).update(entity_ids);
    }

    @Test
    public void acceptRequestTest() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(TEST_ID_USER);
        entity_ids.add(String.valueOf(TEST_ID_GROUP));

        MemberAddedObserver mockObserver = Mockito.mock(MemberAddedObserver.class);
        //Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));
        Mockito.doReturn(testGroup).when(mockGroupDao).get(Mockito.anyLong());

        testService.getObserverMap().remove(EventArg.MEMBER_ADDED_EVENT);
        testService.getObserverMap().put(EventArg.MEMBER_ADDED_EVENT, mockObserver);

        testService.acceptRequest(1l, TEST_ID_USER);
        Mockito.verify(mockGroupDao).addGroupMember(TEST_ID_USER, TEST_ID_GROUP);
        //Mockito.verify(mockObserver).update(entity_ids);
    }

    @Test
    public void removeGroupMemberTest() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(TEST_ID_USER);
        entity_ids.add(String.valueOf(TEST_ID_GROUP));

        MemberRemovedObserver mockObserver = Mockito.mock(MemberRemovedObserver.class);
        //Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));
        Mockito.doReturn(testGroup).when(mockGroupDao).get(Mockito.anyLong());
        Mockito.doReturn(TestData.getTestUserBob()).when(mockUserDao).getUserByEmail(Mockito.anyString());
        testService.setUserDao(mockUserDao);

        testService.getObserverMap().remove(EventArg.MEMBER_REMOVED_EVENT);
        testService.getObserverMap().put(EventArg.MEMBER_REMOVED_EVENT, mockObserver);

        testService.removeGroupMember(TEST_ID_USER, 1l);
        Mockito.verify(mockGroupDao).removeGroupMember(TEST_ID_USER, TEST_ID_GROUP);
        //Mockito.verify(mockObserver).update(entity_ids);
    }

    @Test
    public void addGroupRequestTest() throws Exception {
        UserDaoImp mockUserDao = Mockito.mock(UserDaoImp.class);
        testService.setUserDao(mockUserDao);
        Mockito.when(mockUserDao.getUserByEmail(Mockito.anyString())).thenReturn(TestData.getTestUserBob());
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(TestData.getTestUserBob().getUid());
        entity_ids.add(String.valueOf(TEST_ID_GROUP));

        GroupRequestReceivedObserver mockObserver = Mockito.mock(GroupRequestReceivedObserver.class);
        //Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));

        testService.getObserverMap().remove(EventArg.GROUP_REQUEST_RECEIVED_EVENT);
        testService.getObserverMap().put(EventArg.GROUP_REQUEST_RECEIVED_EVENT, mockObserver);

        testService.addGroupRequest(TEST_ID_USER, 1l);
        Mockito.verify(mockGroupDao).addGroupRequest(TestData.getTestUserBob().getUid(), TEST_ID_GROUP);
        //Mockito.verify(mockObserver).update(entity_ids);
    }

    @Test
    public void removeGroupRequestTest() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(TEST_ID_USER);
        entity_ids.add(String.valueOf(TEST_ID_GROUP));

        RequestDeniedObserver mockObserver = Mockito.mock(RequestDeniedObserver.class);
        //Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));
        Mockito.doReturn(testGroup).when(mockGroupDao).get(Mockito.anyLong());

        testService.getObserverMap().remove(EventArg.GROUP_REQUEST_DENIED_EVENT);
        testService.getObserverMap().put(EventArg.GROUP_REQUEST_DENIED_EVENT, mockObserver);

        testService.removeGroupRequest(TEST_ID_USER, 1l);
        Mockito.verify(mockGroupDao).removeGroupRequest(TEST_ID_USER, TEST_ID_GROUP);
        //Mockito.verify(mockObserver).update(entity_ids);
    }

    @Test
    public void addAdminTest() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(TEST_ID_USER);
        entity_ids.add(String.valueOf(TEST_ID_GROUP));

        AdminAddedObserver mockObserver = Mockito.mock(AdminAddedObserver.class);
        //Mockito.doNothing().when(mockObserver).update(Mockito.anyListOf(String.class));
        Mockito.doReturn(testGroup).when(mockGroupDao).get(Mockito.anyLong());

        testService.getObserverMap().remove(EventArg.ADMIN_ADDED_EVENT);
        testService.getObserverMap().put(EventArg.ADMIN_ADDED_EVENT, mockObserver);

        testService.addAdmin(TEST_ID_USER, 1l);
        Mockito.verify(mockGroupDao).addAdmin(TEST_ID_USER, TEST_ID_GROUP);
        //Mockito.verify(mockObserver).update(entity_ids);
    }

    @Test
    public void getGroupByIdTest() throws Exception {
        Mockito.when(mockGroupDao.get(testGroup.getID())).thenReturn(testGroup);
        GroupEntity result = testService.getGroupById(testGroup.getID());
        Mockito.verify(mockGroupDao, Mockito.times(1)).get(testGroup.getID());
        Assert.assertEquals(testGroup, result);
    }

    @Test
    public void makeJsonableTest_Group() {
        testcGroup.setMembershipList(null);
        testcGroup.setCurrentGos(null);
        Group expected = new Group(testcGroup.getId(), testcGroup.getName(), testcGroup.getDescription(), testcGroup.getMemberCount(), null, null, null);
        expected.setMembershipList(new ArrayList<>());
        expected.setCurrentGos(new ArrayList<>());

        GroupService.makeJsonable(testcGroup);

        Assert.assertEquals(expected.getId(), testcGroup.getId());
        Assert.assertEquals(expected.getName(), testcGroup.getName());
        Assert.assertEquals(expected.getDescription(), testcGroup.getDescription());
        Assert.assertEquals(expected.getCurrentGos(), testcGroup.getCurrentGos());
        Assert.assertEquals(expected.getIcon(), testcGroup.getIcon());
        Assert.assertEquals(expected.getMemberCount(), testcGroup.getMemberCount());
        Assert.assertEquals(expected.getMembershipList(), testcGroup.getMembershipList());
    }

    @Test
    public void makeJsonableTest_Group_withMembership() {
        testcGroup.setCurrentGos(null);
        Group expected = new Group(testcGroup.getId(), testcGroup.getName(), testcGroup.getDescription(), testcGroup.getMemberCount(), null, null, null);
        expected.setMembershipList(new ArrayList<>());
        expected.getMembershipList().add(new GroupMembership(TestData.getTestcAlice(), testcGroup, false, true));
        expected.getMembershipList().add(new GroupMembership(TestData.getTestcBob(), testcGroup, true, false));
        expected.setCurrentGos(new ArrayList<>());

        GroupService.makeJsonable(testcGroup);

        Assert.assertEquals(expected.getMemberCount(), testcGroup.getMemberCount());
        Assert.assertEquals(expected.getMembershipList().size(), testcGroup.getMembershipList().size());
        Assert.assertEquals(expected.getMembershipList().get(0).getUser().getUid(), testcGroup.getMembershipList().get(0).getUser().getUid());
        Assert.assertEquals(expected.getMembershipList().get(0).getGroup().getId(), testcGroup.getMembershipList().get(0).getGroup().getId());
        Assert.assertEquals(expected.getMembershipList().get(0).isAdmin(), testcGroup.getMembershipList().get(0).isAdmin());
        Assert.assertEquals(expected.getMembershipList().get(1).isRequest(), testcGroup.getMembershipList().get(1).isRequest());
    }

    @Test
    public void makeJsonableTest_Group_withCurrentGo() {
        testcGroup.setMembershipList(null);
        Group expected = new Group(testcGroup.getId(), testcGroup.getName(), testcGroup.getDescription(), testcGroup.getMemberCount(), null, null, null);
        expected.setCurrentGos(new ArrayList<>());
        expected.getCurrentGos().add(TestData.getTestcLunch());
        expected.getCurrentGos().add(TestData.getTestcDinner());
        expected.setMembershipList(new ArrayList<>());

        GroupService.makeJsonable(testcGroup);

        Assert.assertEquals(expected.getCurrentGos().size(), testcGroup.getCurrentGos().size());
        Assert.assertEquals(expected.getCurrentGos().get(0).getId(), testcGroup.getCurrentGos().get(0).getId());
        Assert.assertEquals(expected.getCurrentGos().get(1).getId(), testcGroup.getCurrentGos().get(1).getId());
    }

    @Test
    public void makeJsonableTest_GroupMemberShip() {
        GroupMembership result = new GroupMembership(TestData.getTestcBob(), testcGroup, true, false);
        GroupService.makeJsonable(result);
        GroupService.makeJsonable(testcGroup);
        GroupMembership expected = new GroupMembership(TestData.getTestcBob(), TestData.getTestcFoo(), true, false);
        Assert.assertEquals(expected.getUser().getUid(), result.getUser().getUid());
        Assert.assertEquals(expected.getGroup().getId(), result.getGroup().getId());
        Assert.assertEquals(new ArrayList<>(), result.getGroup().getMembershipList());
        Assert.assertEquals(new ArrayList<>(), result.getGroup().getCurrentGos());
    }

    @Test
    public void groupEntityToGroupTest() {
        GroupEntity start = TestData.getTestGroupFoo();
        Group expected = TestData.getTestcFoo();
        Group result = GroupService.groupEntityToGroup(start);

        Assert.assertEquals(expected.getId(), result.getId());
        Assert.assertEquals(expected.getCurrentGos().size(), result.getCurrentGos().size());
        Assert.assertEquals(expected.getMembershipList().size(), result.getMembershipList().size());
        for (int i = 0; i < expected.getMembershipList().size(); i++) {
            Assert.assertEquals(expected.getMembershipList().get(i).getUser().getUid(), result.getMembershipList().get(i).getUser().getUid());
        }
    }

    @Test
    public void unregisterTest() {
        testService.getObserverMap().put(EventArg.USER_DELETED_EVENT, new GroupRemovedObserver(testService));
        testService.unregister(EventArg.USER_DELETED_EVENT);
        Assert.assertNull(testService.getObserverMap().get(EventArg.USER_DELETED_EVENT));
    }

}