package edu.kit.pse17.go_app.ServiceLayer.observer;


import com.google.gson.Gson;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Group;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDao;
import edu.kit.pse17.go_app.ServiceLayer.GroupService;

import java.util.List;

/**
 * Dieser Observer behandelt Fälle wo der Gruppenanfragen erhalten werden.
 */

public class GroupRequestReceivedObserver implements Observer {

    private final FcmClient messenger;
    private GroupService groupService;


    public GroupRequestReceivedObserver(FcmClient messenger, GroupDao groupDao) {
        this.messenger = messenger;
        this.groupService = new GroupService();
    }

    public GroupRequestReceivedObserver(GroupService groupService) {
        this.messenger = new FcmClient();
        this.groupService = groupService;
    }

    public FcmClient getMessenger() {
        return messenger;
    }

    public GroupService getGroupService() {
        return groupService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public void update(List<String> entity_ids, List<String> receiver) {
        GroupEntity group = groupService.getGroupById(Long.valueOf(entity_ids.get(1)));
        Group cGroup = GroupService.groupEntityToGroup(group);
        GroupService.makeJsonable(cGroup);

        String data = new Gson().toJson(cGroup);

        //sende Gruppe zum eingeladenen Benutzer
        messenger.send(data, EventArg.GROUP_REQUEST_RECEIVED_EVENT, receiver);
    }
}
