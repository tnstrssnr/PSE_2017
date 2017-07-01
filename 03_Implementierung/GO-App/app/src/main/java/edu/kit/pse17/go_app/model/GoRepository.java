package edu.kit.pse17.go_app.model;

import android.arch.lifecycle.LiveData;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApi;

/**
 * Created by tina on 01.07.17.
 */

@Singleton
public class GoRepository {

    private final TomcatRestApi webService;
    private final GoDao goDao;
    private final Executor executor;

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
}
