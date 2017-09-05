package edu.kit.pse17.go_app.сommand;

/**
 * Created by Сеня on 09.07.2017.
 */

import org.json.JSONException;
import org.json.JSONObject;

import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * This class implements a command that deletes the Account of
 * a user when called.
 */
public class UserDeletedCommand extends ServerCommand {

    /**
     * Overrides the default constructor.
     */
    public UserDeletedCommand(){
        this.restartNeeded = true;
    }

    /**
     * This method changes the following data in the repositories of the App:
     *
     * -All the members of the groups, in which the user was a member,
     * will be notified that this member is deleted;
     * -All participants of the GO, where the user was a participant,
     * will be notified that this user is deleted;
     * -All the GOs will be deleted where the user responsible was;
     * -If the group had only a single member, it will be deleted.
     */
    @Override
    public void onCommandReceived() {
        String userId = null;

        try {
            String jsonString = getMessage().getString("data");
            JSONObject data = new JSONObject(jsonString);
            userId = data.getString("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GroupRepository.getInstance().onUserDeleted(userId);
    }
}
