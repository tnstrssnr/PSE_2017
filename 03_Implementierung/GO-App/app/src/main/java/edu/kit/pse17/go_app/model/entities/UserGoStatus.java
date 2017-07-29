package edu.kit.pse17.go_app.model.entities;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import edu.kit.pse17.go_app.model.Status;

/**
 * Created by tina on 02.07.17.
 */

/**
 * Entity-Klasse. Anhand dieser Klasse wird eine Tabelle in der lokalen SQLite Datenbank generiert, die UserGoStatus-Objekte persistiert.
 * Diese Klasse stellt alle Teilnehmer eines GOs + Status des Teilnehmers bei diesem GO dar.
 * Jeder Benutzer darf nur Status innerhalb eines GOs haben.
 */
@Entity(tableName = "user_go_status")
public class UserGoStatus {

    /**
     * Teilnehmer eines GOs.
     */
    @SerializedName("user")
    @Expose
    private User user;

    /**
     * GO, bei dem ein Benutzer Teilnehmer ist.
     */
    @SerializedName("go")
    @Expose
    private Go go;

    /**
     * Status eines Benutzers bei einem GO.
     * Kann entweder NOT_GOING (Abgelehnt), oder GOING (Bestätigt) oder GONE (Unterwegs) lauten.
     */
    @SerializedName("status")
    @Expose
    private Status status;

    public UserGoStatus(User user, Go go, Status status) {
        this.user = user;
        this.go = go;
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Go getGo() {
        return go;
    }

    public void setGo(Go go) {
        this.go = go;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
