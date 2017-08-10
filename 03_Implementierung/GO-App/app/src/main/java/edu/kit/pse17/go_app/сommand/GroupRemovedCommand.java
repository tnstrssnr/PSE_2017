package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

import org.json.JSONException;
import org.json.JSONObject;

import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * Diese Klasse implementiert einen Befehl, der bei dem Löschen einer Gruppe
 * ausgeführt wird.
 */
public class GroupRemovedCommand extends ServerCommand {

    /**
     * Diese Methode ändert folgende Daten in Repositorien der App:
     *
     * -Alle Mitglieder der Gruppe werden benachrichtigt, dass die Gruppe nicht mehr
     * existiert;
     * -Alle GOs der Gruppe werden gelöscht;
     * -Die Gruppe selbst (und alle Daten) werden gelöscht.
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
