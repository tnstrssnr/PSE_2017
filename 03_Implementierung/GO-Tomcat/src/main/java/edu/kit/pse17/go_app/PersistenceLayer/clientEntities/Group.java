package edu.kit.pse17.go_app.PersistenceLayer.clientEntities;

import org.apache.poi.sl.draw.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private long id;
    private String name;
    private String description;
    private int memberCount;
    private Drawable icon;
    private List<GroupMembership> membershipList;
    private List<Go> currentGos;


    public Group(long id, String name, String description, int memberCount, Drawable icon, List<GroupMembership> membershipList, List<Go> currentGos) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.memberCount = memberCount;
        this.icon = null;
        this.membershipList = membershipList;
        this.currentGos = currentGos;
    }

    public Group() {
        this.name = "Default Name";
        this.description = "Default Description";
        this.memberCount = 0;
        this.icon = null;
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

    public List<GroupMembership> getMembershipList() {
        return membershipList;
    }

    public void setMembershipList(List<GroupMembership> membershipList) {
        this.membershipList = membershipList;
    }

    public List<Go> getCurrentGos() {
        return currentGos;
    }

    public void setCurrentGos(List<Go> currentGos) {
        this.currentGos = currentGos;
    }

    public boolean isRequest(String userId) {
        for (GroupMembership membership : membershipList) {
            if (membership.getUser().getUid().equals(userId)) {
                return membership.isRequest();
            }
        }
        return false;
    }

    public boolean isAdmin(String userId) {
        for (GroupMembership membership : membershipList) {
            if (membership.getUser().getUid().equals(userId)) {
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
