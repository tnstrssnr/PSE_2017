package edu.kit.pse17.go_app.сommand;

/**
 * Created by Сеня on 09.07.2017.
 */

import org.json.JSONException;
import org.json.JSONObject;

import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * This class implements a command that is used when user denies the request.
 * I.e. the user has rejected the group's request.
 */
public class RequestDeniedCommand extends ServerCommand {

    /**
     * This method changes the following data in the repositories of the App:
     *
     * -All admins of the group will be notified (i.e., this
     * group request will be deleted).
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

        GroupRepository.getInstance().onRequestDenied(userId, Long.parseLong(groupId));
    }
}
