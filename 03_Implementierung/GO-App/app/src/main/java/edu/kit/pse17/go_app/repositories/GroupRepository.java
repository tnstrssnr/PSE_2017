package edu.kit.pse17.go_app.repositories;

import android.arch.lifecycle.Observer;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Singleton;

import edu.kit.pse17.go_app.model.Status;
import edu.kit.pse17.go_app.model.entities.Cluster;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.model.entities.GroupMembership;
import edu.kit.pse17.go_app.model.entities.User;
import edu.kit.pse17.go_app.model.entities.UserGoStatus;
import edu.kit.pse17.go_app.serverCommunication.upstream.Serializer;
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

    private Group groupWithoutId;
    private Go goWithoutId;

    //@Inject
    private GroupRepository(Observer<List<Group>> observer) {
        this.apiService = TomcatRestApiClient.getClient().create(TomcatRestApi.class);
        if (data == null)
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
        if (data == null)
            this.data = new GroupListLiveData();
        //this.executor = executor;
    }


    public void getData(final String userId, final String email, String instanceId, String userName) {

        Call<List<Group>> call  = apiService.getData(userId,"test","test" /*email, userName*/);
        call.enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {

                List<Group> list = response.body();
                if(list.get(0).getId() == -1){
                    data.setValue(new ArrayList<Group>());
                } else {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                String l = "zhopa";
                 StackTraceElement[] a = t.getStackTrace();
            }
        });

        /*Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Call<List<Group>> call = apiService.getData(userId, email);
                try {
                    list = (ArrayList<Group>) call.execute().body();
                    //data.setValue(list);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //data.setValue(list);
    }

    // TODO TO the server !
    private void getGroupData(String userId, long groupId) {

    }

    /*
    * Erzeugt eine Gruppe, group.Id wird danach intern auf dem Server vergeben
    * */
    public void createGroup(String name, String description, User user) {
        Group newGroup = new Group(0, name, description, 1, null, new ArrayList<GroupMembership>(), new ArrayList<Go>());
        newGroup.getMembershipList().add(new GroupMembership(user, newGroup, true, false));

        //add locally
        List<Group> newdata = data.getValue();
        if(newdata ==  null){
            newdata = new ArrayList<>();
        }
        Group group = new Group();
        group.setName(name);
        group.setDescription(description);
        group.setId(-123);
        List<GroupMembership> membership = new ArrayList<>();
        membership.add(new GroupMembership(user, group, true, false));
        group.setMembershipList(membership);
        newdata.add(group);
        groupWithoutId = group;
        data.setValue(newdata);

        //
        Serializer.makeJsonable(newGroup);
        String json = TomcatRestApiClient.gson.toJson(newGroup);

        Call<Long> call = apiService.createGroup(newGroup);
        call.enqueue(new Callback<Long>() {

            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                long code = response.body();
                GroupRepository.getInstance().onGroupIdassigned(code);
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("create_group", t.toString());
            }
        });
    }

    private void onGroupIdassigned(long code) {
        List<Group> oldGroups = data.getValue();
        for(Group group : oldGroups){
            if(group.getId() == -123){
                group.setId(code);
            }
        }
        data.setValue(oldGroups);
    }

    /*
    * Ändert die daten von der Group mit der Id group.Id, weil group.Id hier schon richtig sein muss
    * wegen der Wegwerfens der Id in ViewModels
    * */
    public void editGroup(long groupId, String name, String description) {
        Group alteredGroup = new Group(groupId, name, description, 0, null, null, null);

        Call<Void> call = apiService.editGroup(alteredGroup);
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

        Call<Void> call = apiService.acceptRequest(groupId, userId);
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

        Call<Void> call = apiService.denyRequest(groupId, userId);
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

        Call<Void> call = apiService.removeMember(groupId, userId);
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

        Call<Void> call = apiService.inviteMember(groupId, email);
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

        Call<Void> call = apiService.addAdmin(groupId, userId);
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
                         double lat, double lon, int threshold, Group group, String userId, String userName) {

        Go go = new Go(-321, name, description, start, end, group, lat, lon, userId, userName, new ArrayList<UserGoStatus>(), new ArrayList<Cluster>());
        goWithoutId = go;
        String json = TomcatRestApiClient.gson.toJson(go);
        Call<Long> call = apiService.createGo(go, group.getId(), userId);
        call.enqueue(new Callback<Long>() {

            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {

            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                StackTraceElement[] st = t.getStackTrace();
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
    public void onGoAdded(Go go, long groupId/*, String userId*/) {
        for (Group group : list) {
            if (group.getId() == groupId) {
                Go newGo = go;
                newGo.setGroup(group);
                //go.setOwner(?????);

                List<Go> old = group.getCurrentGos();
                old.add(newGo);
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
                    Go newGo = go;
                    newGo.setGroup(currentGo.getGroup());
                    newGo.setOwner(currentGo.getOwner());
                    newGo.setParticipantsList(currentGo.getParticipantsList());

                    old.remove(currentGo);
                    old.add(newGo);
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
                Group newGroup = group;
                newGroup.setCurrentGos(currentGroup.getCurrentGos());
                newGroup.setMembershipList(currentGroup.getMembershipList());

                list.remove(currentGroup);
                list.add(newGroup);
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
        for (Group currentGroup : list) {
            if (currentGroup.getId() == group.getId()) {
                list.remove(currentGroup);
                list.add(group);

                data.setValue(list);
                break;
            }
        }
    }

    public void onMemberAdded(User user, long groupId) {
        if (GroupListActivity.getUserId().equals(user.getUid())) {
            getGroupData(user.getUid(), groupId); // if added member
        }

        for (Group group : list) {
            if (group.getId() == groupId) {
                GroupMembership membership = new GroupMembership(user, group, false, false);
                List<GroupMembership> newList = group.getMembershipList();
                newList.add(membership);
                group.setMemberCount(group.getMemberCount() + 1);
                group.setMembershipList(newList);

                data.setValue(list);
                break;
            }
        }
    }

    public void onMemberRemoved(String userId, long groupId) {
        for (Group group : list) {
            if (group.getId() == groupId) {
                List<GroupMembership> oldMembershipList = group.getMembershipList();

                for (GroupMembership membership : oldMembershipList) {
                    if (membership.getUser().getUid().equals(userId)) {
                        oldMembershipList.remove(membership);
                        group.setMemberCount(group.getMemberCount() - 1);
                        List<GroupMembership> newMembershipList = oldMembershipList;
                        group.setMembershipList(newMembershipList);
                        break;
                    }
                }
                if (group.getMemberCount() == 0) {
                    list.remove(group);
                }

                List<Go> oldGoList = group.getCurrentGos();

                for (Go go : oldGoList) {
                    List<UserGoStatus> oldMemberList = go.getParticipantsList();

                    for (UserGoStatus member : oldMemberList) {
                        if (member.getUser().getUid().equals(userId)) {
                            oldMemberList.remove(member);
                            List<UserGoStatus> newMemberList = oldMemberList;
                            go.setParticipantsList(newMemberList);
                            break;
                        }
                    }

                    if (go.getParticipantsList().isEmpty() || go.getOwner().equals(userId)) {
                        oldGoList.remove(go);
                        List<Go> newGoList = oldGoList;
                        group.setCurrentGos(newGoList);
                    }
                }

                data.setValue(list);
                break;
            }
        }
    }

    public void onRequestDenied(String userId, long groupId) {
        for (Group group : list) {
            if (group.getId() == groupId) {
                List<GroupMembership> old = group.getMembershipList();

                for (GroupMembership membership : old) {
                    if (membership.getUser().getUid().equals(userId)) {
                        assert (membership.isRequest() == true);
                        membership.setRequest(false); // for consistency
                        old.remove(membership);
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

    public void onUserDeleted(String userId) {
        for (Group group : list) {
            onMemberRemoved(userId, group.getId());
        }

        // logout?
    }

    public void onLocationsUpdated(Go go) {
        for (Group group : list) {
            List<Go> old = group.getCurrentGos();

            for (Go currentGo : old) {
                if (go.getId() == currentGo.getId()) {
                    old.remove(currentGo);
                    old.add(go);
                    List<Go> newGos = old;
                    group.setCurrentGos(newGos);
                    break;
                }
            }

            data.setValue(list);
            break;
        }
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
        GroupMembership myMembership = new GroupMembership(me, group1, true, true);
        memberships.add(myMembership);
        for (int i = 0; i < 10; i++) {
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
        clusters.add(new Cluster(49.012307, 8.402427, 3));
        clusters.add(new Cluster(49.012334, 8.405621, 4));
        clusters.add(new Cluster(49.011271, 8.404376, 5));
        go1.setLocations(clusters);
        ArrayList<Go> gos = new ArrayList<>();
        gos.add(go1);
        group1.setCurrentGos(gos);
        group1.setId(1);
        Group group2 = new Group();
        List<GroupMembership> memberships2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
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

    public void setList(ArrayList<Group> list) {
        this.list = list;
    }

    public ArrayList<Group> getList() {
        return list;
    }

    public void setData(GroupListLiveData data) {
        this.data = data;
    }
}
