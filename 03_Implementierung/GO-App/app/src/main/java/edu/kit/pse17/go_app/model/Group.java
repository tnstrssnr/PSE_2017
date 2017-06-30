package edu.kit.pse17.go_app.model;

import android.graphics.drawable.Icon;

import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse verwaltet Gruppen Objekte
 *
 * Created by tina on 17.06.17.
 */

public class Group {

    private int ID;
    private String name;
    private String description;
    private Icon icon;
    private int memberCount;

    private List<User> members;
    private List<User> admins;
    private List<User> requests;

    private GroupLocation groupLocation;

    /**
     * Konstruktor
     * @param ID eindeutige Nummer, mit der eine Gruppe identifiziert werden kann
     * @param name Gruppenname
     * @param description Gruppenbeschreibung
     * @param icon Gruppenicon
     * @param members Liste aller Gruppenmitglieder
     * @param memberCount Anzahl der Gruppenmitglieder
     */
    public Group(int ID, String name, String description, Icon icon, ArrayList<User> members, int memberCount) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.members = members;
        this.memberCount = memberCount;
    }

    /**
     * erzeugt ein neues Group-Objekt und speichert die Group-Daten in der Datenbank auf dem Tomcat Server
     *
     * @return die neue Gruppe
     */
    public static Group createGroup() {
        return null;
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

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public boolean isAdmin(User user) {
        return false;
    }

    private void onDataChanged() {

    }

    /**
     * Gibt eine Liste mit allen Gruppen des Benutzer mit der ID *uid* zurück
     *
     * @param uid Die User-ID des Benutzer
     * @return Liste mit Gruppen des Benutzers
     */
    public static List<Group> getAllGroups(String uid) {

        return null;
    }

    /**
     * Gibt eine Liste mit allen Gruppen zurück, zu denen der Benutzer mit der ID *uid* eingeladen wurde (und auf die Anfrage noch nicht geantwortet hat)
     *
     * @param uid Die User-ID des Benutzers
     * @return Liste mit allen Gruppenanfragen des Benutzers
     */
    public static List<Group> getAllGroupRequests(String uid) {

        return null;

    }
}
