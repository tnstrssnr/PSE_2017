package edu.kit.pse17.go_app.repositories;

import android.arch.lifecycle.LiveData;
import android.location.Location;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.kit.pse17.go_app.model.Status;
import edu.kit.pse17.go_app.model.entities.Cluster;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApi;
import retrofit2.Call;

/**
 * Das Go-Repository ist verantwortlich für sämtliche Operationen auf den Go-Daten und stellt eine
 * einfache Schnittstelle zum Holen, Ändern und Löschen von Daten zur Verfügung.
 *
 * Bei einer Anfrage weiß das Repository, wo es die Daten holen muss (lokal oder vom remote Server).
 * Das Repository agiert als Vermittler zwischen der lokalen Datanbank und den Daten die die App vom Server erhält.
 */

@Singleton
public class GoRepository extends Repository<List<Go>>{

    private static GoRepository goRepo;
    /**
     * Eine Referenz auf das die Rest-Api, die der TomcatServer bereitstellt, um mit ihm kommunizieren zu können.
     */
    private final TomcatRestApi webService;

    /**
     * Eine DAO zum komminizieren mit der lokalen go-Datenbankrelation
     */
    // private final GoDao goDao;

    /**
     * Ein executor-objekt, um Anfragen auf einem separaten Hintergrundthread ausführen zu können.
     */
    private final Executor executor;

    @Inject
    public GoRepository(TomcatRestApi webService, /*GoDao godao*/ Executor executor) {
        this.webService = webService;
        this.executor = executor;
    }


    @Override
    public List<Go> fetchData() {
        return null;
    }

    @Override
    public List<Go> getUpdatedData() {
        return null;
    }
    /*
    * Ändert den teilnahmestatus von dem User mit id userId für die go mit id goId
    * */
    public void changeStatus(Status status, String goId, String userId){}
    /*
    * Erhält die Locations zu dem go mit Id goId, und schickt gleich eigene Location
    * */
    public LiveData<List<Cluster>> getLocations(String goId, Location location){
        return null;
    }
    /*
    * Ändert die Informationen von Go mit goid - go.goid
    * */
    public void editGo(Go go){}

    public static GoRepository getInstance(){
        if(goRepo == null){
            goRepo = new GoRepository(new TomcatRestApi() {
                @Override
                public Call<List<Group>> getData(String userId) {
                    return null;
                }

                @Override
                public Call<Void> createUser(String userId) {
                    return null;
                }

                @Override
                public Call<Void> deleteUser(String userId) {
                    return null;
                }

                @Override
                public Call<Void> registerDevice(String instanceId) {
                    return null;
                }

                @Override
                public Call<Long> createGroup(String name, String description, String userId) {
                    return null;
                }

                @Override
                public Call<Void> editGroup(long groupId, String name, String description) {
                    return null;
                }

                @Override
                public Call<Void> deleteGroup(Long groupId) {
                    return null;
                }

                @Override
                public Call<Void> acceptRequest(long groupId, String userId) {
                    return null;
                }

                @Override
                public Call<Void> removeMember(String userId, long groupId) {
                    return null;
                }

                @Override
                public Call<Void> inviteMember(long groupId, String userId) {
                    return null;
                }

                @Override
                public Call<Void> denyRequest(String userId, String groupId) {
                    return null;
                }

                @Override
                public Call<Void> addAdmin(String groupId, String userId) {
                    return null;
                }

                @Override
                public Call<Long> createGo(String name, String description, Date start, Date end, double lat, double lon, int threshold, long groupId, String userId) {
                    return null;
                }

                @Override
                public Call<Void> changeStatus(long goId, String userId, Status status) {
                    return null;
                }

                @Override
                public Call<List<Cluster>> getLocation(String goId) {
                    return null;
                }

                @Override
                public Call<Void> setLocation(String userId, long lat, long lon, String goId) {
                    return null;
                }

                @Override
                public Call<Void> deleteGo(String goId) {
                    return null;
                }

                @Override
                public Call<Void> editGo(String goId, String name, String description, Date start, Date end, long lat, long lon, int threshold) {
                    return null;
                }
            }, new Executor() {
                @Override
                public void execute(@NonNull Runnable command) {

                }
            }/*, GroupListViewModel.getCurrentGroupListViewModel().getObserver()*/);
        }
        return goRepo;
    }
}
