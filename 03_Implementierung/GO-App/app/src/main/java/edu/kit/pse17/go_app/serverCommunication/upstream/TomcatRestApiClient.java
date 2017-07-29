package edu.kit.pse17.go_app.serverCommunication.upstream;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Сеня on 21.07.2017.
 */

public class TomcatRestApiClient {
    public static final String BASE_URL = "https://i43pc164.ipd.kit.edu/PSESoSe17Gruppe3/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}