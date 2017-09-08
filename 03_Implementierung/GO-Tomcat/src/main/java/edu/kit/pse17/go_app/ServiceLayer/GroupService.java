package edu.kit.pse17.go_app.ServiceLayer;

/**
 * Diese Klasse handhabt alle Observerfunktion in Bezug auf GroupDaos. Für mehr Information siehe Interface IObservable.
 */

import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Go;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Group;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.GroupMembership;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.User;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDaoImp;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDaoImp;
import edu.kit.pse17.go_app.ServiceLayer.observer.*;
import edu.kit.pse17.go_app.ServiceLayer.observer.Observer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GroupService implements IObservable {

    @Autowired
    private GroupDaoImp groupDao;

    @Autowired
    private UserDaoImp userDao;

    private Map<EventArg, Observer> observerMap;

    private boolean observerInitialized;

    public GroupService() {
        registerAll();
    }

    public GroupService(GroupDaoImp groupDao) {
        registerAll();
        this.groupDao = groupDao;
    }

    public static void makeJsonable(Group group) {
        List<GroupMembership> jsonAbleList = new ArrayList<>();

        if (group.getMembershipList() == null) {
            group.setMembershipList(new ArrayList<>());
        }
        if (group.getCurrentGos() == null) {
            group.setCurrentGos(new ArrayList<>());
        }

        for (GroupMembership groupMembership : group.getMembershipList()) {
            jsonAbleList.add(new GroupMembership(groupMembership.getUser(), new Group(group.getId(), group.getName(), group.getDescription(), 0, null, new ArrayList<>(), new ArrayList<>()), groupMembership.isAdmin(), groupMembership.isRequest()));
        }
        group.setMembershipList(jsonAbleList);

        for (Go go : group.getCurrentGos()) {
            GoService.makeJsonable(go, false);
            go.setGroup(new Group(group.getId(), group.getName(), group.getDescription(), group.getMemberCount(), null, new ArrayList<>(), new ArrayList<>()));
        }
    }

    public static void makeJsonable(GroupMembership groupMembership) {
        groupMembership.getGroup().setMembershipList(new ArrayList<>());
        groupMembership.getGroup().setCurrentGos(new ArrayList<>());
    }

    public static Group groupEntityToGroup(GroupEntity groupEntity) {
        Group group = new Group();
        group.setName(groupEntity.getName());
        group.setDescription(groupEntity.getDescription());
        group.setIcon(null);
        group.setId(groupEntity.getID());

        List<GroupMembership> groupMemberships = new ArrayList<>();
        for (UserEntity member : groupEntity.getMembers()) {
            GroupMembership membership = new GroupMembership();
            membership.setUser(UserService.userEntityToUser(member));
            membership.setGroup(group);
            membership.setRequest(false);
            if (groupEntity.getAdmins().contains(member)) {
                membership.setAdmin(true);
            } else {
                membership.setAdmin(false);
            }
            groupMemberships.add(membership);
        }
        for (UserEntity usr : groupEntity.getRequests()) {
            groupMemberships.add(new GroupMembership(UserService.userEntityToUser(usr), group, false, true));
        }
        group.setMembershipList(groupMemberships);

        List<Go> gos = new ArrayList<>();
        for (GoEntity goEntity : groupEntity.getGos()) {
            Go go = GoService.goEntityToGo(goEntity);
            go.setGroup(group);
            gos.add(go);
        }
        group.setCurrentGos(gos);

        return group;
    }

    public UserDaoImp getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDaoImp userDao) {
        this.userDao = userDao;
    }

    private void registerAll() {
        register(EventArg.GROUP_REQUEST_RECEIVED_EVENT, new GroupRequestReceivedObserver(this));
        register(EventArg.GROUP_EDITED_EVENT, new GroupEditedObserver(this));
        register(EventArg.GROUP_REMOVED_EVENT, new GroupRemovedObserver(this));
        register(EventArg.MEMBER_REMOVED_EVENT, new MemberRemovedObserver(this));
        register(EventArg.GROUP_REQUEST_DENIED_EVENT, new RequestDeniedObserver(this));
        register(EventArg.MEMBER_ADDED_EVENT, new MemberAddedObserver(this));
        register(EventArg.ADMIN_ADDED_EVENT, new AdminAddedObserver(this));
        observerInitialized = true;
    }

    public Map<EventArg, Observer> getObserverMap() {
        return observerMap;
    }

    public long createGroup(Group group) {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setName(group.getName());
        groupEntity.setDescription(group.getDescription());

        UserEntity creator = userDao.get(group.getMembershipList().get(0).getUser().getUid());
        groupEntity.setRequests(new HashSet<>());
        groupEntity.setAdmins(new HashSet<>());
        groupEntity.setMembers(new HashSet<>());
        groupEntity.getAdmins().add(creator);
        groupEntity.getMembers().add(creator);

        long id = groupDao.persist(groupEntity);
        return id;
    }

    public void editGroup(Group group) {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setName(group.getName());
        groupEntity.setDescription(group.getDescription());
        groupEntity.setID(group.getId());
        groupDao.update(groupEntity);

        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(group.getId()));

        List<String> receiver = prepareReceiverList(group.getId());

        notify(EventArg.GROUP_EDITED_EVENT, this, entity_ids, receiver);
    }

    public void deleteGroup(long groupId) {
        List<String> receiver = prepareReceiverList(groupId);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(groupId));

        notify(EventArg.GROUP_REMOVED_EVENT, this, entity_ids, receiver);

        groupDao.delete(groupId);
    }

    public void acceptRequest(long groupId, String userId) {
        List<String> receiver = prepareReceiverList(groupId);

        groupDao.addGroupMember(userId, groupId);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(userId);
        entity_ids.add(String.valueOf(groupId));
        notify(EventArg.MEMBER_ADDED_EVENT, this, entity_ids, receiver);
    }

    public boolean removeGroupMember(String email, long groupId) {
        UserEntity userToRemove = userDao.getUserByEmail(email + ".com");
        List<String> receiver = prepareReceiverList(groupId);

        if (userToRemove != null) { //User gefunden
            String userId = userToRemove.getUid();

            groupDao.removeGroupMember(userId, groupId);

            List<String> entity_ids = new ArrayList<>();
            entity_ids.add(userId);
            entity_ids.add(String.valueOf(groupId));
            entity_ids.add(userToRemove.getInstanceId());
            notify(EventArg.MEMBER_REMOVED_EVENT, this, entity_ids, receiver);
        } else {
            return false;
        }

        return true;
    }

    public boolean addGroupRequest(String email, Long groupId) {
        UserEntity userEntity = userDao.getUserByEmail(email + ".com");
        User user;
        if (userEntity != null) {
            user = UserService.userEntityToUser(userEntity);
        } else {
            return false;
        }

        String userId = user.getUid();
        groupDao.addGroupRequest(userId, groupId);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(groupId));

        List<String> receiver = new ArrayList<>();
        receiver.add(userEntity.getInstanceId());
        notify(EventArg.GROUP_REQUEST_RECEIVED_EVENT, this, entity_ids, receiver);
        return true;
    }

    public void removeGroupRequest(String userId, long groupId) {
        groupDao.removeGroupRequest(userId, groupId);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(userId);
        entity_ids.add(String.valueOf(groupId));
        List<String> receiver = prepareReceiverList(groupId);
        //notify(EventArg.GROUP_REQUEST_DENIED_EVENT, this, entity_ids, receiver);
    }

    public void addAdmin(String userId, long groupId) {
        groupDao.addAdmin(userId, groupId);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(userId);
        entity_ids.add(String.valueOf(groupId));
        List<String> receiver = prepareReceiverList(groupId);
        notify(EventArg.ADMIN_ADDED_EVENT, this, entity_ids, receiver);
    }

    @Override
    public void register(EventArg arg, Observer observer) {
        if (observerMap == null) {
            this.observerMap = new HashMap<>();
        }

        observerMap.put(arg, observer);
    }

    @Override
    public void unregister(EventArg arg) {
        observerMap.remove(arg);
    }

    @Override
    public void notify(EventArg impCode, IObservable observable, List<String> entity_ids, List<String> receiver) {
        if (!observerInitialized) {
            registerAll();
        }
        observerMap.get(impCode).update(entity_ids, receiver);
    }

    public GroupEntity getGroupById(Long id) {
        return groupDao.get(id);
    }

    public List<String> prepareReceiverList(long groupId) {
        GroupEntity group = groupDao.get(groupId);
        List<String> receiver = new ArrayList<>();

        for (UserEntity usr : group.getMembers()) {
            receiver.add(usr.getInstanceId());
        }
        for (UserEntity usr : group.getRequests()) {
            receiver.add(usr.getInstanceId());
        }
        return receiver;
    }
}
