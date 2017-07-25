package edu.kit.pse17.go_app.viewModel;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import edu.kit.pse17.go_app.model.Status;
import edu.kit.pse17.go_app.model.entities.Cluster;
import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.repositories.GroupRepository;
import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApi;
import edu.kit.pse17.go_app.viewModel.livedata.GroupListLiveData;
import retrofit2.Call;

/**
 * Stellt die Daten für die GroupDetailActivity View-Komponente zur Verfügung und übernimmt
 * die Kommunikation mit der Programmlogik, um die richtigen Daten an die View weiterzugeben.
 *
 * Das ViewModel hat keine Abhängigkeit zu der View und wird, anders als die Views,
 * bei Konfigurationsänderungen nicht zerstört, sondern bleibt erhalten.
 */


public class GroupListViewModel extends ViewModel {

    /*
    * speichert die current Instanz von GrouplistViewModel, damit die Daten von Commands
    * zugreifbar sind
    * */

    public static GroupListViewModel currentViewModel;
    /**
     * Die Id des Benutzers, die in dem ViewModel gespeichert ist.
     */
    private String uId;

    /**
     * Das Datenobjekt, das an die View weitergegeben werden soll, gespeichert als LiveData-Objekt,
     * wodurch es für die View observable ist.
     */
    private GroupListLiveData data;

    /**
     * Das Grouprepository, das für die beschaffung der Daten zuständig ist.
     */
    private GroupRepository groupRepo;


    @Inject
    public GroupListViewModel() {
        this.groupRepo = new GroupRepository(new TomcatRestApi() {
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
        });
        currentViewModel = this;
    }

    public void init(String uId) {
        this.uId = uId;
    }
    /*
    * Gibt zurück eine Liste aller Gruppen für ein User
    * */

    public GroupListLiveData getGroups() {
        if(data == null){
            data = new GroupListLiveData();
            data.setValue(groupRepo.fetchData());
        }
        return data;

    }
    protected List<Group> getData(){
        return data.getValue();
    }

    /*
    * Erzeugt eine neue Gruppe und leitet das weiter, damit die Daten auf dem Server persistiert werden
    * group.id wird nicht berücksichtigt bei der Erstellung.
    * */

    public void createGroup(Group group){
        //now just add a group to live data here, later maybe change to server applicable stuff
        //groupRepo.createGroup(group);
        List<Group> newdata = data.getValue();
        newdata.add(group);
        data.setValue(newdata);


        Log.d("VIewModel", "HasObservers? " + data.hasObservers());


    }

    /*
    * Gibt zurück die Instanz des Viewmodels
    * */

    public static GroupListViewModel getCurrentGroupListViewModel(){
        return currentViewModel;
    }

}
