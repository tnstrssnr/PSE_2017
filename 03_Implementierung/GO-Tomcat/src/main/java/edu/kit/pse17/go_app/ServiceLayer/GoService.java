package edu.kit.pse17.go_app.ServiceLayer;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.Status;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GoDaoImp;
import edu.kit.pse17.go_app.ServiceLayer.observer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoService implements IObservable {

    @Autowired
    private GoDaoImp goDao;

    private Map<EventArg, Observer> observerMap;

    public GoService() {
        register(EventArg.GO_ADDED_EVENT, new GoAddedObserver(this));
        register(EventArg.GO_REMOVED_EVENT, new GoRemovedObserver(this));
        register(EventArg.GO_EDITED_COMMAND, new GoEditedObserver(this));
        register(EventArg.STATUS_CHANGED_COMMAND, new StatusChangedObserver(this));
    }

    public GoService(GoDaoImp godao) {
        register(EventArg.GO_ADDED_EVENT, new GoAddedObserver(this));
        register(EventArg.GO_REMOVED_EVENT, new GoRemovedObserver(this));
        register(EventArg.GO_EDITED_COMMAND, new GoEditedObserver(this));
        register(EventArg.STATUS_CHANGED_COMMAND, new StatusChangedObserver(this));
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

    public long createGo(GoEntity go) {
        long id = goDao.persist(go);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(id));
        notify(EventArg.GO_ADDED_EVENT, this, entity_ids);
        return id;
    }

    public boolean changeStatus(String statusChangeContext, long goId) {
        String[] statusChangeArr = statusChangeContext.substring(1, statusChangeContext.length() - 1).split(" ");
        String userId = statusChangeArr[0];
        Status status;

        switch (statusChangeArr[1]) {
            case "ABGELEHNT":
                status = Status.ABGELEHNT;
                break;
            case "BESTÄTIGT":
                status = Status.BESTÄTIGT;
                break;
            case "UNTERWEGS":
                status = Status.UNTERWEGS;
                break;
            default:
                return false;
        }
        goDao.changeStatus(userId, goId, status);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(userId);
        entity_ids.add(String.valueOf(goId));
        notify(EventArg.STATUS_CHANGED_COMMAND, this, entity_ids);
        return true;
    }

    public void delete(long goId) {
        goDao.delete(goId);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(goId));
        notify(EventArg.GO_REMOVED_EVENT, this, entity_ids);
    }

    public void update(GoEntity goEntity) {
        goDao.update(goEntity);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(goEntity.getID()));
        notify(EventArg.GO_EDITED_COMMAND, this, entity_ids);
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
        observerMap.get(impCode).update(entity_ids);
    }
}
