package edu.kit.pse17.go_app.PersistenceLayer.clientEntities;

import com.google.gson.annotations.SerializedName;

/**
 * Die Group-Membership Klasse beschreibt ein Klasse mit Objekten, die Informationen zu Gruppen, beziehungsweise ihre einzelnen Mitgliedern,
 * beinhaltet.
 */

public class GroupMembership {

    @SerializedName("user")
    private User user;

    @SerializedName("group")
    private Group group;

    @SerializedName("isAdmin")
    private boolean isAdmin;

    @SerializedName("isRequest")
    private boolean isRequest;

    public GroupMembership(User user, Group group, boolean isAdmin, boolean isRequest) {
        this.user = user;
        this.group = group;
        this.isAdmin = isAdmin;
        this.isRequest = isRequest;
    }

    public GroupMembership() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isRequest() {
        return isRequest;
    }

    public void setRequest(boolean request) {
        isRequest = request;
    }

}
