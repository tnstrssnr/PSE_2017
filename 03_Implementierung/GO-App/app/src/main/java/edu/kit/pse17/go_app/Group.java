package edu.kit.pse17.go_app;

import android.graphics.drawable.Icon;

import java.util.ArrayList;

import edu.kit.pse17.go_app.User;

/**
 * Diese Klasse verwaltet Gruppen Objekte
 * 
 * Created by tina on 17.06.17.
 */

public class Group {

    private String name;
    private String description;
    private Icon icon;
    private ArrayList<User> members;
    private int memberCount;

    /**
     * Konstruktor
     * @param name Gruppenname
     * @param description Gruppenbeschreibung
     * @param icon Gruppenicon
     * @param members Liste aller Gruppenmitglieder
     * @param memberCount Anzahl der Gruppenmitglieder
     */
    public Group(String name, String description, Icon icon, ArrayList<User> members, int memberCount) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.members = members;
        this.memberCount = memberCount;
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

    public ArrayList<User> getMembers() {
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
}
