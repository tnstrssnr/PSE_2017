package edu.kit.pse17.go_app.сommand;

/**
 * Created by Сеня on 09.07.2017.
 */

import org.json.JSONException;
import org.json.JSONObject;

import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * Diese Klasse implementiert einen Befehl, der bei dem Ablehnen einer
 * Gruppenanfrage vom Benutzer ausgeführt wird.
 * D.h. der Benutzer hat die Gruppenanfrage abgelehnt.
 */
public class RequestDeniedCommand extends ServerCommand {

    /**
     * Diese Methode ändert folgende Daten in Repositorien der App:
     *
     * -Alle Administratoren der Gruppe werden benachrichtigt (d.h., diese
     * eröffnete Gruppenanfrage wird gelöscht).
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

        GroupRepository.getInstance().onRequestDenied(userId, Long.parseLong(groupId));
    }
}
