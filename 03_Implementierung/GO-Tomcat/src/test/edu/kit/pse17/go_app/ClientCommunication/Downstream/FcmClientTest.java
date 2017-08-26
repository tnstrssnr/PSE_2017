package edu.kit.pse17.go_app.ClientCommunication.Downstream;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.TestData;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import retrofit2.Call;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Matchers.anyObject;

public class FcmClientTest {

    private static final String testData = "Testdata";
    private static final EventArg testEvent = EventArg.GROUP_REMOVED_EVENT;
    private static RequestBody expectedRequest;
    private static FcmApi mockApi;
    private static UserEntity testUser;
    private static List<String> testReceiver;
    private static okhttp3.RequestBody resultRequest;

    private FcmClient testClient;

    @Before
    public void setUp() throws Exception {
        mockApi = Mockito.mock(FcmApi.class);
        testReceiver = new ArrayList<>();
        testUser = TestData.getTestUserAlice();
        testReceiver.add(testUser.getInstanceId());
        Mockito.doAnswer(invocation -> {
            resultRequest = (okhttp3.RequestBody) invocation.getArguments()[0];
            return Mockito.mock(Call.class);
        }).when(mockApi).send(anyObject());

        testClient = new FcmClient();
        testClient.setFcmApi(mockApi);
    }

    @After
    public void tearDown() throws Exception {
        testClient = null;
        resultRequest = null;
        testReceiver = null;
        testUser = null;
    }

    @Test
    public void sendTest() throws Exception {
        FcmMessage message = new FcmMessage();
        message.setTo(TestData.getTestcAlice().getInstanceId());

        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("tag", testEvent.toString());
        dataMap.put("data", testData);

        FcmMessage expectedMessage = new FcmMessage();
        expectedMessage.setTo(testUser.getInstanceId());
        expectedMessage.setData(dataMap);

        HashMap<String, String> notificationMap = new HashMap<>();
        notificationMap.put("title", "One of your Groups has been deleted!");
        expectedMessage.setNotification(notificationMap);

        expectedRequest = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(expectedMessage));


        message.setData(dataMap);
        testClient.send(testData, testEvent, testReceiver);
        Assert.assertEquals(expectedRequest.contentLength(), resultRequest.contentLength());
        Assert.assertEquals(expectedRequest.contentType(), resultRequest.contentType());
    }

}