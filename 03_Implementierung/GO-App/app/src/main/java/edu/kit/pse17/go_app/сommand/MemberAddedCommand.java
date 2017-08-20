package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import edu.kit.pse17.go_app.model.entities.User;
import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * This class implements a command that adds the user to the group when called.
 * I.e. the user has confirmed the group's request.
 */
public class MemberAddedCommand extends ServerCommand {

    /**
     * This method changes the following data in the repositories of the App:
     *
     * -The user group of the data will be sent;
     * -All members of the group will be be notified about the new user.
     */
    @Override
    public void onCommandReceived() {
        Gson gson = new Gson();
        User user = null;
        String groupId = null;

        try {
            String jsonString = getMessage().getString("data");
            JSONObject data = new JSONObject(jsonString);
            groupId = data.getString("group_id");
            String userString = data.getString("user");
            user = gson.fromJson(userString, User.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GroupRepository.getInstance().onMemberAdded(user, Long.parseLong(groupId));
    }
}
