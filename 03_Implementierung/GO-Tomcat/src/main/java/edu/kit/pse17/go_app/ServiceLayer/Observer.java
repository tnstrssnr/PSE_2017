package edu.kit.pse17.go_app.ServiceLayer;

/**
 * Created by tina on 30.06.17.
 */
public abstract class Observer<K extends Observable<T>, T> {

    private T state;

    public abstract void update();

}
