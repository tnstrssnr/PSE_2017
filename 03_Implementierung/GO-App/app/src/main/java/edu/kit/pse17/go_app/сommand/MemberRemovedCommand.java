package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

import org.json.JSONException;
import org.json.JSONObject;

import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * Diese Klasse implementiert einen Befehl, der bei dem Austreten (oder Löschen)
 * eines Mitglieds aus der Gruppe ausgeführt wird.
 */
public class MemberRemovedCommand extends ServerCommand {

    /**
     * Diese Methode ändert folgende Daten in Repositorien der App:
     *
     * -Alle Mitglieder der Gruppe werden benachrichtigt, dass ein Mitglied aus
     * der Gruppe ausgetreten (oder gelöscht) ist;
     * -Alle GOs, bei denen der Benutzer GO-Verantwortlicher war, werden gelöscht;
     * -Wenn die Gruppe nur einen einzigen Mitglied hatte, wird diese Gruppe gelöscht.
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

        GroupRepository.getInstance().onMemberRemoved(userId, Long.parseLong(groupId));
    }
}
