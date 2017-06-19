package edu.kit.pse17.go_app.ServerCom;

import org.json.JSONObject;

import edu.kit.pse17.go_app.ServerCom.JSON.JsonParser;

/**
 * Adapter-Klasse, die von anderen Klassen benutzt werden kann, um HTTP-Anfragen an den tomcat-Server zu schicken
 *
 * Created by tina on 19.06.17.
 */

public abstract class HTTPAdapter {

    JsonParser parser;

    /**
     * sendet POSTRequest an Tomcat
     * @param obj JSON-Objekt, das übermittlet werden soll
     */
    protected void sendPostRequest(JSONObject obj) {

    }

    /**
     * sendet GET-Request an Tomcat
     * @return vom Tomcat erhaltenes JSONObjekt
     */
    protected JSONObject sendGetRequest() {

        return null;
    }
}


