package edu.kit.pse17.go_app.ClientCommunication.Upstream;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Go;
import edu.kit.pse17.go_app.ServiceLayer.GoService;
import edu.kit.pse17.go_app.ServiceLayer.LocationService;
import edu.kit.pse17.go_app.TestData;
import org.junit.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.any;

@ActiveProfiles("controllerBeans")
@WebMvcTest(value = GoRestController.class, secure = false)
@SpringBootTest
public class GoRestControllerTest {

    private static GoEntity testGo;
    private static String testGoString;
    private static Go testcGo;
    private static Map<String, String> mockMap;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GoService goService;

    @MockBean
    private LocationService locationService;

    @Before
    public void setUp() throws Exception {
        goService = Mockito.mock(GoService.class);
        locationService = Mockito.mock(LocationService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new GoRestController(goService)).build();
        testGo = TestData.getTestGoLunch();
        testGo.setGroup(null);
        testGo.setOwner(null);
        testcGo = TestData.getTestcLunch();
        testcGo.setGroup(null);
        testcGo.setParticipantsList(null);
        testGoString = new Gson().toJson(testcGo);
        mockMap = Mockito.mock(HashMap.class);
    }

    @After
    public void tearDown() throws Exception {
        goService = null;
        mockMvc = null;
        testGo = null;
        testGoString = null;
        testcGo = null;
    }

    @Test
    public void createGoTest() throws Exception {
        Mockito.when(goService.createGo(any(Go.class))).thenReturn(Long.valueOf(1));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/gos/1/test")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(testGoString);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(String.valueOf(1), result.getResponse().getContentAsString());
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void changeStatusSuccessfulTest() throws Exception {
        Mockito.when(goService.changeStatus(Mockito.anyMap(), Mockito.anyLong())).thenReturn(true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/gos/1/status")
                .accept(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(mockMap))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

    }

    @Test
    public void changeStatusFailTest() throws Exception {
        Mockito.when(goService.changeStatus(mockMap, 1)).thenReturn(false);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/gos/1/status")
                .accept(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(mockMap))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Ignore
    @Test
    public void getLocationTest() throws Exception {
        Mockito.mock(LocationService.class);
        Mockito.when(LocationService.getGroupLocation(1)).thenReturn(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/gos/location/1");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        //TODO finish
    }

    @Ignore
    @Test
    public void setLocationTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/gos/location/1");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        //check if response body is of type GroupLocation
    }

    @Test
    public void deleteGoTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/gos/1")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Mockito.verify(goService, Mockito.times(1)).delete(1);
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void editGoTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/gos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testGoString)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Mockito.verify(goService, Mockito.times(1)).update(any(Go.class));
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

}