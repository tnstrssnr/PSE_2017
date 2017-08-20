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
 * Entity-Class. On the basis of this class, a table is generated
 * in the local SQLite database, which group objects persists.
 * Access to the data is possible only through the GroupEntityDAO-class.
 */

@Entity(tableName = "groups")
public class Group {

    /**
     * ID of the group. The attribute is the primary key of the Relation,
     * and is not only locally, but globally unique.
     */
    @PrimaryKey
    @SerializedName("groupId")
    private long id;

    /**
     * The Name of the group. This does not have to be unique.
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * Description of the group.
     */
    @SerializedName("description")
    @Expose
    private String description;

    /**
     * Number of members.
     */
    @SerializedName("memberCount")
    @Expose
    private int memberCount;

    /**
     * Image of the group.
     */
    @SerializedName("icon")
    public transient Drawable icon;

    /**
     * A list with all the members of the group + Information,
     * whether the member the Administrator is
     * or whether the membership is just an open group request.
     */
    @SerializedName("membershipList")
    @Expose
    private List<GroupMembership> membershipList;

    /**
     * List of all GOs of the group.
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

    /**
     * Method that says if the user with user ID has an open request in this
     * group.
     *
     * @param userId: ID of the user
     *
     * @return Request or not
     */
    public boolean isRequest(String userId){
        for(GroupMembership membership : membershipList){
            if(membership.getUser().getUid().equals(userId)){
                return membership.isRequest();
            }
        }
        return false;
    }

    /**
     * Method that says if the user with user ID is admin in this
     * group.
     *
     * @param userId: ID of the user
     *
     * @return Admin or not
     */
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
