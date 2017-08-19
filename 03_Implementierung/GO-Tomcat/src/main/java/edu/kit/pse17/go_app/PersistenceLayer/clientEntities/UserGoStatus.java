package edu.kit.pse17.go_app.PersistenceLayer.clientEntities;


import edu.kit.pse17.go_app.PersistenceLayer.Status;

public class UserGoStatus {

    private User user;
    private Go go;
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
