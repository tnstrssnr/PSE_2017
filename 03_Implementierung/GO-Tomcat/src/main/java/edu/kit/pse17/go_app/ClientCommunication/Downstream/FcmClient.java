package edu.kit.pse17.go_app.ClientCommunication.Downstream;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *Client-Klasse, die ein HTTP POST-Request an den FCM-Server schickt, wo die Nachricht wiederum an das User-Endgerät weitergeleitet wird.
 * dadurch kann der Server eine Nachricht an einen Client schicken, ohne dass dieser zuvor den Server angesprochen haben muss.
 *
 * Die Methoden der Klasse werden aufgerufen von den Observer-Klassen der Anwendung. Dabei werden die Nachrichten, die an die Clients gesendet werden müssen
 * sowie die Addressaten bereits in den aufrufenden Methoden bestimmt. Diese klasse muss sich nur um das eingentliche Senden der HTTP-reuwests kümmern, der
 * Inhalt der Nachricht spielt dabei keine Rolle.
 *
 * Das ansprechen der Fcm-Server-Api wird mithilfe des Frameworks Retrofit realisiert.
 *
 */
public class FcmClient {

    /**
     * Base URL des FCM-Servers an den die Requests geschickt werden müssen. Dieser Wert darf sich nicht ändern.
     */
    private static final String BASE_URL = "https://fcm.googleapis.com/";

    /**
     * Rest-Schnittstelle des FCM Servers, die vom Retrofit Framework angesprochen werden kann
     */
    private final FcmApi fcmApi;

    /**
     * Konstruktor
     */
    public FcmClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fcmApi = retrofit.create(FcmApi.class);

    }

    /**
     * sendet eine Message an den FCM-Server, der diese an das User-Endgerät weiterleitet
     */
    public void send() {
        fcmApi.sendDownStreamMessage();
    }
}
