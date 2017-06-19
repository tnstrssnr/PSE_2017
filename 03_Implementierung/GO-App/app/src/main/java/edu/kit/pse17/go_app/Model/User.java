package edu.kit.pse17.go_app.Model;

import android.graphics.drawable.Icon;

import java.io.Serializable;
import java.util.List;

/**
 * Diese Klasse verwaltet User Objekte
 *
 * Created by tina on 17.06.17.
 */

public class User implements Serializable {

    private String uid;
    private String name;
    private String email;
    private Icon icon;

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public List<Group> getGroups() {
        return null;
    }

    public List<Group> getGroupRequests() {
        return null;
    }

    public Status getStatus(GO go) {
        return Status.NOT_GOING;
    }
}
