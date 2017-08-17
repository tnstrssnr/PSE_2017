package edu.kit.pse17.go_app.ServiceLayer;


import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDaoImp;
import edu.kit.pse17.go_app.ServiceLayer.observer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupService implements IObservable {

    @Autowired
    private GroupDaoImp groupDao;

    private Map<EventArg, Observer> observerMap;

    public GroupService() {
        register(EventArg.GROUP_REQUEST_RECEIVED_EVENT, new GroupRequestReceivedObserver(this));
        register(EventArg.GROUP_EDITED_COMMAND, new GroupEditedObserver(this));
        register(EventArg.GROUP_REMOVED_EVENT, new GroupRemovedObserver(this));
        register(EventArg.MEMBER_REMOVED_EVENT, new MemberRemovedObserver(this));
        register(EventArg.GROUP_REQUEST_DENIED_EVENT, new RequestDeniedObserver(this));
        register(EventArg.MEMBER_ADDED_EVENT, new MemberAddedObserver(this));
        register(EventArg.ADMIN_ADDED_EVENT, new AdminAddedObserver(this));
    }

    public GroupService(GroupDaoImp groupDao) {
        register(EventArg.GROUP_REQUEST_RECEIVED_EVENT, new GroupRequestReceivedObserver(this));
        register(EventArg.GROUP_EDITED_COMMAND, new GroupEditedObserver(this));
        register(EventArg.GROUP_REMOVED_EVENT, new GroupRemovedObserver(this));
        register(EventArg.MEMBER_REMOVED_EVENT, new MemberRemovedObserver(this));
        register(EventArg.GROUP_REQUEST_DENIED_EVENT, new RequestDeniedObserver(this));
        register(EventArg.MEMBER_ADDED_EVENT, new MemberAddedObserver(this));
        register(EventArg.ADMIN_ADDED_EVENT, new AdminAddedObserver(this));
        this.groupDao = groupDao;
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

    public long createGroup(GroupEntity group) {
        long id = groupDao.persist(group);
        return id;
    }

    public void editGroup(GroupEntity group) {
        groupDao.update(group);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(group.getID()));
        notify(EventArg.GROUP_EDITED_COMMAND, this, entity_ids);
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
        observerMap.get(impCode).update(entity_ids);
    }

    public GroupEntity getGroupById(Long id) {
        return groupDao.get(id);
    }
}
