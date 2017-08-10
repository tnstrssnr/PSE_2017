package edu.kit.pse17.go_app.сommand;

/**
 * Created by Сеня on 09.07.2017.
 */

import org.json.JSONException;
import org.json.JSONObject;

import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * Diese Klasse implementiert einen Befehl, der bei der Veränderung eines Status
 * des Benutzers innerhalb eines GOs ausgeführt wird.
 */
public class StatusChangedCommand extends ServerCommand {

    /**
     * Diese Methode ändert folgende Daten in Repositorien der App:
     *
     * -Alle Teilnehmer des GOs werden über dan neuen Status des Benutzers
     * benachrichtigt.
     */
    @Override
    public void onCommandReceived() {
        String userId = null;
        String goId = null;
        String status = null;

        try {
            String jsonString = getMessage().getString("data");
            JSONObject data = new JSONObject(jsonString);
            userId = data.getString("user_id");
            goId = data.getString("go_id");
            status = data.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GroupRepository.getInstance().onStatusChanged(userId, Long.parseLong(goId), Integer.parseInt(status));
    }
}
