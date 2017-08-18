package edu.kit.pse17.go_app.repositories;

import android.arch.lifecycle.Observer;
import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import edu.kit.pse17.go_app.model.Status;
import edu.kit.pse17.go_app.model.entities.Cluster;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.model.entities.GroupMembership;
import edu.kit.pse17.go_app.model.entities.User;
import edu.kit.pse17.go_app.model.entities.UserGoStatus;
import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApi;
import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApiClient;
import edu.kit.pse17.go_app.view.GroupListActivity;
import edu.kit.pse17.go_app.viewModel.livedata.GroupListLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Das Go-Repository ist verantwortlich für sämtliche Operationen auf den Go-Daten und stellt eine
 * einfache Schnittstelle zum Holen, Ändern und Löschen von Daten zur Verfügung.
 * <p>
 * Bei einer Anfrage weiß das Repository, wo es die Daten holen muss (lokal oder vom remote Server).
 * Das Repository agiert als Vermittler zwischen der lokalen Datanbank und den Daten die die App vom Server erhält.
 */

@Singleton
public class GroupRepository extends Repository<List<Group>> {

    private static GroupRepository groupRepo;
    /**
     * Eine Referenz auf das die Rest-Api, die der TomcatServer bereitstellt, um mit ihm kommunizieren zu können.
     */
    private final TomcatRestApi apiService;

    /**
     * Eine Referenz auf das die Rest-Api, die der TomcatServer bereitstellt, um mit ihm kommunizieren zu können.
     */
    //private final GroupDao groupDao;

    /**
     * Ein executor-objekt, um Anfragen auf einem separaten Hintergrundthread ausführen zu können.
     */
    //private final Executor executor;

    private ArrayList<Group> list;
    private GroupListLiveData data;


    //@Inject
    private GroupRepository(Observer<List<Group>> observer) {
        this.apiService = TomcatRestApiClient.getClient().create(TomcatRestApi.class);
        if(data == null)
            this.data = new GroupListLiveData();
        data.observeForever(observer);
        Log.d("GroupRepoConstructor", "called");
    }

    /*
    * this should be called from GroupViewModel, id does not need 3rd parameter observer,
    * because it should've been given already by GroupListViewModel which is initialized before GroupViewModel
    * */
    public GroupRepository(/*GroupDao groupDao, Executor executor*/) {
        this.apiService = TomcatRestApiClient.getClient().create(TomcatRestApi.class);
        if(data == null)
            this.data = new GroupListLiveData();
        //this.executor = executor;
    }


    public void getData(String userId, String email, String instanceId) {
        final Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("userId", userId);
        parameters.put("email", email);
        parameters.put("instanceId", instanceId);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Call<List<Group>> call = apiService.getData(parameters);
                try {
                    list = (ArrayList<Group>) call.execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

        data.setValue(list);
    }

    /*
    * Erzeugt eine Gruppe, group.Id wird danach intern auf dem Server vergeben
    * */
    public void createGroup(String name, String description, String userId) {
        final Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("name", name);
        parameters.put("description", description);
        parameters.put("userId", userId);

        Call<Long> call = apiService.createGroup(parameters);
        call.enqueue(new Callback<Long>() {

            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {

            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("create_group", t.toString());
            }
        });
    }

    /*
    * Ändert die daten von der Group mit der Id group.Id, weil group.Id hier schon richtig sein muss
    * wegen der Wegwerfens der Id in ViewModels
    * */
    public void editGroup(long groupId, String name, String description) {
        final Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("groupId", Long.toString(groupId));
        parameters.put("name", name);
        parameters.put("description", description);

        Call<Void> call = apiService.editGroup(parameters);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("edit_group", t.toString());
            }
        });
    }

    public void deleteGroup(long groupId) {
        Call<Void> call = apiService.deleteGroup(groupId);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("delete_group", t.toString());
            }
        });
    }

    /*
    * Beantworte die Anfrage für eine Gruppe, answer True - Ja, answer False - Nein
    * */
    public void answerGroupRequest(long groupId, String userId, boolean answer) {
        if (answer) {
            acceptRequest(groupId, userId);
        } else {
            denyRequest(groupId, userId);
        }
    }

    private void acceptRequest(long groupId, String userId) {
        final Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("groupId", Long.toString(groupId));
        parameters.put("userId", userId);

        Call<Void> call = apiService.acceptRequest(parameters);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("accept_request", t.toString());
            }
        });
    }

    private void denyRequest(long groupId, String userId) {
        final Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("groupId", Long.toString(groupId));
        parameters.put("userId", userId);

        Call<Void> call = apiService.denyRequest(parameters);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("deny_request", t.toString());
            }
        });
    }

    public void removeMember(long groupId, String userId) {
        final Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("groupId", Long.toString(groupId));
        parameters.put("userId", userId);

        Call<Void> call = apiService.removeMember(parameters);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("remove_member", t.toString());
            }
        });
    }

    /*
    * Ein Teilnehmer hinzufügen
    * */
    public void inviteMember(long groupId, String email) {
        final Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("groupId", Long.toString(groupId));
        parameters.put("email", email);

        Call<Void> call = apiService.inviteMember(parameters);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("invite_member", t.toString());
            }
        });
    }

    public void addAdmin(long groupId, String userId) {
        final Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("groupId", Long.toString(groupId));
        parameters.put("userId", userId);

        Call<Void> call = apiService.addAdmin(parameters);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("add_admin", t.toString());
            }
        });
    }

    public void createGo(String name, String description, String start, String end,
                         double lat, double lon, int threshold, long groupId, String userId) {
        final Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("name", name);
        parameters.put("description", description);
        parameters.put("start", start);
        parameters.put("end", end);
        parameters.put("latitude", Double.toString(lat));
        parameters.put("longitude", Double.toString(lon));
        parameters.put("threshold", Integer.toString(threshold));
        parameters.put("groupId", Long.toString(groupId));
        parameters.put("userId", userId);

        Call<Long> call = apiService.createGo(parameters);
        call.enqueue(new Callback<Long>() {

            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {

            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("create_go", t.toString());
            }
        });
    }

    //----------------------------------------------------------------------

    public void onAdminAdded(String userId, long groupId) {
        for (Group group : list) {
            if (group.getId() == groupId) {
                List<GroupMembership> oldList = group.getMembershipList();

                for (GroupMembership member : oldList) {
                    if (member.getUser().getUid().equals(userId)) {
                        member.setAdmin(true);
                        break;
                    }
                }

                List<GroupMembership> newList = oldList;
                group.setMembershipList(newList);
                break;
            }
        }

        data.setValue(list);
    }

    /*
    *  Erzeugt ein neues GO in der Gruppe mit groupId
    * */
    public void onGoAdded(Go go, long groupId /*, String userId*/) {
        for (Group group : list) {
            if (group.getId() == groupId) {
                //go.setGroup(group);
                //go.setOwner("TODO bla bla");

                List<Go> old = group.getCurrentGos();
                old.add(go);
                List<Go> newGos = old;
                group.setCurrentGos(newGos);
                data.setValue(list);
                Log.d("GroupRepo", "Should call observer right now");
                break;
            }
        }
    }

    public void onGoEdited(Go go) {
        for (Group group : list) {
            List<Go> old = group.getCurrentGos();

            for (Go currentGo : old) {
                if (go.getId() == currentGo.getId()) {
                    go.setGroup(currentGo.getGroup());
                    go.setOwner(currentGo.getOwner());
                    go.setParticipantsList(currentGo.getParticipantsList());

                    old.remove(currentGo);
                    old.add(go);
                    List<Go> newGos = old;
                    group.setCurrentGos(newGos);
                    break;
                }
            }

            data.setValue(list);
            Log.d("GroupRepo", "Should call observer right now");
            break;
        }
    }

    public void onGoRemoved(long goId) {
        for (Group group : list) {
            List<Go> old = group.getCurrentGos();

            for (Go go : old) {
                if (go.getId() == goId) {
                    old.remove(go);
                    List<Go> newGos = old;
                    group.setCurrentGos(newGos);
                    break;
                }
            }

            data.setValue(list);
            break;
        }
    }

    public void onGroupEdited(Group group) {
        for (Group currentGroup : list) {
            if (group.getId() == currentGroup.getId()) {
                group.setCurrentGos(currentGroup.getCurrentGos());
                group.setMembershipList(currentGroup.getMembershipList());

                list.remove(currentGroup);
                list.add(group);
                data.setValue(list);
                break;
            }
        }
    }

    public void onGroupRemoved(long groupId) {
        for (Group group : list) {
            if (group.getId() == groupId) {
                list.remove(group);
                data.setValue(list);
                break;
            }
        }
    }

    public void onGroupRequestReceived(Group group) {
        //TODO ?!?!?!
    }

    public void onMemberAdded(User user) {
        //TODO ?!?!?!?
    }

    public void onMemberRemoved(String userId, long groupId) {
        for (Group group : list) {
            if (group.getId() == groupId) {
                List<GroupMembership> old = group.getMembershipList();

                for (GroupMembership membership : old) {
                    if (membership.getUser().getUid().equals(userId)) {
                        old.remove(membership);
                        group.setMemberCount(group.getMemberCount() - 1);
                        List<GroupMembership> newMemberList = old;
                        group.setMembershipList(newMemberList);
                        break;
                    }
                }

                data.setValue(list);
                break;
            }
        }
    }

    public void onRequestDenied() {
        //TODO !!!!!!!!!!!
    }

    public void onStatusChanged(String userId, long goId, int status) {
        for (Group group : list) {
            List<Go> old = group.getCurrentGos();

            for (Go go : old) {
                if (go.getId() == goId) {
                    List<UserGoStatus> statusList = go.getParticipantsList();

                    for (UserGoStatus userStatus : statusList) {
                        if (userStatus.getUser().getUid().equals(userId)) {
                            userStatus.setStatus(Status.values()[status]);
                            List<UserGoStatus> newStatusList = statusList;
                            go.setParticipantsList(newStatusList);
                            break;
                        }
                    }

                    List<Go> newGos = old;
                    group.setCurrentGos(newGos);
                    break;
                }
            }

            data.setValue(list);
            break;
        }
    }

    public void onUserDeleted() {
        //TODO !!!!!!!!!!!!
    }

    private void refreshGroup(long groupId) {
        /*executor.execute(new Runnable() {
            @Override
            public void run() {
                //fetch data from server
            }
        });*/
    }

    /*
    * getter fro GroupListLiveData
    * */
    public GroupListLiveData getData() {
        return data;
    }

    @Override
    public List<Group> fetchData() {
        return mockGroupData();

    }

    private List<Group> mockGroupData() {
        list = new ArrayList<>();
        User user1 = new User("id", "User1", "user1@gmail.com");
        User user2 = new User("id", "User2", "user2@gmail.com");
        User user3 = new User("id", "User3", "user3@gmail.com");
        User me = new User("MLsXON00mUPSQvKi7DggE6lTAsq1", "Vova", "vovaspilka1@gmail.com");
        List<GroupMembership> memberships = new ArrayList<>();
        Group group1 = new Group();
        group1.setId(1);
        GroupMembership myMembership = new GroupMembership(me, group1,true,true);
        memberships.add(myMembership);
        for(int i = 0; i < 10; i++){
            GroupMembership mem1 = new GroupMembership(user1, group1, false, false);
            memberships.add(mem1);
        }

        GroupMembership mem2 = new GroupMembership(user2, group1, false, false);
        memberships.add(mem2);
        GroupMembership mem3 = new GroupMembership(user3, group1, false, false);
        memberships.add(mem3);
        group1.setName("Group 1");
        group1.setDescription("DeScRiPtIoN");
        group1.setMembershipList(memberships);
        Go go1 = new Go();
        go1.setId(1);
        List<UserGoStatus> goStatusList = new ArrayList<>();
        goStatusList.add(new UserGoStatus(user1, go1, Status.GOING));
        goStatusList.add(new UserGoStatus(user2, go1, Status.NOT_GOING));
        goStatusList.add(new UserGoStatus(me, go1, Status.GONE));
        go1.setParticipantsList(goStatusList);
        go1.setName("GO1");
        go1.setOwner(me.getUid());

        go1.setDescription("GO DESCRIPTION");
        go1.setStart(new SimpleDateFormat().format(new Date()));
        go1.setEnd(new SimpleDateFormat().format(new Date()));
        List<Cluster> clusters = new ArrayList<>();
        clusters.add(new Cluster(49.012307, 8.402427,3));
        clusters.add(new Cluster(49.012334, 8.405621,4));
        clusters.add(new Cluster(49.011271, 8.404376,5));
        go1.setLocations(clusters);
        ArrayList<Go> gos = new ArrayList<>();
        gos.add(go1);
        group1.setCurrentGos(gos);
        group1.setId(1);
        Group group2 = new Group();
        List<GroupMembership> memberships2 = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            GroupMembership mem1 = new GroupMembership(user1, group2, true, true);
            memberships2.add(mem1);
        }
        group2.setId(2);
        group2.setName("Group 2");
        group2.setDescription("Description");
        group2.setCurrentGos(new ArrayList<Go>());
        group2.setMembershipList(memberships2);
        list.add(group1);
        list.add(group2);
        data.setValue(list);
        String userId = GroupListActivity.getUserId();
        return list;

    }

    @Override
    public List<Group> getUpdatedData() {
        return list;
    }


    public static GroupRepository getInstance() {
        if (groupRepo == null) {
            groupRepo = new GroupRepository(/*, GroupListViewModel.getCurrentGroupListViewModel().getObserver()*/);
        }
        return groupRepo;
    }
}
