package edu.kit.pse17.go_app.ClientCommunication.Downstream;

import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.TestData;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import retrofit2.Call;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Matchers.any;

public class FcmClientTest {

    private static final String testData = "Testdata";
    private static final EventArg testEvent = EventArg.GROUP_REMOVED_EVENT;
    private static OkHttpClient mockHttpClient;
    private static GroupEntity testGroup;
    private static UserEntity testUser;
    private static Set<UserEntity> testReceiver;
    private static Request resultRequest;

    private FcmClient testClient;


    @BeforeClass
    public static void setUpTestclass() {
        mockHttpClient = Mockito.mock(OkHttpClient.class);
        testGroup = TestData.getTestGroupFoo();
        testReceiver = new HashSet<>();
        testUser = TestData.getTestUserAlice();
        testUser.setInstanceId("ebs2PiSApYQ:APA91bFwmDoHGRceQIhNeOzO55NilpCxnjoRWjz2neRPVyEiEveQ_zswlMUC2Ft9k_nR8uwqrt6AdpwKsYXkuTedHQylgp75Ok7FHrEk4Yi6T2JUX-ez3oNo9pA6fcriBUP-FT6pWoY-");
        testReceiver.add(testUser);

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                resultRequest = (Request) invocation.getArguments()[0];
                return Mockito.mock(Call.class);
            }
        }).when(mockHttpClient).newCall(any(Request.class));

    }

    @Before
    public void setUp() throws Exception {
        testClient = new FcmClient();
    }

    @After
    public void tearDown() throws Exception {
        testClient = null;
    }

    @Test
    public void sendTest() throws Exception {
        FcmMessage message = new FcmMessage();
        message.setTo(TestData.getTestcAlice().getInstanceId());
        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("tag", testEvent.toString());
        dataMap.put("data", testData);
        message.setData(dataMap);
        testClient.send(testData, testEvent, testReceiver);
    }

}