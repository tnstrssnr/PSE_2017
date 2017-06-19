package edu.kit.pse17.go_app.ServerCom;

import org.json.JSONObject;

import edu.kit.pse17.go_app.ServerCom.JSON.JsonParser;

/**
 * Created by tina on 19.06.17.
 */

public abstract class HTTPAdapter<T> {

    JsonParser parser;

    protected void sendPostRequest(JSONObject obj) {

    }

    protected JSONObject sendGetRequest() {

        return null;
    }
}


