package edu.kit.pse17.go_app.ServerCom.JSON;

import org.json.JSONObject;

import edu.kit.pse17.go_app.Model.Group;

/**
 *  * wandelt Gruppen-Objekte um in JSON-Objekte der entsprechenden Struktur
 *
 * Created by tina on 19.06.17.
 */

public class GroupParser implements JsonParser<Group> {

    @Override
    public JSONObject parseToJson(Group group) {
        return null;
    }

    @Override
    public Group parseFromJson(JSONObject o) {
        return null;
    }
}
