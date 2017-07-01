package edu.kit.pse17.go_app.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tina on 01.07.17.
 */

@Singleton
public class GroupRepository {

    private final TomcatRestApi webservice;
    private final GroupDao groupDao;
    private final Executor executor;

    @Inject
    public GroupRepository(TomcatRestApi webservice, GroupDao groupDao, Executor executor) {
        this.webservice = webservice;
        this.groupDao = groupDao;
        this.executor = executor;
    }


    public LiveData<Group> getGroup(long groupId) {
       refreshGroup(groupId);
        //return LiveData directly from database
        return groupDao.load(groupId);
        
    }

    private void refreshGroup(long groupId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //fetch data from server
            }
        });
    }

}
