package edu.kit.pse17.go_app.repositories;

import android.arch.lifecycle.LiveData;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.kit.pse17.go_app.model.Group;
import edu.kit.pse17.go_app.model.GroupDao;
import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApi;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Das Go-Repository ist verantwortlich für sämtliche Operationen auf den Go-Daten und stellt eine
 * einfache Schnittstelle zum Holen, Ändern und Löschen von Daten zur Verfügung.
 *
 * Bei einer Anfrage weiß das Repository, wo es die Daten holen muss (lokal oder vom remote Server).
 * Das Repository agiert als Vermittler zwischen der lokalen Datanbank und den Daten die die App vom Server erhält.
 */

@Singleton
public class GroupRepository {

    /**
     * Eine Referenz auf das die Rest-Api, die der TomcatServer bereitstellt, um mit ihm kommunizieren zu können.
     */
    private final TomcatRestApi webservice;

    /**
     * Eine Referenz auf das die Rest-Api, die der TomcatServer bereitstellt, um mit ihm kommunizieren zu können.
     */
    private final GroupDao groupDao;

    /**
     * Ein executor-objekt, um Anfragen auf einem separaten Hintergrundthread ausführen zu können.
     */
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

    public LiveData<List<Group>> getGroupsForUser(String uid) {

        LiveData<List<Group>> groups = groupDao.getGroupsForUser(uid);

        if (groups == null) {
            try {
                Response response = webservice.getGroupsByUser(uid).execute();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return null;
    }
}
