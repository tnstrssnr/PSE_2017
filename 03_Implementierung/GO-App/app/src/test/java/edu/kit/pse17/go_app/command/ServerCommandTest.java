package edu.kit.pse17.go_app.command;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import edu.kit.pse17.go_app.model.entities.Cluster;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.model.entities.GroupMembership;
import edu.kit.pse17.go_app.model.entities.User;
import edu.kit.pse17.go_app.model.entities.UserGoStatus;
import edu.kit.pse17.go_app.repositories.GroupRepository;
import edu.kit.pse17.go_app.view.GroupListActivity;
import edu.kit.pse17.go_app.viewModel.livedata.GroupListLiveData;
import edu.kit.pse17.go_app.сommand.AdminAddedCommand;
import edu.kit.pse17.go_app.сommand.GoAddedCommand;
import edu.kit.pse17.go_app.сommand.GoEditedCommand;
import edu.kit.pse17.go_app.сommand.GoRemovedCommand;
import edu.kit.pse17.go_app.сommand.GroupEditedCommand;
import edu.kit.pse17.go_app.сommand.GroupRemovedCommand;
import edu.kit.pse17.go_app.сommand.GroupRequestReceivedCommand;
import edu.kit.pse17.go_app.сommand.MemberAddedCommand;
import edu.kit.pse17.go_app.сommand.MemberRemovedCommand;
import edu.kit.pse17.go_app.сommand.RequestDeniedCommand;
import edu.kit.pse17.go_app.сommand.ServerCommand;
import edu.kit.pse17.go_app.сommand.StatusChangedCommand;
import edu.kit.pse17.go_app.сommand.UserDeletedCommand;

import static org.junit.Assert.assertTrue;

/**
 * Created by Сеня on 31.08.2017.
 */

public class ServerCommandTest {
    private GroupRepository groupRepo;
    private GroupListLiveData data;
    private ServerCommand cmd;

    @Before
    public void setUp() {
        groupRepo = GroupRepository.getInstance();
        groupRepo.setMessageFlag(false);

        ArrayList<Group> list = new ArrayList<>();
        groupRepo.setList(list);

        data = Mockito.spy(new GroupListLiveData());
        Mockito.when(data.getValue()).thenReturn(groupRepo.getList());
        groupRepo.setData(data);

        GroupListActivity.setUserId("testID");
    }

    @After
    public void tearDown() {
        groupRepo.setMessageFlag(false);
        groupRepo = null;
        data = null;
        cmd = null;
        GroupListActivity.setUserId(null);
    }

    @Test
    public void adminAddedCommandTest() throws JSONException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("user_id", "1");
        jsonData.put("group_id", "1");

        JSONObject obj = new JSONObject();
        obj.put("data", jsonData.toString());

        cmd = new AdminAddedCommand();
        cmd.setMessage(obj);

        cmd.onCommandReceived();

        assertTrue(groupRepo.isMessageFlag());
    }

    @Test
    public void goAddedCommandTest() throws JSONException {
        Gson gson = new Gson();
        Go go = getMockedGo();
        String jsonString = gson.toJson(go);

        JSONObject obj = new JSONObject();
        obj.put("data", jsonString);

        cmd = new GoAddedCommand();
        cmd.setMessage(obj);

        cmd.onCommandReceived();

        assertTrue(groupRepo.isMessageFlag());
    }

    @Test
    public void goEditedCommandTest() throws JSONException {
        Gson gson = new Gson();
        Go go = getMockedGo();
        String jsonString = gson.toJson(go);

        JSONObject obj = new JSONObject();
        obj.put("data", jsonString);

        cmd = new GoEditedCommand();
        cmd.setMessage(obj);

        cmd.onCommandReceived();

        assertTrue(groupRepo.isMessageFlag());
    }

    @Test
    public void goRemovedCommandTest() throws JSONException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("id", "1");

        JSONObject obj = new JSONObject();
        obj.put("data", jsonData.toString());

        cmd = new GoRemovedCommand();
        cmd.setMessage(obj);

        cmd.onCommandReceived();

        assertTrue(groupRepo.isMessageFlag());
    }

    @Test
    public void groupEditedCommandTest() throws JSONException {
        Gson gson = new Gson();
        Group group = getMockedGroup();
        String jsonString = gson.toJson(group);

        JSONObject obj = new JSONObject();
        obj.put("data", jsonString);

        cmd = new GroupEditedCommand();
        cmd.setMessage(obj);

        cmd.onCommandReceived();

        assertTrue(groupRepo.isMessageFlag());
    }

    @Test
    public void groupRemovedCommandTest() throws JSONException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("id", "1");

        JSONObject obj = new JSONObject();
        obj.put("data", jsonData.toString());

        cmd = new GroupRemovedCommand();
        cmd.setMessage(obj);

        cmd.onCommandReceived();

        assertTrue(groupRepo.isMessageFlag());
    }

    @Test
    public void groupRequestReceivedCommandTest() throws JSONException {
        Gson gson = new Gson();
        Group group = getMockedGroup();
        String jsonString = gson.toJson(group);

        JSONObject obj = new JSONObject();
        obj.put("data", jsonString);

        cmd = new GroupRequestReceivedCommand();
        cmd.setMessage(obj);

        cmd.onCommandReceived();

        assertTrue(groupRepo.isMessageFlag());
    }

    @Test
    public void memberAddedCommandTest() throws JSONException {
        Gson gson = new Gson();
        User user = getMockedUser();
        String jsonString = gson.toJson(user);

        JSONObject jsonData = new JSONObject();
        jsonData.put("group_id", "1");
        jsonData.put("user", jsonString);

        JSONObject obj = new JSONObject();
        obj.put("data", jsonData.toString());

        cmd = new MemberAddedCommand();
        cmd.setMessage(obj);

        cmd.onCommandReceived();

        assertTrue(groupRepo.isMessageFlag());
    }

    @Test
    public void memberRemovedCommandTest() throws JSONException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("user_id", "1");
        jsonData.put("group_id", "1");

        JSONObject obj = new JSONObject();
        obj.put("data", jsonData.toString());

        cmd = new MemberRemovedCommand();
        cmd.setMessage(obj);

        cmd.onCommandReceived();

        assertTrue(groupRepo.isMessageFlag());
    }

    @Test
    public void requestDeniedCommandTest() throws JSONException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("user_id", "1");
        jsonData.put("group_id", "1");

        JSONObject obj = new JSONObject();
        obj.put("data", jsonData.toString());

        cmd = new RequestDeniedCommand();
        cmd.setMessage(obj);

        cmd.onCommandReceived();

        assertTrue(groupRepo.isMessageFlag());
    }

    @Test
    public void statusChangedCommandTest() throws JSONException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("user_id", "1");
        jsonData.put("go_id", "1");
        jsonData.put("status", "0");

        JSONObject obj = new JSONObject();
        obj.put("data", jsonData.toString());

        cmd = new StatusChangedCommand();
        cmd.setMessage(obj);

        cmd.onCommandReceived();

        assertTrue(groupRepo.isMessageFlag());
    }

    @Test
    public void userDeletedCommandTest() throws JSONException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("user_id", "1");

        JSONObject obj = new JSONObject();
        obj.put("data", jsonData.toString());

        cmd = new UserDeletedCommand();
        cmd.setMessage(obj);

        cmd.onCommandReceived();

        assertTrue(groupRepo.isMessageFlag());
    }

    private User getMockedUser() {
        String userId = "c1S8oa3nARUDexOfL1fTdhgeB5V2";
        String email = "psepse2017@gmail.com";
        String userName = "Gruppe3 PSE";
        User user = new User(userId, email, userName);
        return user;
    }

    private Group getMockedGroup() {
        String groupName = "TestGroup";
        String description = "TestDescription";
        Group group = new Group(-123, groupName, description, 1, null, new ArrayList<GroupMembership>(), new ArrayList<Go>());
        return group;
    }

    private Go getMockedGo() {
        String goName = "TestGo";
        String description = "TestDescriptionGo";
        String start = "25.12.2017, 12:00";
        String end = "25.12.2017, 13:00";
        Group group = getMockedGroup();
        double lat = 0.00000;
        double lon = 14.00000;
        String userId = "c1S8oa3nARUDexOfL1fTdhgeB5V2";
        String userName = "Gruppe3 PSE";
        Go go = new Go(-321, goName, description, start, end, group, lat, lon, userId, userName, new ArrayList<UserGoStatus>(), new ArrayList<Cluster>());
        return go;
    }
}
