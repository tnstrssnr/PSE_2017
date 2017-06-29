package edu.kit.pse17.go_app.PersistenceLayer.hibernateEntities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Die Klasse ist via Hibernate auf die "Users"-Relation in der DB gemappt. Ein Objekt repräsentiert ein Tupel
 * aus der Relation.
 *
 * Schlüssel der Relation ist das Attribut "id"
 *
 * Created by tina on 28.06.17.
 */

@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    /**
     * eindeutige User-Id
     * Primärschlüssel der Relation
     */
    private String id;


    /**
     * instanceId des Users (bzw. seinem Android-Gerät, dass er momentan benutzt). Diese wird benötigt, damit der Server
     * via FCM Benachrichtigungen auf das Gerät des Benutzers schicken kann.
     */
    private String instanceId;


    /**
     * E-Mail Adresse des Benutzers.
     */
    private String email;


    /**
     * selbst gewählter Username. Default-Wert ist die E-Mail Adresse.
     */
    private String username;


    /**
     * Pfad zur Datei, in der das Profilbild des Benutzers gespeichert ist.
     */
    private String imagePath;

    public UserEntity(String id, String instanceId, String email, String username, String imagePath) {
        this.id = id;
        this.instanceId = instanceId;
        this.email = email;
        this.username = username;
        this.imagePath = imagePath;
    }

    @Column(name = "instanceId")
    public String getInstanceId() {
        return instanceId;
    }

    @Column(name = "imagePath")
    public String getImagePath() {
        return imagePath;
    }

    @Id
    @Column(name = "id", nullable = false, unique = true)
    public String getId() {
        return id;
    }

    @Column(name="email", nullable = false)
    public String getEmail() {
        return email;
    }

    @Column(name="username")
    public String getUsername() {
        return username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
