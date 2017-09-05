package edu.kit.pse17.go_app.сommand;

/**
 * Created by Сеня on 09.07.2017.
 */

import org.json.JSONException;
import org.json.JSONObject;

import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * This class implements a command, which is granting the Admin Rights to
 * member of the group from the Admin of this group when called.
 */
public class AdminAddedCommand extends ServerCommand {

    /**
     * This method changes the following data in the repositories of the App:
     *
     * -All members of the group will be notified about the new Admin.
     */
    @Override
    public void onCommandReceived() {
        String userId = null;
        String groupId = null;

        try {
            String jsonString = getMessage().getString("data");
            JSONObject data = new JSONObject(jsonString);
            userId = data.getString("user_id");
            groupId = data.getString("group_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GroupRepository.getInstance().onAdminAdded(userId, Long.parseLong(groupId));
    }
}
