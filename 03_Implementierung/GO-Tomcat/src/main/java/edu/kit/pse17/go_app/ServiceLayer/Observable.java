package edu.kit.pse17.go_app.ServiceLayer;

import edu.kit.pse17.go_app.PersistenceLayer.daos.PersistentClass;

/**
 * Created by tina on 30.06.17.
 */
public interface Observable<T extends PersistentClass> {

    public void register(Observer observer);

    public void unregister(Observer observer);

    public void notify(T t);

}
