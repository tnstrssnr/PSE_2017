package edu.kit.pse17.go_app.repositories;

import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.kit.pse17.go_app.model.Go;
import edu.kit.pse17.go_app.model.GoDao;
import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApi;

/**
 * Das Go-Repository ist verantwortlich für sämtliche Operationen auf den Go-Daten und stellt eine
 * einfache Schnittstelle zum Holen, Ändern und Löschen von Daten zur Verfügung.
 *
 * Bei einer Anfrage weiß das Repository, wo es die Daten holen muss (lokal oder vom remote Server).
 * Das Repository agiert als Vermittler zwischen der lokalen Datanbank und den Daten die die App vom Server erhält.
 */

@Singleton
public class GoRepository {

    /**
     * Eine Referenz auf das die Rest-Api, die der TomcatServer bereitstellt, um mit ihm kommunizieren zu können.
     */
    private final TomcatRestApi webService;

    /**
     * Eine DAO zum komminizieren mit der lokalen go-Datenbankrelation
     */
    private final GoDao goDao;

    /**
     * Ein executor-objekt, um Anfragen auf einem separaten Hintergrundthread ausführen zu können.
     */
    private final Executor executor;

    @Inject
    public GoRepository(TomcatRestApi webService, GoDao goDao, Executor executor) {
        this.webService = webService;
        this.goDao = goDao;
        this.executor = executor;
    }

    public LiveData<Go> getGo(long goId) {
        refreshGo(goId);
        //return LiveData directly from database
        return goDao.load(goId);
    }

    private void refreshGo(long groupId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //fetch data from server
            }
        });
    }

    public LiveData<List<Go>> getGosForUser(String uid) {

        return null;
    }
}
