package edu.kit.pse17.go_app.serverCommunication.upstream;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.util.ArrayList;
import java.util.List;

import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.model.entities.GroupMembership;
import edu.kit.pse17.go_app.model.entities.UserGoStatus;

/**
 * Created by Vovas on 19.08.2017.
 */

public class Serializer {

    private static ExclusionStrategy groupStrategy;

    public static ExclusionStrategy getGroupExclusionStrategy(){
        groupStrategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {

                return f.getName().contains("membership") || f.getName().contains("current") || f.getName().equals("icon");
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };
        return groupStrategy;
    }

    public static void makeJsonable(Group group) {
        List<GroupMembership> jsonAbleList = new ArrayList<>();
        for (GroupMembership groupMembership : group.getMembershipList()) {
            jsonAbleList.add(new GroupMembership(groupMembership.getUser(), null, groupMembership.isAdmin(), groupMembership.isRequest()));
            makeJsonable(groupMembership);
        }
        group.setMembershipList(jsonAbleList);

        for (Go go : group.getCurrentGos()) {
            makeJsonable(go, false);
        }
    }

    public static void makeJsonable(GroupMembership groupMembership) {
        groupMembership.getGroup().setMembershipList(new ArrayList<GroupMembership>());
        groupMembership.getGroup().getCurrentGos().clear();
    }
    public static void makeJsonable(Go go, boolean keepGroupInfo) {
        if (keepGroupInfo) {
            makeJsonable(go.getGroup());
        } else {
            go.setGroup(null);
        }

        if(go.getParticipantsList() != null) {
            for (UserGoStatus userGoStatus : go.getParticipantsList()) {
                makeJsonable(userGoStatus);
            }
        }
    }

    public static void makeJsonable(UserGoStatus userGoStatus) {
        userGoStatus.getGo().setGroup(null);
        userGoStatus.getGo().setParticipantsList(null);
    }
}
