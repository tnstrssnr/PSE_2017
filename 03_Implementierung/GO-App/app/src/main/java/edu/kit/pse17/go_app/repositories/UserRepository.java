package edu.kit.pse17.go_app.repositories;

import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;

import java.util.concurrent.Executor;

import edu.kit.pse17.go_app.model.entities.User;
import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApi;

/**
 * Dieses Repository verwaltet und vermittelt Datenanfragen und Datenänderungen, die mit dem Benutzerkonto
 * selbst verknüpfte Informationen betreffen.
 *
 * Im Gegensatz zu anderen Repositories spricht diese Klasse auch die SharedPreferences des Systems an.
 */

public class UserRepository extends Repository<User>{

    private final TomcatRestApi webService;
    private final SharedPreferences sharedPrefManager;
    /*
    * Wird wie Thread benutzt, um die UI nicht anzuhalten
    * */
    private final Executor executor;

    public UserRepository(TomcatRestApi webService, SharedPreferences sharedPrefManager, Executor executor) {
        this.webService = webService;
        this.sharedPrefManager = sharedPrefManager;
        this.executor = executor;
    }

    @Override
    public LiveData<User> fetchData() {
        return null;
    }

    @Override
    public LiveData<User> getUpdatedData() {
        return null;
    }
    /*
    * Lösche den User mit uid.
    * */
    public void deleteUser(String uid){
        executor.; //Tomcat deleteUser request
    }
}
