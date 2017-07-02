package edu.kit.pse17.go_app.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;

/**
 * Created by tina on 02.07.17.
 */

@Entity(tableName = "group_memberships",
        indices = {@Index("user"), @Index("group"), @Index(value = {"group", "isAdmin"}), @Index(value = {"group", "isRequest"})})
public class GroupMembership {

    public User user;
    public Group group;
    public boolean isAdmin;
    public boolean isRequest;

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
