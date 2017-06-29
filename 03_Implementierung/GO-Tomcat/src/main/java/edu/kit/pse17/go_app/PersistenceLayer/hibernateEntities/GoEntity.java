package edu.kit.pse17.go_app.PersistenceLayer.hibernateEntities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Die Klasse ist via Hibernate auf die "GOs"-Relation in der DB gemappt. Ein Objekt repräsentiert ein Tupel
 * aus der Relation.
 *
 * Der Schlüssel der Relation ist das Attribut "id"
 *
 * Created by tina on 28.06.17.
 */

@Entity
@Table(name="GOs")
public class GoEntity implements Serializable {

    /**
     * eindeutige Go-ID
     * Attribut ist der Primärschlüssel der Relation
     */
    private int id;

    /**
     * Name des GOs
     */
    private String name;

    /**
     * Beschreibung des GOs
     */
    private String description;

    /**
     * Der GO-Verantwortliche des GOs.
     * Attribut ist Fremdschlüssel der Relation "users"
     */
    private UserEntity owner;

    /**
     * Der Beginn des GOs
     */
    private Date start;

    /**
     * Das Ende des GOs
     */
    private Date end;

    /**
     * Breitengrad des Zielorts des GOs
     */
    private float lat;

    /**
     * Längengrad des Zielorts des GOs
     */
    private float lon;

    /**
     * Clustering-Schwellwert, der für das GO bestimmt wurde
     */
    private int threshold;

    /**
     * Gruppe, zu der das GO gehört.
     * Attribut ist Fremdschlüssel der Relation "groups"
     */
    private GroupEntity groupEntity;


    public GoEntity(int id, String name, String description, UserEntity owner, Date start, Date end, float lat, float lon, int threshold, GroupEntity groupEntity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.start = start;
        this.end = end;
        this.lat = lat;
        this.lon = lon;
        this.threshold = threshold;
        this.groupEntity = groupEntity;
    }

    @Column(name="id", nullable = false)
    public int getId() {
        return id;
    }

    @Column(name="name", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name="description", nullable = false)
    public String getDescription() {
        return description;
    }

    @Column(name="start", nullable = false)
    public Date getStart() {
        return start;
    }

    @Column(name = "end", nullable = false)
    public Date getEnd() {
        return end;
    }

    @Column(name="lat", nullable = true)
    public float getLat() {
        return lat;
    }

    @Column(name="lon", nullable = true)
    public float getLon() {
        return lon;
    }

    @Column(name="groupEntity", nullable = false)
    public GroupEntity getGroupEntity() {
        return groupEntity;
    }

    @Column(name="threshold", nullable = false)
    public int getThreshold() {
        return threshold;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }
}
