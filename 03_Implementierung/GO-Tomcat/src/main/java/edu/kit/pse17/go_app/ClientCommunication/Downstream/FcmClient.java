package edu.kit.pse17.go_app.ClientCommunication.Downstream;

import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import okhttp3.*;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Set;

/**
 * Client-Klasse, die ein HTTP POST-Request an den FCM-Server schickt, wo die Nachricht wiederum an das User-Endgerät
 * weitergeleitet wird. dadurch kann der Server eine Nachricht an einen Client schicken, ohne dass dieser zuvor den
 * Server angesprochen haben muss.
 * <p>
 * Die Methoden der Klasse werden aufgerufen von den Observer-Klassen der Anwendung. Dabei werden die Nachrichten, die
 * an die Clients gesendet werden müssen sowie die Addressaten bereits in den aufrufenden Methoden bestimmt. Diese
 * klasse muss sich nur um das eingentliche Senden der HTTP-Requests kümmern, der Inhalt der Nachricht spielt dabei
 * keine Rolle.
 */
public class FcmClient {

    /**
     * Base URL des FCM-Servers an den die Requests geschickt werden müssen. Dieser Wert darf sich nicht ändern.
     */
    private static final String BASE_URL = "https://fcm.googleapis.com/send";

    private static final String SERVER_KEY = "key=AAAAjalbaUE:APA91bHDguerPdnJ9fOLxvXynowziqVzgw5HB0ug0ZIEFDaFE0AezmBZoD1gcaRROMfriNi7IRKrC5ROVpdOz2Ohegcoj-X4Wphpii1QSgHvRRm6JTxdLi_QUv8GRcenNrMbGEYDEKWC";

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * Ein Http-Client, der für das Senden der HTTP-Requests zuständig ist. Die Konfiguration des HttpClients wird bei
     * der Erstellung des FcmClient-Objekts vorgenommen. Die Konfiguration wird von dem Firebase Cloud Messaging Service
     * vorgegeben.
     */
    private OkHttpClient httpClient;

    /**
     * Die Klasse bietet einen Konstruktor an, der keine Argumente entgegen nimmt. In dem Konstruktor wird die
     * Konfikuration des HttpClients standardmäßig implementiert, sodass er Anfragen an die von FCM definierte URL
     * schicken kann.
     */
    public FcmClient() {
        this.httpClient = new OkHttpClient();
    }

    public FcmClient(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }


    /**
     * Die Methode einen POST-Request an den FCM-Server, der diese an das User-Endgerät weiterleitet. Dafür wird der
     * HttpClient der FcmClient-Instanz benutzt.
     * <p>
     * Diese Methode wird von den Observer-Klassen aufgerufen, um die Änderungen, die dort behandlet wurden mithilde
     * dieser Klasse an die Clients zu schicken. Es wird vorausgesetzt, dass die Daten und vor allem die InstanceIDs,
     * die dieser Methode übergeben werden, gültig sind.
     *
     * @param data     Dieses Objekt enthält die Daten, die an den Client geschickt werden sollen
     * @param command  Ein String, der anzeigt, um was für eine Nachricht es sich handelt, also zu welchem
     *                 Serverereignis sie gehört. Dieser String bestimmt, an welche Command-Klasse auf dem Client die
     *                 Nachricht weitergeleitet wird.
     * @param receiver Eine Liste mit den InstaceIds der Clients,an die die Nachricht geschickt werden soll. dabei muss
     *                 es sich um gültige InstanceIds handeln, sonst kann die Methode nicht fehlerfrei ausgeführt
     *                 werden.
     */

    public void send(String data, EventArg command, Set<UserEntity> receiver) {

        JSONObject eventData = new JSONObject();
        eventData.put("tag", command.toString());
        eventData.put("data", data);

        for (UserEntity msg : receiver) {

            JSONObject json = new JSONObject();
            json.put("to", msg.getInstanceId());
            json.put("data", eventData);

            String jsonString = json.toString();
            RequestBody body = RequestBody.create(JSON, jsonString);
            Request request = new Request.Builder()
                    .url(BASE_URL)
                    .header("Content-Type", "application/json")
                    .addHeader("Authorization", SERVER_KEY)
                    .post(body)
                    .build();

            try {
                final Call call = httpClient.newCall(request);
                Response response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //TODO: do something w/ response
        }


    }

}
