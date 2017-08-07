package edu.kit.pse17.go_app.PersistenceLayer.daos;

import javax.persistence.EntityNotFoundException;

/**
 * Bei diesem Interface handelt es sich um ein Interface für eine Data Access Object Klasse, die
 * die Datenbankzugriffe in sich kapselt.
 *
 * Dieses Interface definiert Methoden auf einer Datenbank, die standarmäßig für jede Tabelle zur Verfügung stehen sollten (CRUD).
 * daher wird dieses Interface von jeder DAO-Klasse implementiert.
 *
 * Das Interface beihaltet zwei Genreics. Das Generic T definiert den entity-Typen, der in der jeweilgen Tabelle verwaltet wird.
 * Das Generic PK ist der Datentyps des Primärschlüssels der Datenbanktabelle.
 *
 * Die Methoden dieses Interfaces werden von dieser DAO Klasse implementiert und sind nach außen sichtbar.
 * Sie werden aufgerufen, von den RestController-Klassen, denn von dort werden die Server-Anfragen, die von Clients
 * gestellt werden, an die Persistence-Klassen weitergeleitet.
 */
public interface AbstractDao<T, PK> {

    /**
     * Diese Methode gibt ein Entity-Objekt zurück, das anhand seines Primärschlüssels aus der Datenbank geholt wurde.
     *
     * @param key Der Primärschlüssel der Entity, die aus der Datenbank geholt werden soll. Der Datentyp wird von dem Generic PK bestimmt,
     *            mit dem das Interface implementiert wird.
     * @return Ein Entity-Objekt, das durch den Schlüssel identifiziert wurde. Konnte keine passende Entity in der Datenbank gefunden werden, gibt die Methode
     * null zurück.
     */
    public T get(PK key);

    /**
     * Diese Methode speichert eine neue Entity vom Typ T in der Datenbank ab. dabei wird das Entity-Objekt vor dem Methodenaufruf
     * erzeugt und der methode "fertig" übergeben.
     *
     * @param entity Das Entity-Objekt, das in der Datenbank gespeichert werden soll. Es wird garantiert, dass das Objekt, welches der Methode
     *               übergeben wird gültig ist (alle Konsistenzbedingungen der Datenbank werden erfüllt).
     */
    public PK persist(T entity);

    /**
     * Diese Methode löscht ein Entity-Objekt aus der Datenbank. Es werden außerdem automatisch alle Entities gelöscht, die mit der gelöschten
     * Entity assoziiert sind (Auch in anderen Datenbanktabellen, sodass der datenbestand nach der Ausführung der Methode konsistent ist)
     *
     * @param key Der Primärschlüssel der Entity, die aus der Datenbanktabelle gelöscht werden soll. Der datentyp wird durch das Generic PK bei der
     *            Implementierung der Klasse spezifiziert.
     *
     * @throws EntityNotFoundException existiert keine Entity mit dem spezifizierten Schlüssel, wird eine EntityNotFoundException geworfen, die
     * von der aufrufenden Klasse behandelt werden muss.
     */
    public void delete(PK key) throws EntityNotFoundException;

    /**
     * Diese Methode ändert Attributwerte eines bereits bestehenden Entity-Objekts. Dabei können nicht in jeder Entity-Klasse alle Attribute geändert werden.
     * Welche Attribute geändert werden können ist in den Entity-Klassen und in den implementierenden Dao-Klassen spezifiziert.
     *
     * @param t Ein Entity-Objekt, dass die geänderten Daten enthält. Das Objekt enthält die ID der Entity, die geändert werden soll und die Werte der Attribute
     *          die neu zugewiesen werden sollen. Alle anderen Attribute sind null, was der Methode signalisiert, dass diese Werte nicht geändert werden müssen.
     *
     * @throws EntityNotFoundException existiert keine Entity mit dem spezifizierten Schlüssel, wird eine EntityNotFoundException geworfen, die
      * von der aufrufenden Klasse behandelt werden muss.
     */
    public void update(T t) throws EntityNotFoundException;
}
