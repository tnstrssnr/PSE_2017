package edu.kit.pse17.go_app.PersistenceLayer.clientEntities;


import com.google.gson.annotations.SerializedName;
import edu.kit.pse17.go_app.PersistenceLayer.Status;

/**
 * Die UserGoStatus Klasse beschreibt ein Klasse mit Objekten, die Informationen zu Usern, beziehungsweise ihrem Status("GOING",
 * "NOT_GOING","GONE") zu spezifischen Gruppen zusammenfasst.
 */

public class UserGoStatus {

    @SerializedName("user")
    private User user;

    @SerializedName("go")
    private Go go;

    @SerializedName("status")
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
