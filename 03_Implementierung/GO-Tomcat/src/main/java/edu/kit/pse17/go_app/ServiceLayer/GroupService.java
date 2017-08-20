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
        for (GroupMembership groupMembership : group.getMembershipList()) {
            makeJsonable(groupMembership);
        }
        for (Go go : group.getCurrentGos()) {
            GoService.makeJsonable(go, false);
        }
    }

    public static void makeJsonable(GroupMembership groupMembership) {
        groupMembership.getGroup().setMembershipList(null);
        groupMembership.getGroup().setMembershipList(null);
    }

    public static void editGroupForJson(GroupEntity group) {

        for (final UserEntity usr : group.getAdmins()) {
            UserService.editUserForJson(usr);
        }

        for (final UserEntity usr : group.getMembers()) {
            UserService.editUserForJson(usr);
        }

        for (final UserEntity usr : group.getRequests()) {
            UserService.editUserForJson(usr);
        }

        for (final GoEntity go : group.getGos()) {
            GoService.editGoForJson(go, false);
        }
    }

    public static Group groupEntityToGroup(GroupEntity groupEntity) {
        Group group = new Group();
        group.setDescription(groupEntity.getDescription());
        group.setIcon(null);
        group.setId(groupEntity.getID());
        group.setMemberCount(groupEntity.getMembers().size());

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

    public GroupDaoImp getGroupDao() {
        return groupDao;
    }

    public void setGroupDao(GroupDaoImp groupDao) {
        this.groupDao = groupDao;
    }

    public Map<EventArg, Observer> getObserverMap() {
        return observerMap;
    }

    public void setObserverMap(Map<EventArg, Observer> observerMap) {
        this.observerMap = observerMap;
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
        groupDao.update(groupEntity);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(group.getId()));
        notify(EventArg.GROUP_EDITED_EVENT, this, entity_ids);
    }

    public void deleteGroup(long groupId) {
        groupDao.delete(groupId);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(groupId));
        notify(EventArg.GROUP_REMOVED_EVENT, this, entity_ids);
    }

    public void acceptRequest(long groupId, String userId) {
        groupDao.addGroupMember(userId, groupId);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(userId);
        entity_ids.add(String.valueOf(groupId));
        notify(EventArg.MEMBER_ADDED_EVENT, this, entity_ids);
    }

    public void removeGroupMember(String userId, long groupid) {
        groupDao.removeGroupMember(userId, groupid);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(userId);
        entity_ids.add(String.valueOf(groupid));
        notify(EventArg.MEMBER_REMOVED_EVENT, this, entity_ids);
    }

    public void addGroupRequest(String userId, Long groupId) {
        groupDao.addGroupRequest(userId, groupId);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(userId);
        entity_ids.add(String.valueOf(groupId));
        notify(EventArg.GROUP_REQUEST_RECEIVED_EVENT, this, entity_ids);
    }

    public void removeGroupRequest(String userId, long groupId) {
        groupDao.removeGroupRequest(userId, groupId);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(userId);
        entity_ids.add(String.valueOf(groupId));
        notify(EventArg.GROUP_REQUEST_DENIED_EVENT, this, entity_ids);
    }

    public void addAdmin(String userId, long groupId) {
        groupDao.addAdmin(userId, groupId);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(userId);
        entity_ids.add(String.valueOf(groupId));
        notify(EventArg.ADMIN_ADDED_EVENT, this, entity_ids);
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
    public void notify(EventArg impCode, IObservable observable, List<String> entity_ids) {
        if (!observerInitialized) {
            registerAll();
        }
        observerMap.get(impCode).update(entity_ids);
    }

    public GroupEntity getGroupById(Long id) {
        return groupDao.get(id);
    }
}
