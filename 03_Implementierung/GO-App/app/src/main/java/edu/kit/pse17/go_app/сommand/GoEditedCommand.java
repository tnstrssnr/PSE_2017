package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

import com.google.gson.Gson;

import org.json.JSONException;

import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * This class implements a command that changes the data of the GO
 * when called.
 */
public class GoEditedCommand extends ServerCommand {

    /**
     * This method changes the following data in the repositories of the App:
     *
     * -All participants of the GO will be notified about the new data.
     */
    @Override
    public void onCommandReceived() {
        Gson gson = new Gson();
        Go go = null;

        try {
            go = gson.fromJson(getMessage().getString("data"), Go.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GroupRepository.getInstance().onGoEdited(go);
    }
}
