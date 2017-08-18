package edu.kit.pse17.go_app.ServiceLayer.observer;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.ServiceLayer.GroupService;
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
public class AdminAddedObserverTest {

    private static final String expectedResult = "{\"user_id\":\"testid_2\",\"group_id\":\"0\"}";

    private AdminAddedObserver observer;
    private FcmClient mockMessenger;
    private GroupService mockGroupService;
    private GroupEntity testGroup;
    private UserEntity testUser;

    @Before
    public void setUp() {
        testGroup = TestData.getTestGroupFoo();
        testUser = TestData.getTestUserAlice();
        this.mockGroupService = Mockito.mock(GroupService.class);
        Mockito.when(mockGroupService.getGroupById(anyLong())).thenReturn(testGroup);
        this.mockMessenger = Mockito.mock(FcmClient.class);
        this.observer = new AdminAddedObserver(mockMessenger, mockGroupService);

    }

    @After
    public void tearDown() throws Exception {
        this.observer = null;
        this.mockMessenger = null;
        this.testGroup = null;
        this.mockGroupService = null;
        this.testUser = null;
    }

    @Test
    public void update() throws Exception {
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(testUser.getUid());
        entity_ids.add(String.valueOf(testGroup.getID()));

        observer.update(entity_ids);
        Mockito.verify(mockMessenger, Mockito.times(1)).send(expectedResult, EventArg.ADMIN_ADDED_EVENT, testGroup.getMembers());
        Mockito.verify(mockMessenger, Mockito.times(1)).send(expectedResult, EventArg.ADMIN_ADDED_EVENT, testGroup.getRequests());
    }

}