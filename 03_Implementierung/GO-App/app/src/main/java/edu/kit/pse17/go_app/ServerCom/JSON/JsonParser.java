package edu.kit.pse17.go_app.ServerCom.JSON;

import org.json.JSONObject;

/**
 * Created by tina on 19.06.17.
 */

public interface JsonParser<T> {

    JSONObject parseToJson(T t);

    T parseFromJson(JSONObject o);
}
