package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

import com.google.gson.Gson;

import org.json.JSONException;

import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * Diese Klasse implementiert einen Befehl, der bei der Veränderung der Daten
 * einer Gruppe ausgeführt wird.
 */
public class GroupEditedCommand extends ServerCommand {

    /**
     * Diese Methode ändert folgende Daten in Repositorien der App:
     *
     * -Alle Mitglieder der Gruppe werden über die neuen Daten benachrichtigt.
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
