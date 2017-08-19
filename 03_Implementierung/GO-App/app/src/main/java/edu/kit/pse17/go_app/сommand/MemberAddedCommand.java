package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import edu.kit.pse17.go_app.model.entities.User;
import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * Diese Klasse implementiert einen Befehl, der bei dem Beitreten einer Gruppe
 * vom Benutzer ausgeführt wird.
 * D.h. der Benutzer hat die Gruppenanfrage bestätigt.
 */
public class MemberAddedCommand extends ServerCommand {

    /**
     * Diese Methode ändert folgende Daten in Repositorien der App:
     *
     * -Dem ausgewählten Benutzer werden alle Daten der Gruppe gesendet;
     * -Alle Mitglieder der Gruppe werden über den neuen Benutzer benachrichtigt.
     */
    @Override
    public void onCommandReceived() {
        Gson gson = new Gson();
        User user = null;
        String groupId = null;

        try {
            String jsonString = getMessage().getString("data");
            JSONObject data = new JSONObject(jsonString);
            groupId = data.getString("group_id");
            String userString = data.getString("user");
            user = gson.fromJson(userString, User.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GroupRepository.getInstance().onMemberAdded(user, Long.parseLong(groupId));
    }
}
