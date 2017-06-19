package edu.kit.pse17.go_app.ServerCom.JSON;

import org.json.JSONObject;

import edu.kit.pse17.go_app.Model.GO;

/**
 * wandelt Go-Objekte um in JSON-Objekte der entsprechenden Struktur
 *
 * Created by tina on 19.06.17.
 */

public class GoParser implements JsonParser<GO> {

    @Override
    public JSONObject parseToJson(GO go) {
        return null;
    }

    @Override
    public GO parseFromJson(JSONObject o) {
        return null;
    }
}
