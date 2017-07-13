package edu.kit.pse17.go_app.PersistenceLayer;

import java.util.List;
import java.util.Set;

/**
 * Dies ist eine Entity Klasse. Sie wird von dem Framework Hinbernate dazu verwendet, POJOS auf Tupel in einer Datenbank zu mappen.
 * Wie das Mapping geschieht ist in dieser Klasse durch Annotations festgelegt. Der Rest der Anwendung kann somit überall mit Java-Objekten
 * hantieren und muss sich nicht um die konkrete Implementierung der Datenbank kümmern.
 *
 * Der Zugriff auf die Datenbank erfolgt nicht in dieser Klasse, sondern nur über eine DAO Klasse, die das Interface UserDao implementiert.
 *
 * Zusätzlich dient diese Klasse als Vorlage des Frameworks Gson zum Parsen von JSON-Objekten, die von der REST API empfangen und gesendet werden.
 * Die Attribute der Klasse bestimmen dabei die Struktur des JSON-Objekts.
 */
public class UserEntity {

    /**
     * Eine im System eindeutige UserID. Diese ID wird generiert von dem Firebase Authentication Service und von dieser Andwendung unverändert
     * übernommen. Die ID wird nach der Registrierung nicht mehr verändert, bis der user seinen Account löscht.
     */
    private String uid;

    /**
     * Ein String, mit dem das Gerät, das der Benutzer im Augenblick benutzt identifiziert werden kann. Diese ID wird vom Firebease Cloud
     * Messaging Service benötigt und erlaubt es dem Server eine Nachricht an einen Client zu schicken, ohne dass dieser Client vorher den Server angesprochen
     * haben muss.
     *
     * Da sich durch Gerätewechsel oder Konfigurationsänderungen am Gerät die InstanceId ändern kann, muss die DAO-Klasse eine Methode zur Änderung der InstanceId
     * besitzen.
     */
    private String instanceId;

    /**
     * Der Benutzername des Users. Dieser muss nicht eindeutig sein. Er kann vom User nicht selbst bestimmt werden, sondern wird übernommen,
     * von dem Google-Account mit dem sich der user in der App angemeldet hat. Da der Benutzer nach der Regsitrierun diesen Account nicht wechseln kann,
     * bleibt auch der Benutzername die ganze Zeit unverändert.
     */
    private String name;

    /**
     * Die Email-Adresse, die mit dem Google-Account asoziiert ist, mit dem sich der Benutzer registriert hat.
     * Da der Benutzer nach der Registrierung diesen Account nicht wechseln kann,
     * bleibt auch der Benutzername die ganze Zeit unverändert.
     */
    private String email;

    /**
     * Eine Liste mit allen Gruppen, in denen der Benutzer Mitglied ist. Dieses Feld enthält einen ForeignKeyConstraint: Die IDs der Gruppenobjekte der Liste
     * sind die Primärschlüssel in der Gruppenrelation. Für dieses Feld müssen Methoden zum Ändern der Liste vorhanden sein, da sich die Gruppen, in den der Benutzer
     * Mitglied ist verändern können.
     */
    private Set<GroupEntity> groups;


    public UserEntity() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<GroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupEntity> groups) {
        this.groups = groups;
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
        if (getEmail() != null ? !getEmail().equals(that.getEmail()) : that.getEmail() != null) return false;
        return getGroups() != null ? getGroups().equals(that.getGroups()) : that.getGroups() == null;
    }

    @Override
    public int hashCode() {
        int result = getUid() != null ? getUid().hashCode() : 0;
        result = 31 * result + (getInstanceId() != null ? getInstanceId().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getGroups() != null ? getGroups().hashCode() : 0);
        return result;
    }
}
