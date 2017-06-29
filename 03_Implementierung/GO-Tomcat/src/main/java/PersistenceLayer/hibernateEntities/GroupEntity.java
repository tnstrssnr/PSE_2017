package PersistenceLayer.hibernateEntities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * * Die Klasse ist via Hibernate auf die "Groups"-Relation in der DB gemappt. Ein Objekt repräsentiert ein Tupel
 * aus der Relation.
 *
 * Schlüssel der Relation ist das Attribut "id"
 *
 * Created by tina on 28.06.17.
 */

@Entity
@Table(name="groups")
public class GroupEntity implements Serializable {

    /**
     * eindeutige ID der Gruppe.
     * Attribut ist Primärschlüssel der Relation
     */
    private int id;

    /**
     * Name der Gruppe
     */
    private String name;

    /**
     * Die Gruppenbeschreibung
     */
    private String description;

    /**
     * Pfad zur Dateil, in der das Gruppenbild gespeichert ist
     */
    private String imagePath;

    public GroupEntity(int id, String name, String description, String imagePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
