package PersistenceLayer.hibernateEntities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Die Klasse ist via Hibernate auf die "GoStatus"-Relation in der DB gemappt. Ein Objekt repräsentiert ein Tupel
 * aus der Relation.
 *
 * Der Schlüssel der Relation ist die Vereinigung der Attribute "user" und "go"
 * Created by tina on 28.06.17.
 */

@Entity
@Table(name = "goStatus")
public class GoStatusEntity implements Serializable{

    /**
     * Das GO, zu dem der Status des Benutzers gehört.
     * Das Attribut ist Fremdschlüssel der Relation "gos".
     *
     * Das Attribut ist Teil des Primärschlüssels dieser Relation.
     */
    GoEntity go;

    /**
     * Der User, zu dem der Status gehört.
     * Das Attribut ist Fremdschlüssel der Relation "users"
     *
     * Das Attribut ist Teil des Primörshlüssels dieser Relation.
     */
    UserEntity user;
    Status status;

    public GoStatusEntity(GoEntity go, UserEntity user, Status status) {
        this.go = go;
        this.user = user;
        this.status = status;
    }

    public GoEntity getGo() {
        return go;
    }

    public void setGo(GoEntity go) {
        this.go = go;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
