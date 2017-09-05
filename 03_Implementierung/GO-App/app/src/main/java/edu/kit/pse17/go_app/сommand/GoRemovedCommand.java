package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

import org.json.JSONException;
import org.json.JSONObject;

import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * This class implements a command that deletes GO when called.
 */
public class GoRemovedCommand extends ServerCommand {

    /**
     * Overrides the default constructor.
     */
    public GoRemovedCommand(){
        this.restartNeeded = true;
    }

    /**
     * This method changes the following data in the repositories of the App:
     *
     * -All members of the group will be notified that the GO no longer
     * exists
     */
    @Override
    public void onCommandReceived() {
        String goId = null;

        try {
            String jsonString = getMessage().getString("data");
            JSONObject data = new JSONObject(jsonString);
            goId = data.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GroupRepository.getInstance().onGoRemoved(Long.parseLong(goId));
    }
}
