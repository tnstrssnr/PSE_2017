package edu.kit.pse17.go_app.ServiceLayer;

import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Group;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.User;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDaoImp;
import edu.kit.pse17.go_app.TestData;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UserServiceTest {

    private UserEntity testUser;
    private UserService testService;
    private UserDaoImp mockUserDao;
    private List<Group> testList;
    private User testcUser;

    @Before
    public void setUp() throws Exception {
        mockUserDao = Mockito.mock(UserDaoImp.class);
        testService = new UserService(mockUserDao);
        testUser = TestData.getTestUserBob();
        testUser.setGos(new HashSet<>());
        testUser.setGroups(new HashSet<>());
        testUser.setRequests(new HashSet<>());
        testList = new ArrayList<>();
        testList.add(TestData.getTestcFoo());
        testList.add(TestData.getTestcBar());
        testcUser = UserService.userEntityToUser(testUser);
    }

    @After
    public void tearDown() throws Exception {
        testService = null;
        testUser = null;
        mockUserDao = null;
        testList = null;
        testcUser = null;
    }

    @Test
    public void editUserForJsonTest() throws Exception {
        UserEntity alteredTestUser = addAssociationsToUser(testUser);

        UserService.editUserForJson(alteredTestUser);
        Assert.assertEquals(alteredTestUser, testUser);
    }

    @Ignore
    @Test
    public void getDataTest() throws Exception {
        UserEntity alteredTestUser = addAssociationsToUser(testUser);

        Mockito.when(mockUserDao.get(Mockito.anyString())).thenReturn(alteredTestUser);
        List<Group> result = testService.getData(alteredTestUser.getUid(), alteredTestUser.getEmail());
        Assert.assertEquals(testList, result);

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
        Assert.assertEquals(testcUser, result);
    }

    private UserEntity addAssociationsToUser(UserEntity user) {
        user.getGos().add(TestData.getTestGoLunch());
        user.getRequests().add(TestData.getTestGroupBar());
        user.getGroups().add(TestData.getTestGroupFoo());
        return user;
    }

}