package edu.kit.pse17.go_app.ClientCommunication.Upstream;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Group;
import edu.kit.pse17.go_app.ServiceLayer.GroupService;
import org.junit.*;
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

import static org.mockito.Matchers.any;

@WebMvcTest(value = GroupRestController.class, secure = false)
@SpringBootTest
public class GroupRestControllerTest {

    private static GroupEntity testGroup;
    private static Group testcGroup;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;
    private String testGroupJson = "{\"ID\":0,\"name\":\"Foo\",\"description\":\"Test Description\"}";


    @BeforeClass
    public static void setUpTestclass() {
        testGroup = new GroupEntity("Foo", "Test Description", null, null, null, null);
        testGroup.setID(0);
        testcGroup = new Group(0, "Foo", "Test Description", 0, null, new ArrayList<>(), new ArrayList<>());
    }


    @Before
    public void setUp() throws Exception {
        System.out.println(new Gson().toJson(testcGroup));
        groupService = Mockito.mock(GroupService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new GroupRestController(groupService)).build();
    }

    @After
    public void tearDown() throws Exception {
        groupService = null;
        mockMvc = null;
    }

    @Test
    public void createGroupTest() throws Exception {
        Mockito.when(groupService.createGroup(any(Group.class))).thenReturn(Long.valueOf(1));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/group/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(testGroupJson);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        Mockito.verify(groupService, Mockito.times(1)).createGroup(any(Group.class));

        Assert.assertEquals(Long.valueOf(1), Long.valueOf(result.getResponse().getContentAsString()));
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void editGroupTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/group/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testGroupJson)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Group group = new Gson().fromJson(testGroupJson, Group.class);
        Mockito.verify(groupService, Mockito.times(1)).editGroup(any(Group.class));
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

    }

    @Test
    public void deleteGroup() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/group/1")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Mockito.verify(groupService, Mockito.times(1)).deleteGroup(1);
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void acceptRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/group/members/1/user_id")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Mockito.verify(groupService, Mockito.times(1)).acceptRequest(1, "user_id");
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void removeMember() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/group/members/1/testid_1")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Mockito.verify(groupService, Mockito.times(1)).removeGroupMember("testid_1", 1);
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void inviteMember() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/group/requests/1/user_id")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Mockito.verify(groupService, Mockito.times(1)).addGroupRequest("user_id", (long) 1);
    }

    @Test
    public void denyRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/group/requests/1/user_id")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Mockito.verify(groupService, Mockito.times(1)).removeGroupRequest("user_id", 1);
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void addAdmin() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/group/admins/1/user_id")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Mockito.verify(groupService, Mockito.times(1)).addAdmin("user_id", 1);
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

}