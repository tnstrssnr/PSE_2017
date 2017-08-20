package edu.kit.pse17.go_app.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Entity-Class. On the basis of this class, a table is generated
 * in the local SQLite database, which group membership objects persists.
 * This class shows all members of the group + Information
 * whether member an Administrator is or whether the membership
 * is just an open group request is.
 */
@Entity(tableName = "group_memberships",
        indices = {@Index("user"), @Index("group"), @Index(value = {"group", "isAdmin"}), @Index(value = {"group", "isRequest"})})
public class GroupMembership {

    /**
     * Member of the group.
     */
    @SerializedName("user")
    @Expose
    private User user;

    /**
     * Group.
     */
    @SerializedName("group")
    @Expose
    private Group group;

    /**
     * Boolean value indicating whether the user is an Administrator of the group.
     */
    @SerializedName("isAdmin")
    @Expose
    private boolean isAdmin;

    /**
     * Boolean value indicating whether the membership of the user is a
     * "regular membership" or an unanswered request.
     */
    @SerializedName("isRequest")
    @Expose
    private boolean isRequest;

    public GroupMembership() {
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
