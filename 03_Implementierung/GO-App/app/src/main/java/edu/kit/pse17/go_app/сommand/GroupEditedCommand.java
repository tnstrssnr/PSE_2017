package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

import com.google.gson.Gson;

import org.json.JSONException;

import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * This class implements a command that changes the data
 * of a group when called.
 */
public class GroupEditedCommand extends ServerCommand {

    /**
     * This method changes the following data in the repositories of the App:
     *
     * -All members of the group will be notified about the new data.
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

        GroupRepository.getInstance().onGroupEdited(group);
    }
}
