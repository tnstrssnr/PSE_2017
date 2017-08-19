package edu.kit.pse17.go_app.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Entity-Klasse. Anhand dieser Klasse wird eine Tabelle in der lokalen SQLite Datenbank generiert, die User-Objekte persistiert.
 * Der Zugriff auf die Daten läuft ausschließlich über die UserEntityDAO-Klasse
 */

@Entity(tableName = "users")
public class User implements Serializable {

    /**
     * Die User-ID des Benutzers. Dieses Attribut ist der Primärschlüssel der Relation. Die ID wird bei der Registrierung
     * von Firebase Authentication Service übernommen.
     */
    @PrimaryKey
    @SerializedName("userId")
    private String uid;

    /**
     * Die Instance-ID eines Benutzers bzw. Android-Endgeräts wird vom Tomcat-Server verwendet, um Benachrichtigungen an das Gerät zu schicken.
     * Die wird von Firebase Cloud Messaging Service generiert.
     */
    @SerializedName("instanceId")
    @Expose
    private String instanceId;

    /**
     * Benutzername des Users. Dieser kann frei gewählt werden und muss nicht eindeutig sein. Der default-Wert ist die Email-Adresse des Benutzers.
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * Die Email-Adresse, mit der sich der Benutzer in der App registriert hat.
     */
    @SerializedName("email")
    @Expose
    private String email;

    /**
     * Profilbild des Benutzers.
     *//*
    @Ignore
    @SerializedName("icon")
    @Expose
    private Drawable icon;*/

    public User(String uid,  String email, String name) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public User(String uid, String instanceId, String name, String email) {
        this.uid = uid;
        this.instanceId = instanceId;
        this.name = name;
        this.email = email;
    }
    public User(){

    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
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

    /*public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }*/
}
