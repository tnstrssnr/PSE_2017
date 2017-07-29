package edu.kit.pse17.go_app.repositories;

import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.inject.Singleton;

import edu.kit.pse17.go_app.model.Status;
import edu.kit.pse17.go_app.model.entities.Cluster;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.model.entities.GroupMembership;
import edu.kit.pse17.go_app.model.entities.User;
import edu.kit.pse17.go_app.model.entities.UserGoStatus;
import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApi;
import edu.kit.pse17.go_app.viewModel.livedata.GroupListLiveData;
import retrofit2.Call;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Das Go-Repository ist verantwortlich für sämtliche Operationen auf den Go-Daten und stellt eine
 * einfache Schnittstelle zum Holen, Ändern und Löschen von Daten zur Verfügung.
 *
 * Bei einer Anfrage weiß das Repository, wo es die Daten holen muss (lokal oder vom remote Server).
 * Das Repository agiert als Vermittler zwischen der lokalen Datanbank und den Daten die die App vom Server erhält.
 */

@Singleton
public class GroupRepository extends Repository<List<Group>>{

    private static GroupRepository groupRepo;
    /**
     * Eine Referenz auf das die Rest-Api, die der TomcatServer bereitstellt, um mit ihm kommunizieren zu können.
     */
    private final TomcatRestApi webservice;

    /**
     * Eine Referenz auf das die Rest-Api, die der TomcatServer bereitstellt, um mit ihm kommunizieren zu können.
     */
    //private final GroupDao groupDao;

    /**
     * Ein executor-objekt, um Anfragen auf einem separaten Hintergrundthread ausführen zu können.
     */
    private final Executor executor;

    private ArrayList<Group> list;
    private GroupListLiveData data = new GroupListLiveData();

    //@Inject
    private GroupRepository(TomcatRestApi webservice, /*GroupDao groupDao,*/ Executor executor, Observer<List<Group>> observer) {
        this.webservice = webservice;
        this.executor = executor;
        data.observeForever(observer);
        Log.d("GroupRepoConstructor", "called");
    }
    /*
    * this should be called from GroupViewModel, id does not need 3rd parameter observer,
    * because it should've been given already by GroupListViewModel which is initialized before GroupViewModel
    * */
    public GroupRepository(TomcatRestApi webservice, /*GroupDao groupDao,*/ Executor executor) {
        this.webservice = webservice;
        this.executor = executor;
    }

    private void refreshGroup(long groupId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //fetch data from server
            }
        });
    }
    /*
    * getter fro GroupListLiveData
    * */
    public GroupListLiveData getData(){
        return data;
    }

    @Override
    public List<Group> fetchData() {
            return mockGroupData();

    }

    private List<Group> mockGroupData(){
        list = new ArrayList<>();
        User user1 = new User("id", "User1", "user1@gmail.com");
        User user2 = new User("id", "User2", "user2@gmail.com");
        User user3 = new User("id", "User3", "user3@gmail.com");
        List<GroupMembership> memberships = new ArrayList<>();
        Group group1 = new Group();
        GroupMembership mem1 = new GroupMembership(user1, group1, false,false);
        memberships.add(mem1);
        GroupMembership mem2 = new GroupMembership(user2, group1, false,false);
        memberships.add(mem2);
        GroupMembership mem3 = new GroupMembership(user3, group1, false,false);
        memberships.add(mem3);
        group1.setName("Group 1");
        group1.setDescription("Description");
        group1.setMembershipList(memberships);
        Go go1 = new Go();
        List<UserGoStatus> goStatusList = new ArrayList<>();
        UserGoStatus userGoStatus1 = new UserGoStatus(user1, go1, Status.GOING);
        goStatusList.add(userGoStatus1);
        go1.setParticipantsList(goStatusList);
        go1.setName("GO1");
        go1.setDescription("GO DESCRIPTION");
        go1.setStart(new SimpleDateFormat().format(new Date()));
        go1.setEnd(new SimpleDateFormat().format(new Date()));
        ArrayList<Go> gos = new ArrayList<>();
        gos.add(go1);
        group1.setCurrentGos(gos);
        group1.setId(1);
        Group group2 = new Group();
        group2.setId(2);
        group2.setName("Group 2");
        group2.setDescription("Description");
        group2.setCurrentGos(new ArrayList<Go>());
        list.add(group1);
        list.add(group2);
        data.setValue(list);
        return list;
    }

    @Override
    public List<Group> getUpdatedData() {
        return list;
    }
    /*
    * Ein Teilnehmer hinzufügen
    * */
    public void addMember(String Email, String groupid){}
    /*
    * Ändert die daten von der Group mit der Id group.Id, weil group.Id hier schon richtig sein muss
    * wegen der Wegwerfens der Id in ViewModels
    * */
    public void editGroup(Group group){}
    /*
    * Erzeugt eine Gruppe, group.Id wird danach intern auf dem Server vergeben
    * */
    public void createGroup(Group group){
        list.add(group);
    }
    /*
    *  Erzeugt ein neues GO in der Gruppe mit groupId
    * */
    public void createGo(Go go, long groupId){
        for ( Group group: list) {
            if(group.getId() == groupId){
                List<Go> old = group.getCurrentGos();
                old.add(go);
                List<Go> newGos = old;
                group.setCurrentGos(newGos);
                data.setValue(list);
                Log.d("GroupRepo", "Should call observer right now");
            }
        }
    }
    /*
    * Beantworte die Anfrage für eine Gruppe, answer True - Ja, answer False - Nein
    * */
    public void answerGroupRequest(String groupId, boolean answer){

    }
    public static GroupRepository getInstance(){
        if(groupRepo == null){
            groupRepo = new GroupRepository(new TomcatRestApi() {

                @Override
                public Call<List<Group>> getData(@QueryMap Map<String, String> parameters) {
                    return null;
                }

                @Override
                public Call<Void> createUser(@Query("userId") String userId) {
                    return null;
                }

                @Override
                public Call<Void> deleteUser(@Query("userId") String userId) {
                    return null;
                }

                @Override
                public Call<Void> registerDevice(@Query("instanceId") String instanceId) {
                    return null;
                }

                @Override
                public Call<Long> createGroup(@QueryMap Map<String, String> parameters) {
                    return null;
                }

                @Override
                public Call<Void> editGroup(@QueryMap Map<String, String> parameters) {
                    return null;
                }

                @Override
                public Call<Void> deleteGroup(@Query("groupId") long groupId) {
                    return null;
                }

                @Override
                public Call<Void> acceptRequest(@QueryMap Map<String, String> parameters) {
                    return null;
                }

                @Override
                public Call<Void> removeMember(@QueryMap Map<String, String> parameters) {
                    return null;
                }

                @Override
                public Call<Void> inviteMember(@QueryMap Map<String, String> parameters) {
                    return null;
                }

                @Override
                public Call<Void> denyRequest(@QueryMap Map<String, String> parameters) {
                    return null;
                }

                @Override
                public Call<Void> addAdmin(@QueryMap Map<String, String> parameters) {
                    return null;
                }

                @Override
                public Call<Long> createGo(@QueryMap Map<String, String> parameters) {
                    return null;
                }

                @Override
                public Call<Void> changeStatus(@QueryMap Map<String, String> parameters) {
                    return null;
                }

                @Override
                public Call<List<Cluster>> getLocation(@QueryMap Map<String, String> parameters) {
                    return null;
                }

                @Override
                public Call<Void> deleteGo(@Query("goId") long goId) {
                    return null;
                }

                @Override
                public Call<Void> editGo(@QueryMap Map<String, String> parameters) {
                    return null;
                }
            }, new Executor() {
                @Override
                public void execute(@NonNull Runnable command) {

                }
            }/*, GroupListViewModel.getCurrentGroupListViewModel().getObserver()*/);
        }
        return groupRepo;
    }
}
