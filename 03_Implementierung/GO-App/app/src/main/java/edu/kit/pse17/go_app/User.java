package edu.kit.pse17.go_app;

import android.graphics.drawable.Icon;

import java.io.Serializable;

/**
 * Diese Klasse verwaltet User Objekte
 * Created by tina on 17.06.17.
 */

public class User implements Serializable {

    String uid;
    String name;
    String email;
    Icon icon;

    /**
     * Konstruktor
     * @param uid User-ID (--> übernommen von FirebaseUser-Objekt aus der FirebaseAPI (eindeutig)
     * @param name Benutzername
     * @param email E-Mailadresse, die bei der Anmeldung verwendet wurde. Wird verwendet, um User nach anderen Usern suchen zu lassen
     * @param icon Profilbild
     */
    public User(String uid, String name, String email, Icon icon) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.icon = icon;
    }
}
