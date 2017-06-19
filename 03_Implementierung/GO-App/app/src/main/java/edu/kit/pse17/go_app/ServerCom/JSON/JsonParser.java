package edu.kit.pse17.go_app.ServerCom.JSON;

import org.json.JSONObject;

/**
 * Die implementierenden Klassen kümmern sich um Wnadlung zwischen Datenobjekt vom Typ T und JSON-Objekt, das zur Kommunikation mit dem GO-Server benutzt werden kann
 *
 * Created by tina on 19.06.17.
 */

public interface JsonParser<T> {

    /**
     * wandelt das Objekt T in ein JsonObject um
     * @param t das umzuwandelnde Objekt
     * @return das umgewandelte JsonObject
     */
    JSONObject parseToJson(T t);

    /**
     * wandelt das JsonObject o in ein Datenobjekt T um
     * @param o das umzuwandelnde Objekt
     * @return das umgewandelte Objekt
     */
    T parseFromJson(JSONObject o);
}
