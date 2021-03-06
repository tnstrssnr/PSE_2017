package edu.kit.pse17.go_app.PersistenceLayer;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Dies ist eine Entity Klasse. Sie wird von dem Framework Hinbernate dazu verwendet, POJOS auf Tupel in einer Datenbank
 * zu mappen. Wie das Mapping geschieht ist in dieser Klasse durch Annotations festgelegt. Der Rest der Anwendung kann
 * somit überall mit Java-Objekten hantieren und muss sich nicht um die konkrete Implementierung der Datenbank kümmern.
 * <p>
 * Der Zugriff auf die Datenbank erfolgt nicht in dieser Klasse, sondern nur über eine DAO Klasse, die das Interface
 * GoDao implementiert.
 * <p>
 * Zusätzlich dient diese Klasse als Vorlage des Frameworks Gson zum Parsen von JSON-Objekten, die von der REST API
 * empfangen und gesendet werden. Die Attribute der Klasse bestimmen dabei die Struktur des JSON-Objekts.
 */
@Entity
@Table(name = "GOS")
public class GoEntity {

    /**
     * Eine global eindeutige Nummer, anhand derer ein Go-Objekt eindeutig identifiziert werden kann. Die Id ist eine
     * positive ganze Zahl im Wertebereich des Datentyps long. Nach Erzeugung des Objekts kann sie bis zu seiner
     * Zerstörung nicht verändert werden.
     */
    @Column(name = "go_id")
    @Id
    @GeneratedValue
    private long ID;

    /**
     * Die Id der Gruppe, in der dieses Go angelegt wurde.
     * Es muss sich dabei um eine gültige Gruppen-Id handeln.
     * <p>
     * Nach Erzeugung der Entity ist der Wert dieser Variable nicht mehr veränderbar.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group;


    /**
     * Die userId des Benutzer der das Go erstellt hat und somit der Go-Verantwortliche ist.
     * Es muss sich dabei um eine gültige Userid handeln.
     * <p>
     * Nach Erzeugung der Entity ist der Wert dieser Variable nicht mehr veränderbar.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner")
    private UserEntity owner;

    /**
     * Der Name des Gos. Dieser muss nicht eindeutig sein. Es handelt sich dabei um einen String, der weniger als 50
     * Zeichen enthält. Der Name eines Gos kann nachträglich (nach Erzeugung des Objekts) geändert werden, es sind
     * entsprechende Methoden zu implementieren. Generiert wird die Id automatisch bei der Persistierung des
     * Entity-Objekts in der Datenbank. Dadurch ist die Eindeutigkeit der Id garantiert.
     */
    @Column(name = "name")
    private String name;

    /**
     * Eine textuelle Beschreibung des Gos. Diese muss nicht eindeutig sein. Es handelt sich dabei um einen String, der
     * weniger als 140 Zeichen enthält. Die Beschreibung eines Gos kann nachträglich (nach Erzeugung des Objekts)
     * geändert werden, es sind entsprechende Methoden zu implementieren.
     */
    @Column(name = "description")
    private String description;

    /**
     * Der Startzeitpunkt des Gos. Er bestimmt ab wann die Standortverfolgung bei einem Go gestartet wird. Dabei darf
     * der zeitpunkt bei der Zuweisung der Variable nicht in der Vergangenheit befinden.
     * <p>
     * Der Startzeitpunkt eines Gos kann nachträglich (nach Erzeugung des Objekts) geändert werden, es sind
     * entsprechende Methoden zu implementieren.
     */
    @Column(name = "start")
    private String start;

    /**
     * Der Endzeitpunkt des Gos. Er bestimmt ab wann die Standortverfolgung bei einem Go gestoppt wird. Dabei darf der
     * Zeitpunkt nie vor dem Startzeitpunkt befinden.
     * <p>
     * Der Endzeitpunkt eines Gos kann nachträglich (nach Erzeugung des Objekts) geändert werden, es sind entsprechende
     * Methoden zu implementieren.
     */
    @Column(name = "end")
    private String end;

    /**
     * Falls es einen Zielort für das Go gibt, wird in diesem Feld der geographische Breitengrad des Zielorts
     * gespeichert. Der Wert muss als Breitengrad interpretierbar sein, muss also zwischen +90 und -90 liegen.
     * <p>
     * Wurde kein Zielort für das Gop bestimmt, kann der Wert dieses Feldes auch null sein.
     * <p>
     * Der Wert kann nachträglich (nach Erzeugung des Objekts) geändert werden, es sind entsprechende Methoden zu
     * implementieren.
     */
    @Column(name = "lat")
    private double lat;

    /**
     * Falls es einen Zielort für das Go gibt, wird in diesem Feld der geographische Längengrad des Zielorts
     * gespeichert. Der Wert muss als Längengrad interpretierbar sein, muss also zwischen +180 und -180 liegen.
     * <p>
     * Wurde kein Zielort für das Go bestimmt, kann der Wert dieses Feldes auch null sein.
     * <p>
     * Der Wert kann nachträglich (nach Erzeugung des Objekts) geändert werden, es sind entsprechende Methoden zu
     * implementieren.
     */
    @Column(name = "lon")
    private double lon;

    /**
     * Eine Map mit allen Teilnehmern des Gos, um ihnen ihren Teilnahmestatus zuzuweisen.
     * <p>
     * Bei Erzeugung eines Objekts dieser Klasse wird dem Go-Verantwortlichen automatisch der Status GOING
     * zugewiesen, allen anderen Gruppenmitgliedern der Status NOT_GOING. . Es besteht keine Abhängigkeit dieser Liste
     * zur LocationService-Klasse oder anderen Klassen, die von LocationService benutzt werden.
     * <p>
     * Es dürfen nur Benutzer Teil dieser Liste sein, die auch Teil der Gruppe sind, in der das Go erstellt wurde. Jedes
     * Mitglied der Gruppe des Gos muss in der Liste enthalten sein.
     * <p>
     * Diese Liste ist veränderbar, es müssen also entsprechende Methoden implementioert werden, um Objekte zu der
     * Liste hinzuzufügen und entfernen zu können.
     */

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "GOING_USERS", joinColumns = {
            @JoinColumn(name = "go_id", nullable = false)
    }, inverseJoinColumns = {@JoinColumn(name = "uid", nullable = false)})
    private Set<UserEntity> goingUsers;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "NOT_GOING_USERS", joinColumns = {
            @JoinColumn(name = "go_id", nullable = false)
    }, inverseJoinColumns = {@JoinColumn(name = "uid", nullable = false)})
    private Set<UserEntity> notGoingUsers;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "GONE_USERS", joinColumns = {
            @JoinColumn(name = "go_id", nullable = false)
    }, inverseJoinColumns = {@JoinColumn(name = "uid", nullable = false)})
    private Set<UserEntity> goneUsers;

    public GoEntity() {

    }

    public GoEntity(GroupEntity group, UserEntity owner, String name, String description, String start, String end, double lat, double lon) {
        this.group = group;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
        this.lat = lat;
        this.lon = lon;
        this.goingUsers = new HashSet<>();
        goingUsers.add(owner);
        this.notGoingUsers = new HashSet<>();
        if (group != null) {
            for (UserEntity user : group.getMembers()) {
                if (!user.equals(owner)) {
                    notGoingUsers.add(user);
                }
            }
        }
        this.goneUsers = new HashSet<>();
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Set<UserEntity> getGoingUsers() {
        return goingUsers;
    }

    public void setGoingUsers(Set<UserEntity> goingUsers) {
        this.goingUsers = goingUsers;
    }

    public Set<UserEntity> getNotGoingUsers() {
        return notGoingUsers;
    }

    public void setNotGoingUsers(Set<UserEntity> notGoingUsers) {
        this.notGoingUsers = notGoingUsers;
    }

    public Set<UserEntity> getGoneUsers() {
        return goneUsers;
    }

    public void setGoneUsers(Set<UserEntity> goneUsers) {
        this.goneUsers = goneUsers;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoEntity)) return false;

        GoEntity goEntity = (GoEntity) o;

        if (getID() != goEntity.getID()) return false;
        if (Double.compare(goEntity.getLat(), getLat()) != 0) return false;
        if (Double.compare(goEntity.getLon(), getLon()) != 0) return false;
        if (getName() != null ? !getName().equals(goEntity.getName()) : goEntity.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(goEntity.getDescription()) : goEntity.getDescription() != null) {
            return false;
        }
        return (getStart() != null ? getStart().equals(goEntity.getStart()) : goEntity.getStart() == null) && (getEnd() != null ? getEnd().equals(goEntity.getEnd()) : goEntity.getEnd() == null);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (getID() ^ (getID() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getStart() != null ? getStart().hashCode() : 0);
        result = 31 * result + (getEnd() != null ? getEnd().hashCode() : 0);
        temp = Double.doubleToLongBits(getLat());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getLon());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
