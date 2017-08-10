package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

import org.json.JSONException;
import org.json.JSONObject;

import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * Diese Klasse implementiert einen Befehl, der bei dem Löschen eines GOs
 * der Gruppe ausgeführt wird.
 */
public class GoRemovedCommand extends ServerCommand {

    /**
     * Diese Methode ändert folgende Daten in Repositorien der App:
     *
     * -Alle Mitglieder der Gruppe werden benachrichtigt, dass das GO nicht mehr
     * existiert.
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
