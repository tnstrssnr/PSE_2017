package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

import com.google.gson.Gson;

import org.json.JSONException;

import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * This class implements a command that sends a group request
 * from the Admin of the group to the user when called.
 */
public class GroupRequestReceivedCommand extends ServerCommand {

    /**
     * This method changes the following data in the repositories of the App:
     *
     * -To th selected user will be sent a group request, which will be shown
     * in the app of the user.
     */
    @Override
    public void onCommandReceived() {
        Gson gson = new Gson();
        Group group = null;

        try {
            group = gson.fromJson(getMessage().getString("data"), Group.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GroupRepository.getInstance().onGroupRequestReceived(group);
    }
}
