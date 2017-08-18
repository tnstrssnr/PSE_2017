package edu.kit.pse17.go_app.PersistenceLayer.clientEntities;

import edu.kit.pse17.go_app.ServiceLayer.Cluster;

import java.util.ArrayList;
import java.util.List;

public class Go {

    private long id;
    private String name;
    private String description;
    private String start;
    private String end;
    private Group group;
    private double desLat;
    private double desLon;
    private String ownerId;
    private String ownerName;
    private List<UserGoStatus> participantsList;
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

    public UserGoStatus getStatus(String userId) {
        for (UserGoStatus status : participantsList) {
            if (status.getUser().getUid().equals(userId)) {
                return status;
            }
        }
        throw new NullPointerException(); //THIS SHOULD NOT HAPPEN
        //return null;
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

    public void makeJsonable(boolean keepGroupInfo) {
        if(keepGroupInfo) {
            this.group.makeJsonable();
        } else {
            this.group = null;
        }
        for(UserGoStatus userGoStatus: participantsList) {
            userGoStatus.makeJsonable();
        }
    }
}
