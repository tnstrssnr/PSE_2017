package edu.kit.pse17.go_app.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.drawable.Icon;

import java.util.List;

/**
 * Entity-Klasse. Anhand dieser Klasse wird eine Tabelle in der lokalen SQLite Datenbank generiert, die Gruppen-Objekte persistiert.
 * Der Zugriff auf die Daten läuft ausschließlich über die GroupEntityDAO-Klasse
 */

@Entity(tableName = "groups")
public class Group {

    /**
     * ID der Gruppe. Das Attribut ist der Primärschlüssel der Relation und nicht nur lokal, sondern global eindeutig.
     */
    @PrimaryKey
    private long id;

    /**
     * Der Name der Gruppe. Dieser muss nicht eindeutig sein
     */
    private String name;

    /**
     * Der Beschreibungstext der Gruppe
     */
    private String description;

    /**
     * Die Anzahl der Gruppenmitglieder
     */
    private int memberCount;
    /**
     * Das Bild der Gruppe
     */
    @Ignore
    public Icon icon;

    /**
     * Eine Liste mit allen Mitgliedern der Gruppe + Information ob das Mitglied ein Administrator ist
     * oder ob es sich bei der Mitgliedschaft lediglich um eine offene Grupppenanfrage handelt.
     */
    private List<GroupMembership> membershipList;

    /**
     * Eine Liste mit allen GOs der Gruppe.
     */
    private List<Go> currentGos;


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

    public List<GroupMembership> getMembershipList() { return membershipList; }

    public void setMembershipList(List<GroupMembership> membershipList) {
        this.membershipList = membershipList;
    }

    public List<Go> getCurrentGos() {
        return currentGos;
    }

    public void setCurrentGos(List<Go> currentGos) {
        this.currentGos = currentGos;
    }


    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }


}
