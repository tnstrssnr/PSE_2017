package edu.kit.pse17.go_app.PersistenceLayer.hibernateEntities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Die Klasse ist via Hibernate auf die "GroupMembers"-Relation in der DB gemappt. Ein Objekt repräsentiert ein Tupel
 * aus der Relation.
 *
 * Der Schlüssel der Relation ist die Vereinigung der Attribute user und group.
 *
 * Created by tina on 28.06.17.
 */

@Entity
@Table(name = "groupMembers")
public class GroupMemberEntity implements Serializable {

    /**
     * Der User, der Teil eienr Gruppe ist.
     * Attribut ist Fremdschlüssel aus der Relation "Users"
     * Attribut ist Teil des Primärschlüssels der Relation
     */
    private UserEntity userEntity;

    /**
     * Gruppe, in der der betreffende User Mitglied ist.
     * Attribut ist Fremdschlüssel aus der Relation "Groups"
     * Attribut ist Teil des Primärschlüssels der Relation
     */
    private GroupEntity groupEntity;

    /**
     * Boolean-Wert, der angibt, ob der betreffende User Administrator der Gruppe ist
     */
    private boolean isAdmin;

    /**
     * Boolean-Wert, der angibt, ob es sich bei der Gruppenmitgliedschaft des Benutzers um eine unbestätigte Gruppenanfrage handelt
     */
    private boolean isRequest;

    public GroupMemberEntity(UserEntity userEntity, GroupEntity groupEntity, boolean isAdmin, boolean isRequest) {
        this.userEntity = userEntity;
        this.groupEntity = groupEntity;
        this.isAdmin = isAdmin;
        this.isRequest = isRequest;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public GroupEntity getGroupEntity() {
        return groupEntity;
    }

    public void setGroupEntity(GroupEntity groupEntity) {
        this.groupEntity = groupEntity;
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
