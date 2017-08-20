package edu.kit.pse17.go_app.ServiceLayer;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.Status;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Go;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.UserGoStatus;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GoDaoImp;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDaoImp;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDaoImp;
import edu.kit.pse17.go_app.ServiceLayer.observer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Diese Klasse handhabt alle Observerfunktion in Bezug auf GoDaos. Für mehr Information siehe Interface IObservable.
 */

@Service
public class GoService implements IObservable {

    @Autowired
    private static GoDaoImp goDao;

    @Autowired
    private GroupDaoImp groupDao;

    @Autowired
    private UserDaoImp userDao;

    private Map<EventArg, Observer> observerMap;

    private boolean observerInitialized;

    public GoService() {
        registerAll();
        observerInitialized = true;
    }

    public GoService(GoDaoImp godao) {
        registerAll();
        this.goDao = godao;
    }

    public static void editGoForJson(GoEntity go, boolean keepGroupInfo) {

        if (keepGroupInfo) {
            GroupService.editGroupForJson(go.getGroup());
        } else {
            go.setGroup(null);
        }

        UserService.editUserForJson(go.getOwner());

        for (final UserEntity usr : go.getNotGoingUsers()) {
            UserService.editUserForJson(usr);
        }
        for (final UserEntity usr : go.getGoingUsers()) {
            UserService.editUserForJson(usr);
        }
        for (final UserEntity usr : go.getGoneUsers()) {
            UserService.editUserForJson(usr);
        }
    }

    //Warning: group is null ! if group-information is required it needs to be added manually -- same for locations
    public static Go goEntityToGo(GoEntity goEntity) {
        Go go = new Go(goEntity.getID(), goEntity.getName(), goEntity.getDescription(), goEntity.getStart(), goEntity.getEnd(), null, goEntity.getLat(), goEntity.getLon(), goEntity.getOwner().getUid(), goEntity.getOwner().getName(), null, null);
        List<UserGoStatus> statuses = new ArrayList<>();
        for (UserEntity usr : goEntity.getGoingUsers()) {
            statuses.add(new UserGoStatus(UserService.userEntityToUser(usr), go, Status.GOING));
        }
        for (UserEntity usr : goEntity.getGoneUsers()) {
            statuses.add(new UserGoStatus(UserService.userEntityToUser(usr), go, Status.GONE));
        }
        for (UserEntity usr : goEntity.getNotGoingUsers()) {
            statuses.add(new UserGoStatus(UserService.userEntityToUser(usr), go, Status.NOT_GOING));
        }
        return go;
    }

    public static void makeJsonable(Go go, boolean keepGroupInfo) {
        if (keepGroupInfo) {
            GroupService.makeJsonable(go.getGroup());
        } else {
            go.setGroup(null);
        }
        for (UserGoStatus userGoStatus : go.getParticipantsList()) {
            makeJsonable(userGoStatus);
        }
    }

    public static void makeJsonable(UserGoStatus userGoStatus) {
        userGoStatus.getGo().setGroup(null);
        userGoStatus.getGo().setParticipantsList(null);
    }

    public void registerAll() {
        register(EventArg.GO_ADDED_EVENT, new GoAddedObserver(this));
        register(EventArg.GO_REMOVED_EVENT, new GoRemovedObserver(this));
        register(EventArg.GO_EDITED_EVENT, new GoEditedObserver(this));
        register(EventArg.STATUS_CHANGED_EVENT, new StatusChangedObserver(this));
        observerInitialized = true;
    }

    public GoDaoImp getGoDao() {
        return goDao;
    }

    public void setGoDao(GoDaoImp goDao) {
        this.goDao = goDao;
    }

    public Map<EventArg, Observer> getObserverMap() {
        return observerMap;
    }

    public void setObserverMap(Map<EventArg, Observer> observerMap) {
        this.observerMap = observerMap;
    }

    public long createGo(Go go) {
        UserEntity owner = userDao.get(go.getOwner());
        GroupEntity groupEntity = groupDao.get(go.getGroup().getId());
        GoEntity goEntity = new GoEntity(groupEntity, owner, go.getName(), go.getDescription(), go.getStart(), go.getEnd(), go.getDesLat(), go.getDesLon());
        long id = goDao.persist(goEntity);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(id));
        notify(EventArg.GO_ADDED_EVENT, this, entity_ids);
        return id;
    }

    public boolean changeStatus(Map<String, String> statusChangeContext, long goId) {
        String userId = statusChangeContext.get("userId");
        Status status;

        switch (statusChangeContext.get("status")) {
            case "NOT_GOING":
                status = Status.NOT_GOING;
                break;
            case "GOING":
                status = Status.GOING;
                break;
            case "GONE":
                status = Status.GONE;
            default:
                return false;
        }
        goDao.changeStatus(userId, goId, status);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(userId);
        entity_ids.add(String.valueOf(goId));
        notify(EventArg.STATUS_CHANGED_EVENT, this, entity_ids);
        return true;
    }

    public void delete(long goId) {
        goDao.delete(goId);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(goId));
        notify(EventArg.GO_REMOVED_EVENT, this, entity_ids);
    }

    public void update(Go go) {
        GoEntity goEntity = new GoEntity(null, null, go.getName(), go.getDescription(), go.getStart(), go.getEnd(), go.getDesLat(), go.getDesLon());
        goDao.update(goEntity);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(goEntity.getID()));
        notify(EventArg.GO_EDITED_EVENT, this, entity_ids);
    }

    public GoEntity getGoById(long id) {
        return goDao.get(id);
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
        if (!this.observerInitialized) {
            registerAll();
        }
        observerMap.get(impCode).update(entity_ids);
    }
}
