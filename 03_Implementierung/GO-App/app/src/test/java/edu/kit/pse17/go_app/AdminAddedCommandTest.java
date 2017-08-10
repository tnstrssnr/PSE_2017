package edu.kit.pse17.go_app;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.сommand.AdminAddedCommand;

import static org.junit.Assert.assertEquals;

/**
 * Created by Сеня on 10.08.2017.
 */

public class AdminAddedCommandTest {
    AdminAddedCommand cmd;

    @Before
    public void start() throws JSONException {
        cmd = new AdminAddedCommand();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id", "112");
        jsonObject.put("group_id", "99998");

        //String jsonInString = gson.toJson(group);

        JSONObject eventData = new JSONObject();
        eventData.put("tag", "ADMIN_ADDED_EVENT");
        eventData.put("data", jsonObject.toString());

        cmd.setMessage(eventData);
    }


    @After
    public void end() {
        cmd = null;
    }

    @Test
    public void onCommandReceivedTest() {
        cmd.onCommandReceived();
        assertEquals("112", cmd.map.get("u"));
        assertEquals("99998", cmd.map.get("g"));
    }
}
