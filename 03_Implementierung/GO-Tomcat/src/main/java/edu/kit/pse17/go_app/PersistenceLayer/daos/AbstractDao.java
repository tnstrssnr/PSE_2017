package edu.kit.pse17.go_app.PersistenceLayer.daos;

import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tina on 30.06.17.
 */
public abstract class AbstractDao<PK extends Serializable, T> {

    private Class<T> persistenClass;

    private SessionFactory sf;


    public abstract T create(T t);

    public abstract T getById(PK id);

    public abstract List<T> getAll();

    public abstract T update(T t);

    public abstract T delete(T t);

}
