package edu.kit.pse17.go_app.ServiceLayer;

/**
 * Created by tina on 30.06.17.
 */
public interface Observable<T> {

    public void register(Observer observer);

    public void unregister(Observer observer);

    public void notify(T t);

}
