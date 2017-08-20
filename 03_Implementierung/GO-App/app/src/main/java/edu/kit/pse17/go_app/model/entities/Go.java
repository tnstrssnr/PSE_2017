package edu.kit.pse17.go_app.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class. On the basis of this class, a table is generated
 * in the local SQLite database, which GO objects persists.
 * Access to the data is possible only through the GoEntityDAO-class.
 */
@Entity(tableName = "gos")
public class Go {

    /**
     * ID of the GO. The attribute is the primary key of the Relation,
     * and is not only locally, but globally unique.
     */
    @PrimaryKey
    @SerializedName("goId")
    private long id;

    /**
     * GO name (freely selectable, must not be unique).
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * Description of GO.
     */
    @SerializedName("description")
    @Expose
    private String description;

    /**
     * Start time of the GO.
     */
    @SerializedName("start")
    @Expose
    private String start;

    /**
     * End time of the GO.
     */
    @SerializedName("end")
    @Expose
    private String end;

    /**
     * The group of the GO.
     */
    @SerializedName("group")
    @Expose
    private Group group;

    /**
     * Latitude of GO destination (value is -1 if the destination is not set).
     */
    @SerializedName("lat")
    private double desLat;

    /**
     * Longitude of GO destination (value is -1 if the destination is not set).
     */
    @SerializedName("lon")
    private double desLon;

    /**
     * ID of the responsible user.
     */
    @SerializedName("userId")
    private String ownerId;

    /**
     * Name of the responsible user.
     */
    @SerializedName("ownerName")
    @Expose
    private String ownerName;

    /**
     * A list with the user status of each group member in the group of GOs.
     */
    @SerializedName("participantsList")
    @Expose
    private List<UserGoStatus> participantsList;

    /**
     * A list of the clusters of GO.
     */
    @SerializedName("locations")
    @Expose
    private List<Cluster> locations;

    public Go(long id, String name, String description, String start, String end, Group group, double desLat, double desLon, String ownerId, String ownerName, List<UserGoStatus> participantsList, List<Cluster> locations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
        this.group = group;
        this.desLat = desLat;
        this.desLon = desLon;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.participantsList = participantsList;
        this.locations = locations;
    }

    public Go() {
        this.name = "Default Go Name";
        this.participantsList = new ArrayList<>();
        this.locations = new ArrayList<>();
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public double getDesLat() {
        return desLat;
    }

    public void setDesLat(double lat) {
        this.desLat = lat;
    }

    public double getDesLon() {
        return desLon;
    }

    public void setDesLon(double lon) {
        this.desLon = lon;
    }

    public String getOwner() {
        return ownerId;
    }

    public void setOwner(String owner) {
        this.ownerId = owner;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public List<UserGoStatus> getParticipantsList() {
        return participantsList;
    }

    public void setParticipantsList(List<UserGoStatus> participantsList) {
        this.participantsList = participantsList;
    }

    /**
     * Get status by user ID.
     *
     * @param userId: ID of the user
     *
     * @return Status of the user
     */
    public UserGoStatus getStatus(String userId){
        for (UserGoStatus status: participantsList) {
            if(status.getUser().getUid().equals(userId)){
                return status;
            }
        }
        throw new NullPointerException(); //THIS SHOULD NOT HAPPEN
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Cluster> getLocations() {
        return locations;
    }

    public void setLocations(List<Cluster> locations) {
        this.locations = locations;
    }
}
