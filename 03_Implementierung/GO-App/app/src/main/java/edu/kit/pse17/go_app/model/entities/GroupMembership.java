package edu.kit.pse17.go_app.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Entity-Klasse. Anhand dieser Klasse wird eine Tabelle in der lokalen SQLite Datenbank generiert, die GroupMembership-Objekte persistiert.
 * Diese Klasse stellt alle Mitglieder der Gruppe + Information ob das Mitglied ein Administrator ist
 * oder ob es sich bei der Mitgliedschaft lediglich um eine offene Grupppenanfrage handelt dar.
 */
@Entity(tableName = "group_memberships",
        indices = {@Index("user"), @Index("group"), @Index(value = {"group", "isAdmin"}), @Index(value = {"group", "isRequest"})})
public class GroupMembership {

    /**
     * Mitglied der Gruppe.
     */
    @SerializedName("user")
    @Expose
    private User user;

    /**
     * Gruppe, zu der der Mitglied gehört.
     */

    @SerializedName("group")
    @Expose
    private Group group;

    /**
     * Boolean-Wert der angibt, ob der Benutzer ein Administrator der Gruppe ist.
     */
    @SerializedName("isAdmin")
    @Expose
    private boolean isAdmin;

    /**
     * Boolean-Wert, der angibt, ob es sich bei der Mitgliedschaft des Benutzers um eine "ordentliche Mitgliedschaft"
     * oder um eine unbeantwortete Gruppenanfrage handelt.
     */
    @SerializedName("isRequest")
    @Expose
    private boolean isRequest;
public GroupMembership(){

}
    public GroupMembership(User user, Group group, boolean isAdmin, boolean isRequest) {
        this.user = user;
        this.group = group;
        this.isAdmin = isAdmin;
        this.isRequest = isRequest;
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
