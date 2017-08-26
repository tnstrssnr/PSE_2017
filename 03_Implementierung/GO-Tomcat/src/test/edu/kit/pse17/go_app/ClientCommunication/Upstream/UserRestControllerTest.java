package edu.kit.pse17.go_app.ClientCommunication.Upstream;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Group;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.User;
import edu.kit.pse17.go_app.ServiceLayer.GroupService;
import edu.kit.pse17.go_app.ServiceLayer.UserService;
import edu.kit.pse17.go_app.TestData;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;

@WebMvcTest(value = UserRestController.class, secure = false)
@SpringBootTest
public class UserRestControllerTest {

    private static final String testUserJson = "{\"uid\":\"testid_1\",\"instanceId\":\"testInstance_1\",\"name\":\"Bob\",\"email\":\"bob@testmail.com\",\"groups\":[],\"requests\":[],\"gos\":[]}";
    private static final String testListString = "[{\"groupId\":-1,\"name\":\"dummy\",\"description\":\"dummy\",\"memberCount\":0,\"membershipList\":[],\"currentGos\":[]}]";
    private static final String testcuserString = "{\"userId\":\"testid_2\",\"instanceId\":\"testInstance_2\",\"name\":\"Alice\",\"email\":\"alice@testmail.com\"}";
    private static UserEntity testUser;
    private static User testcUser;
    private static List<Group> testGroupList;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        userService = Mockito.mock(UserService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new UserRestController(userService)).build();
        testUser = TestData.getTestUserBob();
        testcUser = TestData.getTestcAlice();
        testGroupList = new ArrayList<>();
        testGroupList.add(TestData.getTestcBar());
        testGroupList.add(TestData.getTestcFoo());
        GroupService.makeJsonable(testGroupList.get(0));
        GroupService.makeJsonable(testGroupList.get(1));
        System.out.println(new Gson().toJson(testGroupList));

    }

    @After
    public void tearDown() throws Exception {
        userService = null;
        mockMvc = null;
    }

    @Test
    public void getDataTest() throws Exception {
        Mockito.when(userService.getData(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/testid_1/testmail/testname").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(result.getResponse().getContentAsString());
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        Assert.assertEquals(testListString, result.getResponse().getContentAsString());
    }

    @Test
    public void createUserSuccessfulTest() throws Exception {
        Mockito.when(userService.createUser(any(UserEntity.class))).thenReturn(true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/testid_1")
                .content(testUserJson)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }

    @Test
    public void createUserDuplicateTest() throws Exception {
        Mockito.when(userService.createUser(any(UserEntity.class))).thenReturn(false);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/testid_1")
                .content(testUserJson)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(HttpStatus.I_AM_A_TEAPOT.value(), result.getResponse().getStatus());
    }


    @Test
    public void updateUserTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/testid_1")
                .content(testUserJson)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Mockito.verify(userService, times(1)).updateUser(any(UserEntity.class));
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void deleteUser() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/user/testid_1")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Mockito.verify(userService, times(1)).deleteUser("testid_1");
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void registerDevice() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/testuid/device/testinstanceid")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Mockito.verify(userService, times(1)).registerDevice("testuid", "testinstanceid");
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void getUserbyMail_Successful() throws Exception {
        Mockito.when(userService.getUserbyMail(Mockito.anyString())).thenReturn(testcUser);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/search/test@mail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Mockito.verify(userService, times(1)).getUserbyMail("test@mail.com");
        Assert.assertEquals(testcuserString, result.getResponse().getContentAsString());
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void getUserbyMail_NotFound() throws Exception {
        Mockito.when(userService.getUserbyMail(Mockito.anyString())).thenReturn(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/search/test@mail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Mockito.verify(userService, times(1)).getUserbyMail("test@mail.com");
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

}