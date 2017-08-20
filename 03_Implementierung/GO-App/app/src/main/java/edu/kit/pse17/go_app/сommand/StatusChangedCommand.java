package edu.kit.pse17.go_app.сommand;

/**
 * Created by Сеня on 09.07.2017.
 */

import org.json.JSONException;
import org.json.JSONObject;

import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * This class implements a command that changes the status of
 * the user within a GO when called.
 */
public class StatusChangedCommand extends ServerCommand {

    /**
     * This method changes the following data in the repositories of the App:
     *
     * -All participants of the GO will be notified about the new Status of the
     * user.
     */
    @Override
    public void onCommandReceived() {
        String userId = null;
        String goId = null;
        String status = null;

        try {
            String jsonString = getMessage().getString("data");
            JSONObject data = new JSONObject(jsonString);
            userId = data.getString("user_id");
            goId = data.getString("go_id");
            status = data.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GroupRepository.getInstance().onStatusChanged(userId, Long.parseLong(goId), Integer.parseInt(status));
    }
}
