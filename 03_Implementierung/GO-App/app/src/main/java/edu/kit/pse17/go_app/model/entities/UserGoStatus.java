package edu.kit.pse17.go_app.model.entities;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import edu.kit.pse17.go_app.model.Status;

/**
 * Created by tina on 02.07.17.
 */

/**
 * Entity-Class. On the basis of this class, a table is generated
 * in the local SQLite database, which UserGoStatus objects persists.
 * This class represents all the participants of GOs + Status
 * of the participant in this GO.
 * Each user can only have Status within a GOs.
 */
@Entity(tableName = "user_go_status")
public class UserGoStatus {

    /**
     * Participant of the GO.
     */
    @SerializedName("user")
    @Expose
    private User user;

    /**
     * GO, in which a user is a participant.
     */
    @SerializedName("go")
    @Expose
    private Go go;

    /**
     * Status of a user at a GO.
     * Can be either NOT_GOING, GOING or GONE.
     */
    @SerializedName("status")
    @Expose
    private Status status;

    public UserGoStatus() {
    }

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
