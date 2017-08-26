package edu.kit.pse17.go_app.ServiceLayer.observer;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.ServiceLayer.GoService;
import edu.kit.pse17.go_app.TestData;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class GoEditedObserverTest {

    private static final String TEST_RECEIVER = "receiver_instance_id";
    private static final EventArg EXPECTED_EVENT = EventArg.GO_EDITED_EVENT;

    //might change after testing goEntityToGo method
    private static final String EXPECTED_JSON = "{\"goId\":1,\"name\":\"lunch\",\"description\":\"test description\",\"start\":\"Thu Aug 30 00:00:00 CEST 3917\",\"end\":\"Sat Sep 01 00:00:00 CEST 3917\",\"lat\":0.0,\"lon\":0.0,\"userId\":\"testid_1\",\"ownerName\":\"Bob\",\"participantsList\":[{\"user\":{\"userId\":\"testid_1\",\"instanceId\":\"testInstance_1\",\"name\":\"Bob\",\"email\":\"bob@testmail.com\"},\"status\":\"GOING\"}],\"locations\":[]}";

    private EventArg resultEvent;
    private String resultString;
    private List<String> resultReceiver;

    @Mock
    private FcmClient mockMessenger;

    @Mock
    private GoService mockService;

    private List<String> receiver;
    private List<String> entity_ids;
    private GoEditedObserver observer;

    @Before
    public void setUp() throws Exception {
        receiver = new ArrayList<>();
        receiver.add(TEST_RECEIVER);
        entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(1));
        //Mockito settings
        mockMessenger = Mockito.mock(FcmClient.class);
        mockService = Mockito.mock(GoService.class);
        Mockito.doReturn(TestData.getTestGoLunch()).when(mockService).getGoById(Mockito.anyLong());

        Mockito.doAnswer(invocation -> {
            resultString = (String) invocation.getArguments()[0];
            resultEvent = (EventArg) invocation.getArguments()[1];
            resultReceiver = (List<String>) invocation.getArguments()[2];
            return true;
        }).when(mockMessenger).send(Mockito.anyString(), Mockito.any(EventArg.class), Mockito.anyList());

        observer = new GoEditedObserver(mockMessenger, mockService);
    }


    @After
    public void tearDown() throws Exception {
        receiver = null;
        mockService = null;
        mockMessenger = null;
        resultEvent = null;
        resultReceiver = null;
        resultString = null;
        observer = null;
    }

    @Test
    public void update() throws Exception {
        observer.update(entity_ids, receiver);

        Assert.assertEquals(EXPECTED_EVENT, resultEvent);
        Assert.assertEquals(EXPECTED_JSON, resultString);
        Assert.assertEquals(receiver, resultReceiver);
    }

    @Test
    public void constructorTest1() {
        observer = new GoEditedObserver(mockService);
        Assert.assertNotNull(observer.getMessenger());
        assertThat(observer.getMessenger(), instanceOf(FcmClient.class));
        Assert.assertEquals(mockService, observer.getGoService());
    }

    @Test
    public void constructorTest2() {
        observer = new GoEditedObserver(mockMessenger, mockService);
        Assert.assertEquals(mockService, observer.getGoService());
        Assert.assertEquals(mockMessenger, observer.getMessenger());
    }

}