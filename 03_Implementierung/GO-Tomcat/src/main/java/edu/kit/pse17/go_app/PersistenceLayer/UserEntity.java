package edu.kit.pse17.go_app.PersistenceLayer;

import javax.persistence.*;
import java.util.Set;

/**
 * Dies ist eine Entity Klasse. Sie wird von dem Framework Hibernate dazu verwendet, POJOS auf Tupel in einer Datenbank
 * zu mappen. Wie das Mapping geschieht ist in dieser Klasse durch Annotations festgelegt. Der Rest der Anwendung kann
 * somit überall mit Java-Objekten hantieren und muss sich nicht um die konkrete Implementierung der Datenbank kümmern.
 * <p>
 * Der Zugriff auf die Datenbank erfolgt nicht in dieser Klasse, sondern nur über eine DAO Klasse, die das Interface
 * UserDao implementiert.
 * <p>
 * Zusätzlich dient diese Klasse als Vorlage des Frameworks Gson zum Parsen von JSON-Objekten, die von der REST API
 * empfangen und gesendet werden. Die Attribute der Klasse bestimmen dabei die Struktur des JSON-Objekts.
 */
@Entity
@Table(name = "USERS")
public class UserEntity {

    /**
     * Eine im System eindeutige UserId. Diese Id wird generiert von dem Firebase Authentication Service und von dieser
     * Andwendung unverändert übernommen. Die Id wird nach der Registrierung nicht mehr verändert, bis der user seinen
     * Account löscht.
     */

    @Column(name = "uid")
    @Id
    private String uid;

    /**
     * Ein String, mit dem das Gerät, das der Benutzer im Augenblick benutzt, identifiziert werden kann. Diese Id wird
     * vom Firebease Cloud Messaging Service benötigt und erlaubt es dem Server eine Nachricht an einen Client zu
     * schicken, ohne dass dieser Client vorher den Server angesprochen haben muss.
     * <p>
     * Da sich durch Gerätewechsel oder Konfigurationsänderungen am Gerät die InstanceId ändern kann, muss die
     * DAO-Klasse eine Methode zur Änderung der InstanceId besitzen.
     */
    @Column(name = "instance_id")
    private String instanceId;

    /**
     * Der Benutzername des Users. Dieser muss nicht eindeutig sein. Er kann vom User nicht selbst bestimmt werden,
     * sondern wird übernommen, von dem Google-Account mit dem sich der user in der App angemeldet hat. Da der Benutzer
     * nach der Registrierung diesen Account nicht wechseln kann, bleibt auch der Benutzername die ganze Zeit
     * unverändert.
     */
    @Column(name = "name")
    private String name;

    /**
     * Die Email-Adresse, die mit dem Google-Account assoziiert ist, mit dem sich der Benutzer registriert hat.
     * Da der Benutzer nach der Registrierung diesen Account nicht wechseln kann,
     * bleibt auch der Benutzername die ganze Zeit unverändert.
     */
    @Column(name = "email")
    private String email;

    /**
     * Eine Liste mit allen Gruppen, in denen der Benutzer Mitglied ist. Dieses Feld enthält einen ForeignKeyConstraint:
     * Die Ids der Gruppenobjekte der Liste sind die Primärschlüssel in der Gruppenrelation. Für dieses Feld müssen
     * Methoden zum Ändern der Liste vorhanden sein, da sich die Gruppen, in denen der Benutzer Mitglied ist, verändern
     * können.
     */

    @ManyToMany(mappedBy = "members", cascade = CascadeType.ALL)
    private Set<GroupEntity> groups;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "requests")
    private Set<GroupEntity> requests;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private Set<GoEntity> gos;

    public UserEntity() {
    }

    public Set<GoEntity> getGos() {
        return gos;
    }

    public void setGos(Set<GoEntity> gos) {
        this.gos = gos;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(final String uid) {
        this.uid = uid;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(final String instanceId) {
        this.instanceId = instanceId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Set<GroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(final Set<GroupEntity> groups) {
        this.groups = groups;
    }

    public Set<GroupEntity> getRequests() {
        return requests;
    }

    public void setRequests(final Set<GroupEntity> requests) {
        this.requests = requests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;

        UserEntity that = (UserEntity) o;

        if (getUid() != null ? !getUid().equals(that.getUid()) : that.getUid() != null) return false;
        if (getInstanceId() != null ? !getInstanceId().equals(that.getInstanceId()) : that.getInstanceId() != null)
            return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        return getEmail() != null ? getEmail().equals(that.getEmail()) : that.getEmail() == null;
    }

    @Override
    public int hashCode() {
        int result = getUid() != null ? getUid().hashCode() : 0;
        result = 31 * result + (getInstanceId() != null ? getInstanceId().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        return result;
    }
}
