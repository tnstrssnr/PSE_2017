package edu.kit.pse17.go_app.PersistenceLayer;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Dies ist eine Entity Klasse. Sie wird von dem Framework Hinbernate dazu verwendet, POJOS auf Tupel in einer Datenbank zu mappen.
 * Wie das Mapping geschieht ist in dieser Klasse durch Annotations festgelegt. Der Rest der Anwendung kann somit überall mit Java-Objekten
 * hantieren und muss sich nicht um die konkrete Implementierung der Datenbank kümmern.
 *
 * Der Zugriff auf die Datenbank erfolgt nicht in dieser Klasse, sondern nur über eine DAO Klasse, die das Interface GroupDao implementiert.
 *
 * Zusätzlich dient diese Klasse als Vorlage des Frameworks Gson zum Parsen von JSON-Objekten, die von der REST API empfangen und gesendet werden.
 * Die Attribute der Klasse bestimmen dabei die Struktur des JSON-Objekts.
 */
@Entity
@Table(name = "GROUPS")
public class GroupEntity {

    /**
     * Eine im gesamten System eindeutige ID-Nummer, anhand derer eine Gruppe eindeutig identifiziert werden kann.
     * Die ID ist eine positive ganze Zahl im Wertebereich des Datentyps long.
     * Nach Erzeugung des Objekts kann sie bis zu seiner Zerstörung nicht verändert werden. Generiert wird die Id automatisch bei der Persistierung des Entity-Objekts
     * in der Datenbank. Dadurch ist die Eindeutigkeit der ID garantiert.
     */
    @Column(name = "group_id")
    @Id
    @GeneratedValue
    private long ID;

    /**
     * Der Name der gruppe. Dieser muss nicht eindeutig sein.
     * Es handelt sich dabei um einen String, der weniger als 50 Zeichen enthält.
     * Der Name einer Gruppe kann nachträglich (nach Erzeugung des Objekts) geändert werden, es sind entsprechende Methoden zu implementieren.
     */
    @Column(name = "name")
    private String name;

    /**
     * Eine textuelle Ebschreibung der Gruppe. Diese muss nicht eindeutig sein.
     * Es handelt sich dabei um einen String, der weniger als 140 Zeichen enthält.
     * Die Beschreibung einer Gruppe kann nachträglich (nach Erzeugung des Objekts) geändert werden, es sind entsprechende Methoden zu implementieren.
     */
    @Column(name = "description")
    private String description;

    /**
     * Eine Liste, die alle Benutzer enthält, die vollwertige Mitglieder der Gruppe sind, also Benutzer die eine Mitgliedsanfrage erhalten und diese bestätigt haben,
     * plus der Ersteller der Gruppe, sollte er noch nicht ausgetreten sein.
     *
     * Bei der Erzeugung eines Objekts dieser Klasse, wird der Ersteller automatisch diser Liste hinzugefügt.
     *
     * Die Länge der Liste liegt zwischen 1 und 50. Sie kann niemals komplett leer sein. Gruppen mit 0 Mitgliedern werden automatisch gelöscht.
     *
     * Die Liste muss auch nach der Erzeugung des Objekts veränderbar sein, entsprechende Methoden sind zu implementieren.
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "MEMBERS", joinColumns = {
            @JoinColumn(name = "group_id", nullable = false)
    }, inverseJoinColumns = { @JoinColumn(name = "uid", nullable = false)})
    private Set<UserEntity> members;

    /**
     * Eine Liste mit allen Admins der Gruppe. Jeder Benutzer, der Teil dieser Liste ist, muss auch teil der members-Liste sein, da nur ein
     * Gruppenmitglied Administrator einer Gruppe sein kann.
     *
     * Bei der Erzeugung eines Objekts dieser Klasse, wird der Ersteller automatisch diser Liste hinzugefügt.
     *
     * Die Länge der Liste liegt zwischen 1 und 50. Sie kann niemals komplett leer sein. Gruppen mit 0 Mitgliedern werden automatisch gelöscht.
     *
     * Die Liste muss auch nach der Erzeugung des Objekts veränderbar sein, entsprechende Methoden sind zu implementieren.
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "ADMINS", joinColumns = {
            @JoinColumn(name = "group_id", nullable = false)
    }, inverseJoinColumns = { @JoinColumn(name = "uid", nullable = false)})
    private Set<UserEntity> admins;

    /**
     * Eine Liste, die alle Benutzer enthält, die eine Mitgliedschaftsanfrage zu dieser Gruppe erhalten haben, diese aber noch nicht beantwortet
     * haben.
     *
     * Bei Erzeugung dieses Objekts ist die Liste leer. Die Länge der Liste kann zwischen 0 und 50 liegen. Dabei hängt die maximale Länge auch
     * von der aktuellen Mitgliederanzahl ab. Die Summe von beidem darf 50 nicht übersteigen.
     *
     * Die Liste muss auch nach der Erzeugung des Objekts veränderbar sein, entsprechende Methoden sind zu implementieren.
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "REQUESTS", joinColumns = {
            @JoinColumn(name = "group_id", nullable = false)
    }, inverseJoinColumns = { @JoinColumn(name = "uid", nullable = false)})
    private Set<UserEntity> requests;

    /**
     * Eine Liste mit allen GOs, die in dieser Gruppe erstellt wurden. DAbei handelt es sich nur um GOs, die gerade aktiv sind, oder in Zukunft
     * noch aktiv sein werden. GOs, die schon vüruber sind werden automatisch aus dieser Liste gelöscht.
     *
     * Bei Erstellung eines Objekts dieser Klasse ist diese Liste leer. Die Länge der Liste liegt zwischen 0 und 10 GOs.
     *
     * Die Liste muss auch nach der Erzeugung des Objekts veränderbar sein, entsprechende Methoden sind zu implementieren.
     */

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Set<GoEntity> gos;

    public GroupEntity() {
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
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

    public Set<UserEntity> getMembers() {
        return members;
    }

    public void setMembers(Set<UserEntity> members) {
        this.members = members;
    }

    public Set<UserEntity> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<UserEntity> admins) {
        this.admins = admins;
    }

    public Set<UserEntity> getRequests() {
        return requests;
    }

    public void setRequests(Set<UserEntity> requests) {
        this.requests = requests;
    }

    public Set<GoEntity> getGos() {
        return gos;
    }

    public void setGos(Set<GoEntity> gos) {
        this.gos = gos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupEntity)) return false;

        GroupEntity that = (GroupEntity) o;

        if (getID() != that.getID()) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        if (getMembers() != null ? !getMembers().equals(that.getMembers()) : that.getMembers() != null) return false;
        if (getAdmins() != null ? !getAdmins().equals(that.getAdmins()) : that.getAdmins() != null) return false;
        if (getRequests() != null ? !getRequests().equals(that.getRequests()) : that.getRequests() != null)
            return false;
        return getGos() != null ? getGos().equals(that.getGos()) : that.getGos() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getID() ^ (getID() >>> 32));
        return result;
    }
}
