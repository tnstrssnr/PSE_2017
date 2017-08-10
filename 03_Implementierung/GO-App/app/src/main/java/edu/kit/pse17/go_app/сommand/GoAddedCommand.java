package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

import com.google.gson.Gson;

import org.json.JSONException;

import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * Diese Klasse implementiert einen Befehl, der bei dem Erstellen eines neuen
 * GOs der Gruppe ausgeführt wird.
 */
public class GoAddedCommand extends ServerCommand {

    /**
     * Diese Methode ändert folgende Daten in Repositorien der App:
     *
     * -Alle Mitglieder der Gruppe werden über das neue GO benachrichtigt.
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
