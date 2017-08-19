package edu.kit.pse17.go_app.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.kit.pse17.go_app.model.Status;
import edu.kit.pse17.go_app.model.entities.Cluster;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.model.entities.GroupMembership;
import edu.kit.pse17.go_app.model.entities.User;
import edu.kit.pse17.go_app.model.entities.UserGoStatus;
import edu.kit.pse17.go_app.view.GroupListActivity;
import edu.kit.pse17.go_app.viewModel.livedata.GroupListLiveData;

import static org.junit.Assert.assertTrue;

/**
 * Created by Сеня on 20.08.2017.
 */

public class GroupRepositoryTest {
    private GroupRepository groupRepo;
    private ArrayList<Group> list;
    private GroupListLiveData data;

    @Before
    public void start() {
        groupRepo = GroupRepository.getInstance();
        list = mockGroupData();
        groupRepo.setList(list);
        data = groupRepo.getData();
        data.setValue(list);
        groupRepo.setData(data);
    }

    @After
    public void end() {
        groupRepo = null;
        list = null;
        data = null;
    }

    @Test
    public void onAdminAddedTest() {
        String userId = "id1";
        long groupId = 1;

        groupRepo.onAdminAdded(userId, groupId);

        ArrayList<Group> newList = groupRepo.getList();
        for (Group group : newList) {
            if (group.getId() == groupId) {
                List<GroupMembership> old = group.getMembershipList();

                for (GroupMembership member : old) {
                    if (member.getUser().getUid().equals(userId)) {
                        assertTrue(member.isAdmin());
                        break;
                    }
                }

                break;
            }
        }
    }


    private ArrayList<Group> mockGroupData() {
        ArrayList<Group> groupList = new ArrayList<>();
        User user1 = new User("id1", "user1@gmail.com", "User1");
        User user2 = new User("id2", "user2@gmail.com", "User2");
        User user3 = new User("id3", "user3@gmail.com", "User3");
        User me = new User("MLsXON00mUPSQvKi7DggE6lTAsq1", "vovaspilka1@gmail.com", "Vova");
        List<GroupMembership> memberships = new ArrayList<>();
        Group group1 = new Group();
        group1.setId(1);
        GroupMembership myMembership = new GroupMembership(me, group1, true, false);
        memberships.add(myMembership);
        GroupMembership mem1 = new GroupMembership(user1, group1, false, false);
        memberships.add(mem1);
        GroupMembership mem2 = new GroupMembership(user2, group1, false, false);
        memberships.add(mem2);
        GroupMembership mem3 = new GroupMembership(user3, group1, false, true);
        memberships.add(mem3);
        group1.setName("Group 1");
        group1.setDescription("DeScRiPtIoN");
        group1.setMembershipList(memberships);
        Go go1 = new Go();
        go1.setId(1);
        List<UserGoStatus> goStatusList = new ArrayList<>();
        goStatusList.add(new UserGoStatus(user1, go1, Status.GOING));
        goStatusList.add(new UserGoStatus(user2, go1, Status.NOT_GOING));
        goStatusList.add(new UserGoStatus(me, go1, Status.GONE));
        go1.setParticipantsList(goStatusList);
        go1.setName("GO1");
        go1.setOwner(me.getUid());
        go1.setDescription("GO DESCRIPTION");
        go1.setStart(new SimpleDateFormat().format(new Date()));
        go1.setEnd(new SimpleDateFormat().format(new Date()));
        List<Cluster> clusters = new ArrayList<>();
        clusters.add(new Cluster(49.012307, 8.402427, 3));
        clusters.add(new Cluster(49.012334, 8.405621, 4));
        clusters.add(new Cluster(49.011271, 8.404376, 5));
        go1.setLocations(clusters);
        ArrayList<Go> gos = new ArrayList<>();
        gos.add(go1);
        group1.setCurrentGos(gos);

        /*Group group2 = new Group();
        List<GroupMembership> memberships2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GroupMembership mem21 = new GroupMembership(user1, group2, true, true);
            memberships2.add(mem21);
        }
        group2.setId(2);
        group2.setName("Group 2");
        group2.setDescription("Description");
        group2.setCurrentGos(new ArrayList<Go>());
        group2.setMembershipList(memberships2);*/
        groupList.add(group1);
        //groupList.add(group2);
        //data.setValue(list);
        //String userId = GroupListActivity.getUserId();
        return groupList;

    }
}
