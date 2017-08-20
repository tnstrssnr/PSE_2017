package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

import com.google.gson.Gson;

import org.json.JSONException;

import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * This class implements a command that creates a new
 * GO in the group when called.
 */
public class GoAddedCommand extends ServerCommand {

    /**
     * This method changes the following data in the repositories of the App:
     *
     * -All members of the group will be notified about the new GO.
     */
    @Override
    public void onCommandReceived() {
        Gson gson = new Gson();
        long groupId;
        Go go = null;

        try {
            go = gson.fromJson(getMessage().getString("data"), Go.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        groupId = go.getGroup().getId();
        GroupRepository.getInstance().onGoAdded(go, groupId);
    }
}
