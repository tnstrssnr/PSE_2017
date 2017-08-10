package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

import com.google.gson.Gson;

import org.json.JSONException;

import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * Diese Klasse implementiert einen Befehl, der bei dem Senden einer Gruppenanfrage
 * vom Admin der Gruppe zu einem Benutzer ausgeführt wird.
 */
public class GroupRequestReceivedCommand extends ServerCommand {

    /**
     * Diese Methode ändert folgende Daten in Repositorien der App:
     *
     * -Dem ausgewählten Benutzer wird eine Gruppenanfrage gesendet, die in der
     * App des Benutzers gezeigt wird.
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

        GroupRepository.getInstance().onGroupRequestReceived(group);
    }
}
