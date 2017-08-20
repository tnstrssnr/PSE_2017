package edu.kit.pse17.go_app.ServiceLayer.observer;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDaoImp;
import edu.kit.pse17.go_app.ServiceLayer.GroupService;
import edu.kit.pse17.go_app.ServiceLayer.UserService;
import org.json.simple.JSONObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Dieser Observer behandelt wo ein Gruppenmitglied einer Gruppe hinzugefügt wird.
 */

public class MemberAddedObserver implements Observer {

    private final FcmClient messenger;
    private GroupService groupService;

    public MemberAddedObserver(FcmClient messenger, GroupService groupService) {
        this.messenger = messenger;
        this.groupService = groupService;
    }

    public MemberAddedObserver(GroupService groupService) {
        this.messenger = new FcmClient();
        this.groupService = groupService;
    }

    @Override
    public void update(List<String> entity_ids) {
        GroupEntity group = groupService.getGroupById(Long.valueOf(entity_ids.get(1)));
        UserDaoImp userDao = new UserDaoImp(groupService.getGroupDao().getSf());
        UserEntity newUser = userDao.get(entity_ids.get(0));

        JSONObject json = new JSONObject();
        json.put("group_id", group.getID());
        json.put("user", new Gson().toJson(UserService.userEntityToUser(newUser)));
        String data = json.toJSONString();

        Set<UserEntity> receiver = new HashSet<>();
        for (UserEntity usr : group.getMembers()) {
            receiver.add(usr);
        }
        for (UserEntity usr : group.getRequests()) {
            receiver.add(usr);
        }
        messenger.send(data, EventArg.MEMBER_ADDED_EVENT, receiver);
    }
}
