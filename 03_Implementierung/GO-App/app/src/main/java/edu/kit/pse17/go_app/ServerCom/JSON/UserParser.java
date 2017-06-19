package edu.kit.pse17.go_app.ServerCom.JSON;

import org.json.JSONObject;

import edu.kit.pse17.go_app.User;

/**
 *  * wandelt User-Objekte um in JSON-Objekte der entsprechenden Struktur
 *
 * Created by tina on 19.06.17.
 */

public  class UserParser implements JsonParser<User> {

    @Override
    public JSONObject parseToJson(User user) {
        return null;
    }

    @Override
    public User parseFromJson(JSONObject o) {
        return null;
    }
}
