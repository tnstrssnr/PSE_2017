package edu.kit.pse17.go_app.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.graphics.drawable.Icon;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity-Klasse. Anhand dieser Klasse wird eine Tabelle in der lokalen SQLite Datenbank generiert, die User-Objekte persistiert.
 * Der Zugriff auf die Daten läuft ausschließlich über die GroupDao-Klasse
 */

@Entity
public class Group {

    /**
     * ID der Gruppe. Das Attribut ist der Primärschlüssel der Relation und nicht nur lokal, sondern global eindeutig.
     */
    @PrimaryKey
    public long id;

    /**
     * Der Name der Gruppe. Dieser muss nicht eindeutig sein
     */
    public String name;

    /**
     * Der beschreibungstext der Gruppe
     */
    public String description;

    /**
     * Das Bild er Gruppe
     */
    @Ignore
    public Icon icon;

    /**
     * Eine Liste aller Benutzer, die Mitglied der Gruppe sind. Dies schließt Benutzer, die eingeladen wurden, aber die Mitgliedschaft
     * noch nicht bestätigt haben nicht mit ein. Administratoren der Gruppe sind in dieser Liste ebenfalls aufgeführt.
     */
    @Relation(parentColumn = "id", entityColumn = "uid")
    public List<User> members;

    /**
     * Eine Liste aller Administratoren der Gruppe
     */
    @Relation(parentColumn = "id", entityColumn = "uid")
    public List<User> admins;

    /**
     * Eine Liste aller Benutzer, die zu der Gruppe eingeladen wurden, die mitgliedschaft aber nich nicht bestätigt haben.
     */
    @Relation(parentColumn = "id", entityColumn = "uid")
    public List<User> requests;


    /**
     * Boolean-Wert der angibt, ob der Benutzer ein Administrator der Gruppe ist.
     */
    public boolean isAdmin;

    /**
     * Boolean-Wert, der angibt, ob es sich bei der Mitgliedschaft des Benutzers um eine "ordentliche Mitgliedschaft"
     * oder um eine unbeantwortete Gruppenanfrage handelt.
     */
    public boolean isRequest;

    @Ignore
    private GroupLocation groupLocation;

    public Group() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<User> getAdmins() {
        return admins;
    }

    public void setAdmins(List<User> admins) {
        this.admins = admins;
    }

    public List<User> getRequests() {
        return requests;
    }

    public void setRequests(List<User> requests) {
        this.requests = requests;
    }

    public GroupLocation getGroupLocation() {
        return groupLocation;
    }

    public void setGroupLocation(GroupLocation groupLocation) {
        this.groupLocation = groupLocation;
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
