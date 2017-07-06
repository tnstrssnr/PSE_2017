package edu.kit.pse17.go_app.model.entities;

import android.arch.persistence.room.Entity;

import edu.kit.pse17.go_app.model.Status;

/**
 * Created by tina on 02.07.17.
 */

@Entity(tableName = "user_go_status")
public class UserGoStatus {

    public User user;
    public Go go;
    public Status status;

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
