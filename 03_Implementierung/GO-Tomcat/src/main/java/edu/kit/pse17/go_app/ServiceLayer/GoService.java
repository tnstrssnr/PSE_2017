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
    private GoDaoImp goDao;

    @Autowired
    private GroupDaoImp groupDao;

    @Autowired
    private UserDaoImp userDao;

    @Autowired
    private GroupService groupService;

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

    /**
     * Wandelt eine GoEntity in ein entsprechendes GO-Objekt um. Achtung: Diese Methode setzt Gruppe auf null! --> muss
     * ggfs. danach wieder hinzugefügt werden --> kann zu Stackoverflow beim Parsen führen
     *
     * @param goEntity Das Go, das umgewandelt werden soll
     * @return das erzeugt Go-Objekt
     */
    public static Go goEntityToGo(GoEntity goEntity) {
        Go go = new Go(goEntity.getID(), goEntity.getName(), goEntity.getDescription(), goEntity.getStart(), goEntity.getEnd(), null, goEntity.getLat(), goEntity.getLon(), goEntity.getOwner().getUid(), goEntity.getOwner().getName(), new ArrayList<>(), new ArrayList<>());
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
        go.setParticipantsList(statuses);
        return go;
    }

    /**
     * Bearbeitet das Go so, das beim Parsen kein StackOverflow Error ausgegeben wird.
     *
     * @param go            das Go zum Parsen
     * @param keepGroupInfo -- sollen die Informationen über die Gruppe des Gos erhalten bleiben?
     */
    public static void makeJsonable(Go go, boolean keepGroupInfo) {
        if (keepGroupInfo && go.getGroup() != null) {
            GroupService.makeJsonable(go.getGroup());
        } else {
            go.setGroup(null);
        }

        if (go.getParticipantsList() != null) {
            ArrayList<UserGoStatus> jsonableList = new ArrayList<>();
            for (UserGoStatus userGoStatus : go.getParticipantsList()) {
                jsonableList.add(new UserGoStatus(userGoStatus.getUser(), null, userGoStatus.getStatus()));
                //GoService.makeJsonable(userGoStatus);
            }
            go.setParticipantsList(jsonableList);
        } else {
            go.setParticipantsList(new ArrayList<>());
        }
    }

    public static void makeJsonable(UserGoStatus userGoStatus) {
        userGoStatus.getGo().setGroup(null);
        userGoStatus.getGo().setParticipantsList(new ArrayList<>());
        userGoStatus.getGo().setLocations(null);
    }

    public void setGroupDao(GroupDaoImp groupDao) {
        this.groupDao = groupDao;
    }


    public void setUserDao(UserDaoImp userDao) {
        this.userDao = userDao;
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

    public Map<EventArg, Observer> getObserverMap() {
        return observerMap;
    }

    public long createGo(Go go) {
        UserEntity owner = userDao.get(go.getOwner());
        GroupEntity groupEntity = groupDao.get(go.getGroup().getId());
        GoEntity goEntity = new GoEntity(groupEntity, owner, go.getName(), go.getDescription(), go.getStart(), go.getEnd(), go.getDesLat(), go.getDesLon());
        long id = goDao.persist(goEntity);

        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(id));
        List<String> receiver = groupService.prepareReceiverList(groupEntity.getID());

        notify(EventArg.GO_ADDED_EVENT, this, entity_ids, receiver);
        return id;
    }

    public boolean changeStatus(Map<String, String> statusChangeContext, long goId) {
        String userId = statusChangeContext.get("userId");
        Status status;

        for (UserEntity usr : getGoById(goId).getGoneUsers()){
            if (usr.getUid().equals(userId));
            LocationService.removeUser( userId, goId);
        }

        switch (statusChangeContext.get("status")) {
            case "NOT_GOING":
                status = Status.NOT_GOING;
                break;
            case "GOING":
                status = Status.GOING;
                break;
            case "GONE":
                status = Status.GONE;
                break;
            default:
                return false;
        }


        goDao.changeStatus(userId, goId, status);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(userId);
        entity_ids.add(String.valueOf(goId));
        List<String> receiver = groupService.prepareReceiverList(goDao.get(goId).getGroup().getID());
        notify(EventArg.STATUS_CHANGED_EVENT, this, entity_ids, receiver);
        return true;
    }

    public void delete(long goId) {
        LocationService.removeGo(goId);
        long groupId = goDao.get(goId).getGroup().getID();
        goDao.delete(goId);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(goId));
        entity_ids.add(String.valueOf(groupId));
        List<String> receiver = groupService.prepareReceiverList(groupId);
        notify(EventArg.GO_REMOVED_EVENT, this, entity_ids, receiver);
    }

    public void update(Go go) {
        GoEntity goEntity = new GoEntity(null, null, go.getName(), go.getDescription(), go.getStart(), go.getEnd(), go.getDesLat(), go.getDesLon());


        goEntity.setID(go.getId());
        goDao.update(goEntity);
        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(String.valueOf(go.getId()));

        GroupEntity affectedGroup = goDao.get(go.getId()).getGroup();
        List<String> receiver = groupService.prepareReceiverList(affectedGroup.getID());

        notify(EventArg.GO_EDITED_EVENT, this, entity_ids, receiver);
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
    public void notify(EventArg impCode, IObservable observable, List<String> entity_ids, List<String> receiver) {
        if (!this.observerInitialized) {
            registerAll();
        }
        observerMap.get(impCode).update(entity_ids, receiver);
    }
}
