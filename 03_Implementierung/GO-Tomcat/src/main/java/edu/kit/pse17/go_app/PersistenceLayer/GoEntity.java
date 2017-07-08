package edu.kit.pse17.go_app.PersistenceLayer;

import java.util.Date;
import java.util.List;

/**
 * Dies ist eine Entity Klasse. Sie wird von dem Framework Hinbernate dazu verwendet, POJOS auf Tupel in einer Datenbank zu mappen.
 * Wie das Mapping geschieht ist in dieser Klasse durch Annotations festgelegt. Der Rest der Anwendung kann somit überall mit Java-Objekten
 * hantieren und muss sich nicht um die konkrete Implementierung der Datenbank kümmern.
 *
 * Der Zugriff auf die Datenbank erfolgt nicht in dieser Klasse, sondern nur über eine DAO Klasse, die das Interface GoDao implementiert.
 *
 * Zusätzlich dient diese Klasse als Vorlage des Frameworks Gson zum Parsen von JSON-Objekten, die von der REST API empfangen und gesendet werden.
 * Die Attribute der Klasse bestimmen dabei die Struktur des JSON-Objekts.
 */
public class GoEntity {

    /**
     * Eine global eindeutige Nummer, anhand derer ein Go-Ovjekt eindeutig identifiziert werden kann.
     * Die ID ist eine positive ganze Zahl im Wertebereich des Datentyps long. Nach Erzeugung des Objekts kann sie bis zu seiner Zerstörung nicht verändert werden.
     */
    private long ID;

    /**
     * Die ID der Gruppe, in der dieses GO angelegt wurde.
     * Es muss sich dabei um eine gültige Gruppen-ID handeln.
     *
     * Nach Erzeugung der Entity ist der Wert dieser Variable nicht mehr veränderbar.
     */
    private long groupId;


    /**
     * Die userId des Benutzer der das GO erstellt hat und somit der Go-Verantwortliche ist.
     * Es muss sich dabei um eine gültige Userid handeln.
     *
     * Nach Erzeugung der Entity ist der Wert dieser Variable nicht mehr veränderbar.
     */
    private String owner;

    /**
     * Der Name des GOs. Dieser muss nicht eindeutig sein.
     * Es handelt sich dabei um einen String, der weniger als 50 Zeichen enthält.
     * Der Name eines GOs kann nachträglich (nach Erzeugung des Objekts) geändert werden, es sind entsprechende Methoden zu implementieren. Generiert wird die Id automatisch bei der Persistierung des Entity-Objekts
     * in der Datenbank. Dadurch ist die Eindeutigkeit der ID garantiert.
     */
    private String name;

    /**
     * Eine textuelle Ebschreibung des GOs. Diese muss nicht eindeutig sein.
     * Es handelt sich dabei um einen String, der weniger als 140 Zeichen enthält.
     * Die Beschreibung eines GOs kann nachträglich (nach Erzeugung des Objekts) geändert werden, es sind entsprechende Methoden zu implementieren.
     */
    private String description;

    /**
     * Der Startzeitpunkt des GOs. Er bestimmt ab wann die Standortverfolgung bei einem GO gestartet wird. Dabei darf der zeitpunkt
     * bei der Zuweisung der Variable nicht in der Vergangenheit befinden.
     *
     * Der Startzeitpunkt eines GOs kann nachträglich (nach Erzeugung des Objekts) geändert werden, es sind entsprechende Methoden zu implementieren.
     */
    private Date start;

    /**
     * Der Endzeitpunkt des GOs. Er bestimmt ab wann die Standortverfolgung bei einem GO gestoppt wird. Dabei darf der Zeitpunkt
     * nie vor dem Startzeitpunkt befinden.
     *
     * Der Endzwitpunkt eines GOs kann nachträglich (nach Erzeugung des Objekts) geändert werden, es sind entsprechende Methoden zu implementieren.
     */
    private Date end;

    /**
     * Falls es einen Zielort für das GO gibt, wird in diesem Feld der geografische Breitengrad des Zielorts gespeichert. Der Wert muss als Breitengrad interpretierbar sein,
     * muss also zwischen +90 und -90 liegen.
     *
     * Wurde kein Zielort für das GO bestimmt, kann der Wert dieses Feldes auch null sein.
     *
     * Der Wert kann nachträglich (nach Erzeugung des Objekts) geändert werden, es sind entsprechende Methoden zu implementieren.
     */
    private long lat;

    /**
     * Falls es einen Zielort für das GO gibt, wird in diesem Feld der geografische Längengrad des Zielorts gespeichert. Der Wert muss als Längengrad interpretierbar sein,
     * muss also zwischen +180 und -180 liegen.
     *
     * Wurde kein Zielort für das GO bestimmt, kann der Wert dieses Feldes auch null sein.
     *
     * Der Wert kann nachträglich (nach Erzeugung des Objekts) geändert werden, es sind entsprechende Methoden zu implementieren.
     */
    private long lon;

    /**
     * Eine Liste mit allen Teilnehmern des GOs, die den Teilnahmestatus "ABGELEHNT" haben. Dabei handelt es sich um den default-Status, d.h. bei der
     * Erzeugung einer Entity werden alle Benutzer, abgesehen vom GO-Verantwortlichen, zu dieser Liste hinzugefügt.
     * Es dürfen nur Benutzer Teil dieser Liste sein, die auch Teil der Gruppe sind, in der das Go erstellt wurde.
     * Der GO-Verantwortliche darf nicht Teil dieser Liste sein.
     * Jedes Gruppenmitglied muss in genau eienr der drei Statuslisten enthalten sein.
     *
     * Diese Liste ist veränderlich, es müssen also entsprechende Methoden implementioert werden, um Objekte zu der Liste hinzufügen und Entfernen zu können.
     */
    private List<UserEntity> notGoingUsers;

    /**
     * Eine Liste mit allen Teilnehmern des GOs, die den Teilnahmestatus "BESTÄTIGT" haben.
     * Es dürfen nur Benutzer Teil dieser Liste sein, die auch Teil der Gruppe sind, in der das GO erstellt wurde.
     * Bei Erzeugung eines Objekts dieser Klasse wird der GO-Verantwortliche automatisch zu dieser Liste hinzugefügt.
     * Jedes Gruppenmitglied muss in genau eienr der drei Statuslisten enthalten sein.
     *
     * Diese Liste ist veränderlich, es müssen also entsprechende Methoden implementioert werden, um Objekte zu der Liste hinzufügen und Entfernen zu können.
     */
    private List<UserEntity> goingUsers;

    /**
     * Eine Liste mit allen Teilnehmern des GOs, die den Teilnahmestatus "UNTERWEGS" haben,d.h. sie enthält alle Gruppenmitglieder, deren
     * Standorte im Moment verfolgt werden. Es besteht jedoch keine Abhängigkeit dieser Liste zur LocationService-Klasse oder anderen Klassen, die von LocationService
     * benutzt werden.
     * Es dürfen nur Benutzer Teil dieser Liste sein, die auch Teil der Gruppe sind, in der das GO erstellt wurde.
     * Jedes Gruppenmitglied muss in genau eienr der drei Statuslisten enthalten sein.
     *
     * Diese Liste ist veränderlich, es müssen also entsprechende Methoden implementioert werden, um Objekte zu der Liste hinzufügen und Entfernen zu können.
     */
    private List<UserEntity> goneUsers;

    public GoEntity() {

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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLon() {
        return lon;
    }

    public void setLon(long lon) {
        this.lon = lon;
    }

    public List<UserEntity> getNotGoingUsers() {
        return notGoingUsers;
    }

    public void setNotGoingUsers(List<UserEntity> notGoingUsers) {
        this.notGoingUsers = notGoingUsers;
    }

    public List<UserEntity> getGoingUsers() {
        return goingUsers;
    }

    public void setGoingUsers(List<UserEntity> goingUsers) {
        this.goingUsers = goingUsers;
    }

    public List<UserEntity> getGoneUsers() {
        return goneUsers;
    }

    public void setGoneUsers(List<UserEntity> goneUsers) {
        this.goneUsers = goneUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoEntity)) return false;

        GoEntity goEntity = (GoEntity) o;

        if (getID() != goEntity.getID()) return false;
        if (getLat() != goEntity.getLat()) return false;
        if (getLon() != goEntity.getLon()) return false;
        if (!getName().equals(goEntity.getName())) return false;
        if (!getDescription().equals(goEntity.getDescription())) return false;
        if (!getStart().equals(goEntity.getStart())) return false;
        if (!getEnd().equals(goEntity.getEnd())) return false;
        if (!getNotGoingUsers().equals(goEntity.getNotGoingUsers())) return false;
        if (!getGoingUsers().equals(goEntity.getGoingUsers())) return false;
        return getGoneUsers().equals(goEntity.getGoneUsers());
    }

    @Override
    public int hashCode() {
        int result = (int) (getID() ^ (getID() >>> 32));
        result = 31 * result + getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getStart().hashCode();
        result = 31 * result + getEnd().hashCode();
        result = 31 * result + (int) (getLat() ^ (getLat() >>> 32));
        result = 31 * result + (int) (getLon() ^ (getLon() >>> 32));
        result = 31 * result + getNotGoingUsers().hashCode();
        result = 31 * result + getGoingUsers().hashCode();
        result = 31 * result + getGoneUsers().hashCode();
        return result;
    }
}
