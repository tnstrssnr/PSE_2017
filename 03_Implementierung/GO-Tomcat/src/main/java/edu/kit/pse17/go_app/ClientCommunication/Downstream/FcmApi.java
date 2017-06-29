package edu.kit.pse17.go_app.ClientCommunication.Downstream;

import retrofit2.http.POST;

/**
 * API des FCM-Servers.
 * Kann von Retrofit-Objekten verwendet werden, um POST-Requests an den FCM Server zu schicken.
 *
 * Created by tina on 29.06.17.
 */
public interface FcmApi {

    @POST("fcm/send")
    public void sendDownStreamMessage(); //TODO: add parameters

}
