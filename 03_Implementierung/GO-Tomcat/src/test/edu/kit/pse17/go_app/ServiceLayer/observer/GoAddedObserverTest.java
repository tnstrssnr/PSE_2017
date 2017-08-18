package edu.kit.pse17.go_app.ServiceLayer.observer;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.ServiceLayer.GoService;
import edu.kit.pse17.go_app.TestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyLong;

@Ignore
public class GoAddedObserverTest {

    private static final String expectedResult = "{\"ID\":1,\"name\":\"lunch\",\"description\":\"test description\",\"start\":\"Aug 30, 3917 12:00:00 AM\",\"end\":\"Sep 1, 3917 12:00:00 AM\",\"lat\":0.0,\"lon\":0.0}";

    private GoAddedObserver observer;
    private FcmClient mockMessenger;
    private GoService mockGoService;
    private GoEntity testGo;

    @Before
    public void setUp() throws Exception {
        testGo = TestData.getTestGoLunch();
        this.mockGoService = Mockito.mock(GoService.class);
        Mockito.when(mockGoService.getGoById(anyLong())).thenReturn(TestData.getTestGoLunch());
        this.mockMessenger = Mockito.mock(FcmClient.class);
        this.observer = new GoAddedObserver(mockMessenger, mockGoService);
    }

    @After
    public void tearDown() throws Exception {
        this.observer = null;
        this.mockMessenger = null;
        this.mockGoService = null;
        this.testGo = null;
    }

    @Ignore
    @Test
    public void update() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(testGo.getID()));
        observer.update(entity_ids);
        Mockito.verify(mockMessenger, Mockito.times(1)).send(expectedResult, EventArg.GO_ADDED_EVENT, TestData.getTestGoLunch().getGroup().getMembers());
        Mockito.verify(mockMessenger, Mockito.times(1)).send(expectedResult, EventArg.GO_ADDED_EVENT, TestData.getTestGoLunch().getGroup().getRequests());
    }

}