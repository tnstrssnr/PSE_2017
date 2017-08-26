package edu.kit.pse17.go_app.ServiceLayer;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Group;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.GroupMembership;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.User;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDaoImp;
import edu.kit.pse17.go_app.ServiceLayer.observer.UserDeletedObserver;
import edu.kit.pse17.go_app.TestData;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UserServiceTest {

    private UserEntity testUser;
    private UserService testService;
    private UserDaoImp mockUserDao;
    private List<Group> testList;

    @Before
    public void setUp() throws Exception {
        mockUserDao = Mockito.mock(UserDaoImp.class);
        testService = new UserService(mockUserDao);
        testUser = TestData.getTestUserBob();
        testUser.setGos(new HashSet<>());
        testUser.setGroups(new HashSet<>());
        testUser.setRequests(new HashSet<>());
        testList = new ArrayList<>();
        Group listGroup = GroupService.groupEntityToGroup(TestData.getTestGroupFoo());
        GroupService.makeJsonable(listGroup);
        testList.add(listGroup);
    }

    @After
    public void tearDown() throws Exception {
        testService = null;
        testUser = null;
        mockUserDao = null;
        testList = null;
    }

    @Test
    public void getDataTest() throws Exception {
        UserEntity alteredTestUser = addAssociationsToUser(testUser);

        Mockito.when(mockUserDao.get(Mockito.anyString())).thenReturn(alteredTestUser);
        List<Group> result = testService.getData(alteredTestUser.getUid(), alteredTestUser.getEmail(), alteredTestUser.getName());
        Assert.assertEquals(testList.get(0).getId(), result.get(0).getId());

    }

    @Test
    public void getDataTest_RequestsInList() throws Exception {
        UserEntity alteredTestUser = addAssociationsToUser(testUser);
        alteredTestUser.getGroups().clear();
        alteredTestUser.getRequests().add(TestData.getTestGroupFoo());
        Mockito.when(mockUserDao.get(Mockito.anyString())).thenReturn(alteredTestUser);
        List<Group> result = testService.getData(alteredTestUser.getUid(), alteredTestUser.getEmail(), alteredTestUser.getName());
        Assert.assertEquals(testList.get(0).getId(), result.get(0).getId());

    }

    @Test
    public void getDataTest_UserNotInDb() throws Exception {
        UserEntity alteredTestUser = addAssociationsToUser(testUser);
        Mockito.when(mockUserDao.get(Mockito.anyString())).thenReturn(null);

        UserEntity persistedUserExpected = new UserEntity();
        persistedUserExpected.setUid(alteredTestUser.getUid());
        persistedUserExpected.setName(alteredTestUser.getName());
        persistedUserExpected.setEmail(alteredTestUser.getEmail());
        List<Group> result = testService.getData(alteredTestUser.getUid(), alteredTestUser.getEmail(), alteredTestUser.getName());
        Mockito.verify(mockUserDao, Mockito.times(1)).persist(persistedUserExpected);
        Assert.assertEquals(new ArrayList<>(), result);

    }

    @Test
    public void createUserSuccessfulTest() throws Exception {
        boolean result = testService.createUser(testUser);
        Mockito.verify(mockUserDao, Mockito.times(1)).persist(testUser);
        Assert.assertEquals(true, result);
    }

    @Test
    public void createUserFailTest() throws Exception {
        Mockito.when(mockUserDao.persist(Mockito.any(UserEntity.class))).thenThrow(ConstraintViolationException.class);
        boolean result = testService.createUser(testUser);
        Mockito.verify(mockUserDao, Mockito.times(1)).persist(testUser);
        Assert.assertEquals(false, result);
    }

    @Test
    public void updateUserTest() throws Exception {
        testService.updateUser(testUser);
        Mockito.verify(mockUserDao, Mockito.times(1)).update(testUser);
    }

    @Test
    public void deleteUserTest() throws Exception {
        UserEntity alteredUser = addAssociationsToUser(testUser);
        Mockito.when(mockUserDao.get(Mockito.anyString())).thenReturn(alteredUser);
        testService.deleteUser(testUser.getUid());
        Mockito.verify(mockUserDao, Mockito.times(1)).delete(testUser.getUid());
    }

    @Test
    public void registerDeviceTest() throws Exception {
        Mockito.when(mockUserDao.get(Mockito.anyString())).thenReturn(testUser);
        testService.registerDevice(testUser.getUid(), "new_instance_id");
        testUser.setInstanceId("new_instance_id");
        Mockito.verify(mockUserDao, Mockito.times(1)).update(testUser);
    }

    @Test
    public void getUserbyMailTest() throws Exception {
        final String testMail = "test@mail.com";
        Mockito.when(mockUserDao.getUserByEmail(Mockito.anyString())).thenReturn(testUser);
        User result = testService.getUserbyMail(testMail);
        Mockito.verify(mockUserDao, Mockito.times(1)).getUserByEmail(testMail);
        Assert.assertEquals(testUser.getEmail(), result.getEmail());

        Group group = GroupService.groupEntityToGroup(TestData.getTestGroupFoo());
        group.getMembershipList().add(new GroupMembership(TestData.getTestcAlice(), group, true, false));
        GroupService.makeJsonable(group);
    }

    @Test
    public void constructorTest() {
        testService = new UserService();
        Assert.assertEquals(1, testService.getObserverMap().size());
    }

    @Test
    public void unregisterTest() {
        testService.getObserverMap().put(EventArg.USER_DELETED_EVENT, new UserDeletedObserver(testService));
        testService.unregister(EventArg.USER_DELETED_EVENT);
        Assert.assertEquals(null, testService.getObserverMap().get(EventArg.USER_DELETED_EVENT));
    }

    @Test
    public void registerTest() {
        UserDeletedObserver registeredObserver = new UserDeletedObserver(testService);
        testService.setObserverMap(null);
        testService.register(EventArg.USER_DELETED_EVENT, registeredObserver);
        Assert.assertEquals(1, testService.getObserverMap().size());
        Assert.assertEquals(registeredObserver, testService.getObserverMap().get(EventArg.USER_DELETED_EVENT));
    }

    @Test
    public void userEntityToUserTest() {
        User expected = TestData.getTestcBob();
        User result = UserService.userEntityToUser(testUser);

        Assert.assertEquals(expected.getUid(), result.getUid());
        Assert.assertEquals(expected.getName(), result.getName());
        Assert.assertEquals(expected.getEmail(), result.getEmail());
        Assert.assertEquals(expected.getInstanceId(), result.getInstanceId());
    }

    private UserEntity addAssociationsToUser(UserEntity user) {
        user.getGos().add(TestData.getTestGoLunch());
        user.getGroups().add(TestData.getTestGroupFoo());
        return user;
    }

}