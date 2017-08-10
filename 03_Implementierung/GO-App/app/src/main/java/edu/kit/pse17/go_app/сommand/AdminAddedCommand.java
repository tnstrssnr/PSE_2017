package edu.kit.pse17.go_app.сommand;

/**
 * Created by Сеня on 09.07.2017.
 */

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * Diese Klasse implementiert einen Befehl, der bei der Erteilung von Admin-Rechten
 * zu einem Mitglied der Gruppe vom Admin dieser Gruppe ausgeführt wird.
 */
public class AdminAddedCommand extends ServerCommand {
    public HashMap<String, String> map = new HashMap<>();

    /**
     * Diese Methode ändert folgende Daten in Repositorien der App:
     *
     * -Alle Mitglieder der Gruppe werden über den neuen Admin benachrichtigt.
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

        /*map.put("u", userId);
        map.put("g", groupId);*/
        GroupRepository.getInstance().onAdminAdded(userId, Long.parseLong(groupId));
    }
}
