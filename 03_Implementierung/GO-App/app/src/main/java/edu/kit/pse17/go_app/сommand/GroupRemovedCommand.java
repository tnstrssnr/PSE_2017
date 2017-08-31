package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

import org.json.JSONException;
import org.json.JSONObject;

import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * This class implements a command that deletes a group
 * when called.
 */
public class GroupRemovedCommand extends ServerCommand {

    public GroupRemovedCommand(){
        this.restartNeeded = true;
    }

    /**
     * This method changes the following data in the repositories of the App:
     *
     * -All members of the group will be notified that group no longer
     * exists;
     * -All of the GOs in the group will be deleted;
     * -The group itself (and all data) will be deleted.
     */
    @Override
    public void onCommandReceived() {
        String groupId = null;

        try {
            String jsonString = getMessage().getString("data");
            JSONObject data = new JSONObject(jsonString);
            groupId = data.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GroupRepository.getInstance().onGroupRemoved(Long.parseLong(groupId));
    }
}
