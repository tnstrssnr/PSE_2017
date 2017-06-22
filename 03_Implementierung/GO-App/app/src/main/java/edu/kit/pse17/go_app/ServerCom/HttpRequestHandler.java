package edu.kit.pse17.go_app.ServerCom;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
;

/**
 * Adapter-Klasse, die von anderen Klassen benutzt werden kann, um HTTP-Anfragen an den tomcat-Server zu schicken
 *
 * Created by tina on 19.06.17.
 */

public abstract class HTTPAdapter {

    private static final String url = "https://i43pc164.ipd.kit.edu/PSESoSe17Gruppe3/tomcat";

    HttpURLConnection conn;

    /**
     * sendet POSTRequest an Tomcat
     */
    protected void sendRequest() {

        try {
            URL server_url = new URL(url);
            conn = (HttpURLConnection) server_url.openConnection();
            conn.connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    }

}


