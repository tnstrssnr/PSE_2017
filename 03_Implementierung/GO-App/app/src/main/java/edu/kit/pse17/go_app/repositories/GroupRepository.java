package edu.kit.pse17.go_app.repositories;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
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
 * The Group Repository is responsible for all operations of the Group Data
 * (inclusive data of some GOs) and is the simple interface for fetching,
 * changing and deletion of data.
 * In the case of a request the Repository can fetch the data from server.
 * <p>
 * The Repository operate as a mediator between the local database and data
 * that the app from server obtain.
 * This Repository is also a singleton.
 */
@Singleton
public class GroupRepository extends Repository<List<Group>> {

    /**
     * Private attribute for GroupRepository (see Singleton).
     */
    private static GroupRepository groupRepo;

    /**
     * Attribute to handle server requests about the specific GO.
     */
    private GoRepository goRepo;

    /**
     * The Reference to the REST-API, which TomcatServer provides, so that
     * communication with the server is possible.
     */
    private final TomcatRestApi apiService;

    /**
     * Local database of groups (not consistent).
     */
    private List<Group> list;

    /**
     * LiveData for groups (consistent).
     */
    private GroupListLiveData data;

    /**
     * HTTP status code of the response of the server (by the requests).
     * It is used for testing.
     */
    private int responseStatus;

    /**
     * Flag that shows if the message from server came or not.
     * It is used for testing.
     */
    private boolean messageFlag;

    /**
     * GO with ID assigned from server.
     */
    private Go goWithIdAssigned;

    /**
     * Constructor for Group Repository.
     */
    private GroupRepository() {
        goRepo = GoRepository.getInstance();
        this.apiService = TomcatRestApiClient.getClient().create(TomcatRestApi.class);
        if (this.data == null) {
            this.data = new GroupListLiveData();
        }
    }

    /**
     * Method that fetches all the data from server (list of groups) and sets
     * it to the local database.
     *
     * @param userId:     ID of the user
     * @param email:      E-Mail of the user
     * @param instanceId: Instance ID of the device
     * @param userName:   Name of the user
     */
    public void getData(final String userId, final String email, String instanceId, String userName) {
        Call<List<Group>> call = apiService.getData(userId, email, userName);
        //we update our device instance id, every time we call data, so it would always be consistent
        registerDevice(userId,FirebaseInstanceId.getInstance().getToken());
        call.enqueue(new Callback<List<Group>>() {

            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                List<Group> groups = response.body();

                if (groups.get(0).getId() == -1) {
                    data.postValue(new ArrayList<Group>());
                } else {
                    data.postValue(response.body());
                }

                responseStatus = response.code();
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                Log.e("get_data", t.toString());
            }
        });
    }

    /**
     * Method that registers the new device on the server.
     * The device ID will be matched to the user ID on the server.
     *
     * @param userId: ID of the user
     * @param instanceId: ID of the device.
     */
    public void registerDevice(String userId, String instanceId) {
        Call<Void> call = apiService.registerDevice(userId, instanceId);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                responseStatus = response.code();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("register_device", t.toString());
            }
        });
    }

    /**
     * Method that creates group. Group ID is assigned then internally on the
     * server.
     *
     * @param name:        Name of the group
     * @param description: Description of the group
     * @param user:        User entity (admin)
     */
    public void createGroup(String name, String description, User user) {
        Group newGroup = new Group(0, name, description, 1, null, new ArrayList<GroupMembership>(), new ArrayList<Go>());
        newGroup.getMembershipList().add(new GroupMembership(user, newGroup, true, false));

        /* add locally */
        List<Group> newData = data.getValue();
        if (newData == null) {
            newData = new ArrayList<>();
        }
        Group group = new Group();
        group.setName(name);
        group.setDescription(description);
        group.setId(-123);
        List<GroupMembership> membership = new ArrayList<>();
        membership.add(new GroupMembership(user, group, true, false));
        group.setMembershipList(membership);
        newData.add(group);
        list = newData;
        data.postValue(list);

        /* To send the group to the server without stack overflow */
        Serializer.makeJsonable(newGroup);

        Call<Long> call = apiService.createGroup(newGroup);
        call.enqueue(new Callback<Long>() {

            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                long code = response.body();
                onGroupIdAssigned(code);

                responseStatus = response.code();
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("create_group", t.toString());
            }
        });
    }

    /**
     * This method assigns the group ID, which comes from server,
     * to the new group internally in the app.
     *
     * @param code: Group ID that comes from server
     */
    private void onGroupIdAssigned(long code) {
        List<Group> oldGroups = data.getValue();
        for (Group group : oldGroups) {
            if (group.getId() == -123) {
                group.setId(code);
                break;
            }
        }
        list = oldGroups;
        data.postValue(list);
    }

    /**
     * Method that changes the data of the group with the group ID.
     * The ID here is correct cause this is already proved in ViewModels.
     *
     * @param groupId:     ID of the group
     * @param name:        Name of the group
     * @param description: Description of the group
     */
    public void editGroup(long groupId, String name, String description) {
        Group alteredGroup = new Group(groupId, name, description, 0, null, null, null);

        Call<Void> call = apiService.editGroup(alteredGroup);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                responseStatus = response.code();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("edit_group", t.toString());
            }
        });
    }

    /**
     * Method that deletes the group with the group ID.
     *
     * @param groupId: ID of the group
     */
    public void deleteGroup(long groupId) {
        Call<Void> call = apiService.deleteGroup(groupId);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                responseStatus = response.code();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("delete_group", t.toString());
            }
        });
    }

    /**
     * The answer on the group request.
     *
     * @param groupId: ID of the group
     * @param userId:  ID of the user
     * @param answer:  True - add to the group;
     *                 False - don't add to the group
     */
    public void answerGroupRequest(long groupId, String userId, boolean answer) {
        if (answer) {
            acceptRequest(groupId, userId);
        } else {
            denyRequest(groupId, userId);
        }
    }

    /**
     * Method that is used in case of accepting of the group request.
     *
     * @param groupId: ID of the group
     * @param userId:  ID of the user
     */
    private void acceptRequest(long groupId, String userId) {
        Call<Void> call = apiService.acceptRequest(groupId, userId);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                responseStatus = response.code();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("accept_request", t.toString());
            }
        });
    }

    /**
     * Method that is used in case of denying of the group request.
     *
     * @param groupId: ID of the group
     * @param userId:  ID of the user
     */
    private void denyRequest(final long groupId, final String userId) {
        Call<Void> call = apiService.denyRequest(groupId, userId);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                onGroupRemoved(groupId);

                responseStatus = response.code();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("deny_request", t.toString());
            }
        });
    }

    /**
     * Method that removes member from the group.
     *
     * @param groupId: ID of the group
     * @param email:   E-Mail address of the user
     */
    public void removeMember(long groupId, String email) {
        Call<Void> call = apiService.removeMember(groupId, email);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                responseStatus = response.code();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("remove_member", t.toString());
            }
        });
    }

    /**
     * Method that is used in case of sending of the group request.
     *
     * @param groupId: ID of the group
     * @param email:   E-Mail address of the user
     */
    public void inviteMember(long groupId, String email) {
        Call<Void> call = apiService.inviteMember(groupId, email);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                responseStatus = response.code();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("invite_member", t.toString());
            }
        });
    }

    /**
     * Method that is used in case of adding of a new administrator.
     *
     * @param groupId: ID of the group
     * @param userId:  ID of the user
     */
    public void addAdmin(long groupId, String userId) {
        Call<Void> call = apiService.addAdmin(groupId, userId);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                responseStatus = response.code();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("add_admin", t.toString());
            }
        });
    }

    /**
     * Method that creates a new GO.
     *
     * @param name:        Name of the GO
     * @param description: Description of the GO
     * @param start:       Start time
     * @param end:         End time
     * @param lat:         Desired latitude
     * @param lon:         Desired longitude
     * @param group:       Group
     * @param userId:      ID of the user
     * @param userName:    Name of the user
     */
    public void createGo(String name, String description, String start, String end,
                         double lat, double lon, int threshold, Group group, String userId, String userName) {
        Go go = new Go(-321, name, description, start, end, group, lat, lon, userId, userName, new ArrayList<UserGoStatus>(), new ArrayList<Cluster>());
        goWithIdAssigned = go;
        Call<Long> call = apiService.createGo(go, group.getId(), userId);
        call.enqueue(new Callback<Long>() {

            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                goWithIdAssigned.setId(response.body());

                responseStatus = response.code();
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("create_go", t.toString());
            }
        });
    }


    /**
     * Method that is invoked when the AdminAddedCommand is called (on receiving
     * of the server message).
     * This method puts the new admin to the local database.
     *
     * @param userId:  ID of the new admin
     * @param groupId: ID of the group
     */
    public void onAdminAdded(String userId, long groupId) {
        messageFlag = true;
        list = data.getValue();
        outer:
        for (Group group : list) {
            if (group.getId() == groupId) {
                List<GroupMembership> oldList = group.getMembershipList();

                for (GroupMembership member : oldList) {
                    if (member.getUser().getUid().equals(userId)) {
                        member.setAdmin(true);
                        break outer;
                    }
                }

                List<GroupMembership> newList = oldList;
                group.setMembershipList(newList);
            }
        }

        data.postValue(list);
    }

    /**
     * Method that is invoked when the GoAddedCommand is called (on receiving
     * of the server message).
     * This method puts the new GO to the local database.
     *
     * @param go:      New GO
     * @param groupId: ID of the group of the GO
     */
    public void onGoAdded(Go go, long groupId) {
        messageFlag = true;
        list = data.getValue();
        for (Group group : list) {
            if (group.getId() == groupId) {
                Go newGo = go;
                newGo.setGroup(group);

                List<GroupMembership> membership = group.getMembershipList();
                List<UserGoStatus> goParticipants = new ArrayList<>();
                for (GroupMembership member : membership) {
                    if (!member.isRequest()) {
                        Status status = Status.NOT_GOING;
                        if (member.getUser().getUid().equals(go.getOwner())) {
                            status = Status.GOING;
                        }
                        UserGoStatus participant = new UserGoStatus(member.getUser(), newGo, status);
                        goParticipants.add(participant);
                    }
                }
                newGo.setParticipantsList(goParticipants);

                List<Go> old = group.getCurrentGos();
                old.add(newGo);
                List<Go> newGos = old;
                group.setCurrentGos(newGos);
                data.postValue(list);
                break;
            }
        }
    }

    /**
     * Method that is invoked when the GoEditedCommand is called (on receiving
     * of the server message).
     * This method puts the new data to the GO in the local database.
     *
     * @param go: GO with the new data
     */
    public void onGoEdited(Go go) {
        messageFlag = true;
        list = data.getValue();
        outer:
        for (Group group : list) {
            List<Go> old = group.getCurrentGos();

            for (Go currentGo : old) {
                if (go.getId() == currentGo.getId()) {
                    Go newGo = go;
                    newGo.setGroup(currentGo.getGroup());
                    newGo.setOwner(currentGo.getOwner());
                    newGo.setParticipantsList(currentGo.getParticipantsList());
                    newGo.setLocations(currentGo.getLocations());

                    int index = old.indexOf(currentGo);
                    old.set(index, newGo);
                    List<Go> newGos = old;
                    group.setCurrentGos(newGos);
                    break outer;
                }
            }

        }
        data.postValue(list);
    }

    /**
     * Method that is invoked when the GoRemovedCommand is called (on receiving
     * of the server message).
     * This method deletes this GO from the local database.
     *
     * @param goId: ID of the GO
     */
    public void onGoRemoved(long goId) {
        messageFlag = true;
        list = data.getValue();
        outer:
        for (Group group : list) {
            List<Go> old = group.getCurrentGos();

            for (Go go : old) {
                if (go.getId() == goId) {
                    old.remove(go);
                    List<Go> newGos = old;
                    group.setCurrentGos(newGos);
                    break outer;
                }
            }

        }
        data.postValue(list);
    }

    /**
     * Method that is invoked when the GroupEditedCommand is called (on receiving
     * of the server message).
     * This method puts the new data to the group in the local database.
     *
     * @param group: New data of the group
     */
    public void onGroupEdited(Group group) {
        messageFlag = true;
        list = data.getValue();
        for (Group currentGroup : list) {
            if (group.getId() == currentGroup.getId()) {
                Group newGroup = group;
                newGroup.setCurrentGos(currentGroup.getCurrentGos());
                newGroup.setMembershipList(currentGroup.getMembershipList());

                int index = list.indexOf(currentGroup);
                list.set(index, newGroup);
                data.postValue(list);
                break;
            }
        }
    }

    /**
     * Method that is invoked when the GroupRemovedCommand is called (on receiving
     * of the server message).
     * This method deletes this group from the local database.
     *
     * @param groupId: ID of the group
     */
    public void onGroupRemoved(long groupId) {
        messageFlag = true;
        list = data.getValue();
        for (Group group : list) {
            if (group.getId() == groupId) {
                list.remove(group);
                data.postValue(list);
                break;
            }
        }
    }

    /**
     * Method that is invoked when the GroupRequestReceivedCommand is called
     * (on receiving of the server message).
     * This method puts the new request to the local database.
     *
     * @param group: Group with the new request
     */
    public void onGroupRequestReceived(Group group) {
        messageFlag = true;
        list = data.getValue();
        list.add(group);
        data.postValue(list);
    }

    /**
     * Method that is invoked when the MemberAddedCommand is called (on receiving
     * of the server message).
     * This method puts the new member of the group to the local database.
     *
     * @param user:    Added user
     * @param groupId: ID of the group
     */
    public void onMemberAdded(User user, long groupId) {
        messageFlag = true;
        list = data.getValue();
        for (Group group : list) {
            if (group.getId() == groupId) {
                List<GroupMembership> old = group.getMembershipList();

                for (GroupMembership member : old) {
                    if (member.getUser().getUid().equals(user.getUid())) {
                        old.remove(member);
                        List<GroupMembership> newList = old;
                        group.setMembershipList(newList);
                        break;
                    }
                }

                GroupMembership membership = new GroupMembership(user, group, false, false);
                List<GroupMembership> newList = group.getMembershipList();
                newList.add(membership);
                group.setMembershipList(newList);

                List<Go> goList = group.getCurrentGos();
                for (Go go : goList) {
                    List<UserGoStatus> participantsList = go.getParticipantsList();
                    UserGoStatus participant = new UserGoStatus(user, go, Status.NOT_GOING);
                    participantsList.add(participant);
                    go.setParticipantsList(participantsList);
                }
                group.setCurrentGos(goList);

                data.postValue(list);
                break;
            }
        }
    }

    /**
     * Method that is invoked when the MemberRemovedCommand is called (on receiving
     * of the server message).
     * This method deletes the member from the group in the local database.
     * For more information see MemberRemovedCommand.
     *
     * @param userId:  ID of the user
     * @param groupId: ID of the group
     */
    public void onMemberRemoved(String userId, long groupId) {
        messageFlag = true;
        list = data.getValue();

        /* For the member that will be removed: delete the whole group,
        not the membership */
        if (GroupListActivity.getUserId().equals(userId)) {
            onGroupRemoved(groupId);
            return;
        }

        ArrayList<Go> gosToBeDeleted = new ArrayList<>();
        ArrayList<Group> groupsToBeDeleted = new ArrayList<>();

        for (Group group : list) {
            if (group.getId() == groupId) {
                List<GroupMembership> oldMembershipList = group.getMembershipList();

                for (GroupMembership membership : oldMembershipList) {
                    if (membership.getUser().getUid().equals(userId)) {
                        oldMembershipList.remove(membership);
                        List<GroupMembership> newMembershipList = oldMembershipList;
                        group.setMembershipList(newMembershipList);
                        break;
                    }
                }
                if (group.getMembershipList().size() == 0) {
                    groupsToBeDeleted.add(group);
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
                        gosToBeDeleted.add(go);
                    }
                }

                data.postValue(list);
            }
        }

        for (Go go : gosToBeDeleted) {
            goRepo.deleteGo(go.getId());
        }

        for (Group group : groupsToBeDeleted) {
            deleteGroup(group.getId());
        }
    }

    /**
     * Method that is invoked when the RequestDeniedCommand is called (on receiving
     * of the server message).
     * This method deletes the request from the group in the local database.
     *
     * @param userId:  ID of the user
     * @param groupId: ID of the group
     */
    public void onRequestDenied(String userId, long groupId) {
        messageFlag = true;
        list = data.getValue();
        outer:
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
                        break outer;
                    }
                }

            }
        }
        data.postValue(list);
    }

    /**
     * Method that is invoked when the StatusChangedCommand is called (on receiving
     * of the server message).
     * This method changes the status of the member in the GO in the local database.
     *
     * @param userId: ID of the user
     * @param goId:   ID of the GO
     * @param status: New status: 0 - NOT_GOING;
     *                1 - GOING;
     *                2 - GONE
     */
    public void onStatusChanged(String userId, long goId, int status) {
        messageFlag = true;
        list = data.getValue();
        outer:
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
                    break outer;
                }
            }
        }
        data.postValue(list);
    }

    /**
     * Method that is invoked when the UserDeletedCommand is called (on receiving
     * of the server message).
     * This method deletes all information that linked with the user.
     * For more information see UserDeletedCommand.
     *
     * @param userId
     */
    public void onUserDeleted(String userId) {
        messageFlag = true;
        updateData();
    }

    /**
     * Method that is invoked when the locations of the GO are updated (from
     * the GO Repository).
     * This method updates locations of the users of the GO in local database.
     *
     * @param goId: ID of GO
     * @param locations: New list of clusters
     */
    public void onLocationsUpdated(long goId, List<Cluster> locations) {
        list = data.getValue();
        outer:
        for (Group group : list) {
            List<Go> old = group.getCurrentGos();

            for (Go currentGo : old) {
                if (goId == currentGo.getId()) {
                    currentGo.setLocations(locations);
                    List<Go> newGos = old;
                    group.setCurrentGos(newGos);
                    break outer;
                }
            }
        }
        data.postValue(list);
    }

    /**
     * Method that gets all the data from server if there were too many
     * messages from server so that they were deleted (see MessageReceiver).
     */
    public void updateData() {
        getData(GroupListActivity.getUserId(), GroupListActivity.getGlobalEmail(), FirebaseInstanceId.getInstance().getToken(), GroupListActivity.getDisplayName());
    }

    /**
     * Getter for GroupListLiveData.
     *
     * @return LiveData of the groups
     */
    public GroupListLiveData getData() {
        return data;
    }

    /**
     * GetInstance method for GroupRepository Singleton.
     *
     * @return GroupRepository Singleton object
     */
    public static GroupRepository getInstance() {
        if (groupRepo == null) {
            groupRepo = new GroupRepository();
        }
        return groupRepo;
    }

    /**
     * Setter for list of groups.
     * It is used only for testing. DO NOT use it in the main program!
     *
     * @param list: List of groups
     */
    @Deprecated
    public void setList(List<Group> list) {
        this.list = list;
    }

    /**
     * Getter for list of groups.
     * It is used only for testing. DO NOT use it in the main program!
     *
     * @return List of groups
     */
    @Deprecated
    public List<Group> getList() {
        return list;
    }

    /**
     * Setter for LiveData of the groups.
     * It is used only for testing. DO NOT use it in the main program!
     *
     * @param data: GroupListLiveData
     */
    @Deprecated
    public void setData(GroupListLiveData data) {
        this.data = data;
    }

    /**
     * Getter for the GO Repository.
     * It is used only for testing. DO NOT use it in the main program!
     *
     * @return Current GO Repository
     */
    @Deprecated
    public GoRepository getGoRepo() {
        return goRepo;
    }

    /**
     * Setter for the GO Repository.
     * It is used only for testing (GO Repository is set with the mocked
     * object). DO NOT use it in the main program!
     *
     * @param goRepo: The mocked object of the GO Repository class
     */
    @Deprecated
    public void setGoRepo(GoRepository goRepo) {
        this.goRepo = goRepo;
    }

    /**
     * Getter for the HTTP status code of the response of the server.
     * It is used only for testing.
     *
     * @return The HTTP status code
     */
    @Deprecated
    public int getResponseStatus() {
        return responseStatus;
    }

    /**
     * Setter for the HTTP status code of the response of the server.
     * It is used only for testing (to reset the status code).
     *
     * @param responseStatus: Status of the response (0)
     */
    @Deprecated
    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    /**
     * Getter for the flag of the message from server.
     * It is used only for testing.
     *
     * @return Flag
     */
    @Deprecated
    public boolean isMessageFlag() {
        return messageFlag;
    }

    /**
     * Getter for the flag of the message from server.
     * It is used only for testing (to reset the flag).
     *
     * @param messageFlag: False
     */
    @Deprecated
    public void setMessageFlag(boolean messageFlag) {
        this.messageFlag = messageFlag;
    }

    /**
     * Getter for the GO with ID assigned from server.
     * It is used only for testing (to get the ID of the new GO for the tests).
     *
     * @return: GO with ID assigned from server
     */
    @Deprecated
    public Go getGoWithIdAssigned() {
        return goWithIdAssigned;
    }
}
