package edu.kit.pse17.go_app.PersistenceLayer.daos;

/**
 * Created by tina on 08.08.17.
 */
public interface IAbstractDao<T, PK> {
    /**
     * Diese Methode gibt ein Entity-Objekt zurück, das anhand seines Primärschlüssels aus der Datenbank geholt wurde.
     *
     * @param key Der Primärschlüssel der Entity, die aus der Datenbank geholt werden soll. Der Datentyp wird von dem Generic PK bestimmt,
     *            mit dem das Interface implementiert wird.
     * @return Ein Entity-Objekt, das durch den Schlüssel identifiziert wurde. Konnte keine passende Entity in der Datenbank gefunden werden, gibt die Methode
     * null zurück.
     */
    T get(PK key);

    PK persist(T entity);

    void delete(T t);

    void update(T t);
}
