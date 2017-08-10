package edu.kit.pse17.go_app.serverCommunication.upstream;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Сеня on 21.07.2017.
 */

/**
 * The client to the Tomcat REST API Interface. It contains of the BASE URL
 * of the server and object of the Retrofit class.
 *
 * The Retrofit class is singleton.
 */
public class TomcatRestApiClient {

    /**
     * Base URL of the server
     */
    public static final String BASE_URL = "https://i43pc164.ipd.kit.edu/PSESoSe17Gruppe3/";

    /**
     * Retrofit singleton
     */
    private static Retrofit retrofit = null;

    /**
     * Method that builds and returns the Retrofit object (with GSON converter)
     *
     * @return Retrofit object
     */
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