package edu.kit.pse17.go_app.ClientCommunication.Downstream;

import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.TestData;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Matchers.any;

public class FcmClientTest {

    private static final String testData = "Testdata";
    private static final EventArg testEvent = EventArg.GROUP_REMOVED_EVENT;
    private static OkHttpClient mockHttpClient;
    private static GroupEntity testGroup;
    private static Set<UserEntity> testReceiver;
    private static Request resultRequest;

    private FcmClient testClient;


    @BeforeClass
    public static void setUpTestclass() {
        mockHttpClient = Mockito.mock(OkHttpClient.class);
        testGroup = TestData.getTestGroupFoo();
        testReceiver = new HashSet<>();
        testReceiver.add(TestData.getTestUserAlice());

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
        testClient = new FcmClient(mockHttpClient);
    }

    @After
    public void tearDown() throws Exception {
        testClient = null;
    }

    @Test
    public void sendTest() throws Exception {
        testClient.send(testData, testEvent, testReceiver);
        System.out.println();
    }

}