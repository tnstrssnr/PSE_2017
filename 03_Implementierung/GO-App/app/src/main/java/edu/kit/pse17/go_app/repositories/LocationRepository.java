package edu.kit.pse17.go_app.repositories;

import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Singleton;

import edu.kit.pse17.go_app.model.entities.Cluster;
import edu.kit.pse17.go_app.model.GoDao;
import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApi;

/**
 * Created by tina on 02.07.17.
 */

@Singleton
public class LocationRepository {

    private TomcatRestApi webservice;
    private Executor executor;
    private GoDao goDao;

    public LocationRepository(TomcatRestApi webservice, Executor executor, GoDao goDao) {
        this.webservice = webservice;
        this.executor = executor;
        this.goDao = goDao;
    }

    public LiveData<List<Cluster>> getLocationData(long goId) {
        return null;
    }
}
