package edu.kit.pse17.go_app.PersistenceLayer.daos;

import java.util.List;

/**
 * DAO-Interface für einfache CRUD-Operationen auf der DB via Hibernate
 *
 * Created by tina on 28.06.17.
 */
public interface CrudDao<T> {

    /**
     * erstellt ein neues Tupel in der zur Entity T gehörenden Relation
     * @param t Entity-Objekt des zu erstellenden Tupels
     * @return Entity-Objekt des zu erstellenden Tupels
     */
    public T create(T t);

    /**
     * gibt ein Tupel aus der zur Entity T gehörenden Relation zurück
     * @param id ID des Tupels
     * @return Entity-Objekt des Tupels
     */
    public T read(String id);

    /**
     * aktualisiert ein Tupel aus der zu T gehörenden Relation
     *
     * @param t das Entity-Objekt des zu aktualisiernden Tupels
     * @return Das Entity-Objekt des aktualierten Tupels
     */
    public T update(T t);

    /**
     * löscht ein Tupel aus der zu T gehörenden Relation
     *
     * @param t das Entity-Objekt des zu löschenden Tupels
     * @return Das gelöschte Tupel
     */
    public T delete(T t);

    /**
     * gibt eine Liste aller Entity-Objekte der Relation T zurück
     *
     * @return Liste aller Entitiy-Objekte
     */
    public List<T> getAll();


}
