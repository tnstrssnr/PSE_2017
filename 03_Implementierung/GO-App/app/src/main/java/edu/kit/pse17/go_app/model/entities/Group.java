package edu.kit.pse17.go_app.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.drawable.Drawable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import edu.kit.pse17.go_app.view.GroupListActivity;

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
    @SerializedName("groupId")
    private long id;

    /**
     * Der Name der Gruppe. Dieser muss nicht eindeutig sein
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * Der Beschreibungstext der Gruppe
     */
    @SerializedName("description")
    @Expose
    private String description;

    /**
     * Die Anzahl der Gruppenmitglieder
     */
    @SerializedName("memberCount")
    @Expose
    private int memberCount;

    /**
     * Bild der Gruppe
     */
    @SerializedName("icon")
    public transient Drawable icon;

    /**
     * Eine Liste mit allen Mitgliedern der Gruppe + Information ob das Mitglied ein Administrator ist
     * oder ob es sich bei der Mitgliedschaft lediglich um eine offene Grupppenanfrage handelt.
     */
    @SerializedName("membershipList")
    @Expose
    private List<GroupMembership> membershipList;

    /**
     * Eine Liste mit allen GOs der Gruppe.
     */

    @SerializedName("currentGos")
    @Expose
    private List<Go> currentGos;


    public Group(long id, String name, String description, int memberCount, Drawable icon, List<GroupMembership> membershipList, List<Go> currentGos) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.memberCount = memberCount;
        this.icon = icon;
        this.membershipList = membershipList;
        this.currentGos = currentGos;
    }

    public Group() {
        this.name = "Default Name";
        this.description = "Default Description";
        this.memberCount = 0;
        this.icon = GroupListActivity.default_group_icon;
        this.membershipList = new ArrayList<>();
        this.currentGos = new ArrayList<>();
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

    public boolean isRequest(String userId){
        for(GroupMembership membership : membershipList){
            if(membership.getUser().getUid().equals(userId)){
                return membership.isRequest();
            }
        }
        return false;
    }

    public boolean isAdmin(String userId){
        for(GroupMembership membership : membershipList){
            if(membership.getUser().getUid().equals(userId)){
                return membership.isAdmin();
            }
        }
        return false;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }


    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
