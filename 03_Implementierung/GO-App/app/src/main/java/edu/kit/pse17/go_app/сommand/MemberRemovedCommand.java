package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

import org.json.JSONException;
import org.json.JSONObject;

import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * This class implements a command for the remove (or delete)
 * of a member from the group when called.
 */
public class MemberRemovedCommand extends ServerCommand {

    /**
     * This method changes the following data in the repositories of the App:
     *
     * -All members of the group will be notified that a member is removed from
     * the group (or deleted);
     * -All the GOs will be deleted, where the user responsible was;
     * -If the group had only a single member, it will be deleted.
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

        GroupRepository.getInstance().onMemberRemoved(userId, Long.parseLong(groupId));
    }
}
