package edu.kit.pse17.go_app.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.graphics.drawable.Icon;

import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse verwaltet Gruppen Objekte
 *
 * Created by tina on 17.06.17.
 */

@Entity
public class Group {

    @PrimaryKey
    public long id;
    public String name;
    public String description;
    public String imagePath;

    @Ignore
    public Icon icon;

    @Relation(parentColumn = "id", entityColumn = "uid")
    public List<User> members;

    @Relation(parentColumn = "id", entityColumn = "uid")
    public List<User> admins;

    @Relation(parentColumn = "id", entityColumn = "uid")
    public List<User> requests;

    @Ignore
    private GroupLocation groupLocation;

    public Group(long id, String name, String description, String imagePath, Icon icon, List<User> members, List<User> admins, List<User> requests, GroupLocation groupLocation) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.icon = icon;
        this.members = members;
        this.admins = admins;
        this.requests = requests;
        this.groupLocation = groupLocation;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
}
