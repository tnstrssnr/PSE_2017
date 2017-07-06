package edu.kit.pse17.go_app.repositories;

import android.content.SharedPreferences;

import java.util.concurrent.Executor;

import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApi;

/**
 * Dieses Repository verwaltet und vermittelt Datenanfragen und Datenänderungen, die mit dem Benutzerkonto
 * selbst verknüpfte Informationen betreffen.
 *
 * Im Gegensatz zu anderen Repositories spricht diese Klasse auch die SharedPreferences des Systems an.
 */

public class UserRepository {

    private final TomcatRestApi webService;
    private final SharedPreferences sharedPrefManager;
    private final Executor executor;

    public UserRepository(TomcatRestApi webService, SharedPreferences sharedPrefManager, Executor executor) {
        this.webService = webService;
        this.sharedPrefManager = sharedPrefManager;
        this.executor = executor;
    }
}
