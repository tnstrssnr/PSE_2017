package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

import com.google.gson.Gson;

import org.json.JSONException;

import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * Diese Klasse implementiert einen Befehl, der bei der Veränderung der Daten
 * eines GOs ausgeführt wird.
 */
public class GoEditedCommand extends ServerCommand {

    /**
     * Diese Methode ändert folgende Daten in Repositorien der App:
     *
     * -Alle Mitglieder der Gruppe werden über die neuen Daten des GOs
     * benachrichtigt.
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
