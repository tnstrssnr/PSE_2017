package edu.kit.pse17.go_app.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.drawable.Icon;

import java.util.List;

/**
 * Entity-Klasse. Anhand dieser Klasse wird eine Tabelle in der lokalen SQLite Datenbank generiert, die User-Objekte persistiert.
 * Der Zugriff auf die Daten läuft ausschließlich über die GroupDao-Klasse
 */

@Entity(tableName = "groups")
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
     * Die Anzahl der Gruppenmitglieder
     */
    public int memberCount;
    /**
     * Das Bild er Gruppe
     */
    @Ignore
    public Icon icon;

    /**
     * Boolean-Wert der angibt, ob der Benutzer ein Administrator der Gruppe ist.
     */
    public boolean isAdmin;

    /**
     * Boolean-Wert, der angibt, ob es sich bei der Mitgliedschaft des Benutzers um eine "ordentliche Mitgliedschaft"
     * oder um eine unbeantwortete Gruppenanfrage handelt.
     */
    public boolean isRequest;

    /**
     * Eine Liste mit allen Mitgliedern der Gruppe + Information on das Mitglied ein Administrator ist
     * oder ob es sich bei der Mitgliedschaft lediglich um eine offene Grupppenanfrage handelt.
     */
    public List<GroupMembership> membershipList;

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

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }
}
