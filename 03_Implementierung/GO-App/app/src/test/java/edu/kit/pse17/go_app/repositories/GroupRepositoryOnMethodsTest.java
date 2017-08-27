package edu.kit.pse17.go_app.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

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
import edu.kit.pse17.go_app.viewModel.livedata.GroupListLiveData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Сеня on 20.08.2017.
 */

public class GroupRepositoryOnMethodsTest {
    private GroupRepository groupRepo;
    private ArrayList<Group> list;
    private GroupListLiveData data;

    @Before
    public void setUp() {
        groupRepo = GroupRepository.getInstance();
        list = mockGroupData();
        groupRepo.setList(list);

        data = Mockito.spy(new GroupListLiveData());
        Mockito.when(data.getValue()).thenReturn(list);
        groupRepo.setData(data);
    }

    @After
    public void tearDown() {
        list = null;
        data = null;
        groupRepo = null;
    }

    @Test
    public void onAdminAddedTest() {
        String userId = "id1";
        long groupId = 1;

        groupRepo.onAdminAdded(userId, groupId);

        List<GroupMembership> newList = groupRepo.getList().get(0).getMembershipList();
        for (GroupMembership member : newList) {
            if (member.getUser().getUid().equals(userId)) {
                assertTrue(member.isAdmin());
            }
        }
    }

    @Test
    public void onGoAddedTest() {
        Go go = new Go(2, "GO2", "des", null, null, groupRepo.getList().get(0), 0, 0, "id1", "User1", null, null);
        List<UserGoStatus> part = new ArrayList<>();
        UserGoStatus member = new UserGoStatus(new User("id1", "user1@gmail.com", "User1"), go, Status.GOING);
        part.add(member);
        go.setParticipantsList(part);
        long groupId = 1;

        groupRepo.onGoAdded(go, groupId);

        List<Go> newList = groupRepo.getList().get(0).getCurrentGos();
        Go addedGo = newList.get(1);
        assertTrue(addedGo.getId() == 2);
        assertTrue(addedGo.getGroup().getId() == 1);
        assertEquals(addedGo.getDescription(), "des");
        assertEquals(addedGo.getOwner(), "id1");
    }

    @Test
    public void onGoEditedTest() {
        Go go = new Go(1, "GO1.1", "desNew", null, null, null, 100, 0, "id1", "User1", null, null);

        groupRepo.onGoEdited(go);

        List<Go> newList = groupRepo.getList().get(0).getCurrentGos();
        Go addedGo = newList.get(0);
        assertEquals(addedGo.getName(), "GO1.1");
        assertEquals(addedGo.getDescription(), "desNew");
        assertEquals(addedGo.getOwner(), "id1");
        assertTrue(addedGo.getDesLat() == 100);
        assertTrue(addedGo.getGroup().getId() == 1);
    }

    @Test
    public void onGoRemovedTest() {
        long goId = 1;

        groupRepo.onGoRemoved(goId);

        List<Go> newList = groupRepo.getList().get(0).getCurrentGos();
        assertTrue(newList.isEmpty());
    }

    @Test
    public void onGroupEditedTest() {
        Group group = new Group(1, "newGR1", "DDD", 4, null, null, null);

        groupRepo.onGroupEdited(group);

        Group addedGroup = groupRepo.getList().get(0);
        assertTrue(addedGroup.getId() == 1);
        assertEquals(addedGroup.getName(), "newGR1");
        assertEquals(addedGroup.getDescription(), "DDD");
        assertTrue(addedGroup.getCurrentGos().get(0).getId() == 1);
        assertEquals(addedGroup.getMembershipList().get(0).getUser().getName(), "Vova");
    }

    @Test
    public void onGroupRemoved() {
        long groupId = 1;

        groupRepo.onGroupRemoved(groupId);

        assertTrue(groupRepo.getList().isEmpty());
    }

    @Test
    public void onGroupRequestReceivedTest() {
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
        GroupMembership mem2 = new GroupMembership(user2, group1, false, true); // here request!
        memberships.add(mem2);
        GroupMembership mem3 = new GroupMembership(user3, group1, false, true);
        memberships.add(mem3);
        group1.setName("Group 1");
        group1.setDescription("DeScRiPtIoN");
        group1.setMembershipList(memberships);

        groupRepo.onGroupRequestReceived(group1);

        assertTrue(groupRepo.getList().get(0).getMembershipList().get(3).isRequest());
    }

    @Test
    public void onMemberAddedTest() {
        User user = new User("id3", "user3@gmail.com", "User3");
        long groupId = 1;

        groupRepo.onMemberAdded(user, groupId);

        assertEquals(groupRepo.getList().get(0).getMembershipList().get(3).getUser().getUid(), "id3");
        assertFalse(groupRepo.getList().get(0).getMembershipList().get(3).isRequest());
        assertTrue(groupRepo.getList().get(0).getMembershipList().size() == 4);
    }

    @Test
    public void onMemberRemovedTestUsualUser() {
        String userId = "id2";
        long groupId = 1;

        groupRepo.onMemberRemoved(userId, groupId);

        List<GroupMembership> newList = groupRepo.getList().get(0).getMembershipList();
        for (GroupMembership member : newList) {
            assertNotEquals(member.getUser().getUid(), userId);
        }
    }

    /*@Test
    public void onMemberRemovedTestAllUsers() {
        String userId = "id1";
        long groupId = 1;
        groupRepo.onMemberRemoved(userId, groupId);

        userId = "id2";
        groupRepo.onMemberRemoved(userId, groupId);

        userId = "id3";
        groupRepo.onMemberRemoved(userId, groupId);

        userId = "MLsXON00mUPSQvKi7DggE6lTAsq1";
        groupRepo.onMemberRemoved(userId, groupId);

        assertTrue(groupRepo.getList().isEmpty());
    }*/

    @Test
    public void onMemberRemovedTestGoResponsible() {
        String userId = "id1";
        long groupId = 1;

        // GO that will be deleted
        final long goId = 1;

        GoRepository goRepo = groupRepo.getGoRepo();
        goRepo = Mockito.spy(goRepo);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                deleteGoLocal(goId);
                return null;
            }
        }).when(goRepo).deleteGo(Mockito.anyLong());
        groupRepo.setGoRepo(goRepo);

        groupRepo.onMemberRemoved(userId, groupId);
        groupRepo.setGoRepo(GoRepository.getInstance());

        List<Go> newList = groupRepo.getList().get(0).getCurrentGos();
        assertTrue(newList.isEmpty());
    }

    private void deleteGoLocal(long goId) {
        List<Go> newList;
        list = (ArrayList<Group>) groupRepo.getData().getValue();
        outer:
        for (Group group : list) {
            List<Go> oldList = group.getCurrentGos();

            for (Go go : oldList) {
                if (go.getId() == goId) {
                    oldList.remove(go);
                    newList = oldList;
                    group.setCurrentGos(newList);
                    break outer;
                }
            }
        }

        groupRepo.setList(list);
    }

    @Test
    public void onRequestDeniedTest() {
        String userId = "id3";
        long groupId = 1;

        groupRepo.onRequestDenied(userId, groupId);

        List<GroupMembership> members = groupRepo.getList().get(0).getMembershipList();
        for (GroupMembership member : members) {
            assertNotEquals(member.getUser().getUid(), userId);
        }
    }

    @Test
    public void onStatusChangedTest() {
        String userId = "MLsXON00mUPSQvKi7DggE6lTAsq1";
        long goId = 1;
        int status = 0; // NOT_GOING

        groupRepo.onStatusChanged(userId, goId, status);

        assertEquals(groupRepo.getList().get(0).getCurrentGos().get(0).getParticipantsList().get(2).getStatus(), Status.NOT_GOING);
    }

    @Test
    public void onUserDeletedTest() {
        String userId = "id2";

        groupRepo.onUserDeleted(userId);

        List<GroupMembership> newList = groupRepo.getList().get(0).getMembershipList();
        for (GroupMembership member : newList) {
            assertNotEquals(member.getUser().getUid(), userId);
        }
    }

    @Test
    public void onLocationsUpdatedTest() {
        Go go = new Go(1, "GO1.1", "desNew", null, null, null, 100, 0, "id1", "User1", null, null);
        List<Cluster> clusters = new ArrayList<>();
        clusters.add(new Cluster(00.00000, 100.12345, 12));
        go.setLocations(clusters);

        groupRepo.onLocationsUpdated(go.getId(), clusters);

        List<Go> newList = groupRepo.getList().get(0).getCurrentGos();
        Go addedGo = newList.get(0);
        assertEquals(addedGo.getLocations().get(0).getSize(), 12);
        assertTrue(addedGo.getLocations().get(0).getLon() == 100.12345);
        assertTrue(addedGo.getLocations().size() == 1);
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
        group1.setMemberCount(4);
        group1.setName("Group 1");
        group1.setDescription("DeScRiPtIoN");
        group1.setMembershipList(memberships);
        Go go1 = new Go();
        go1.setId(1);
        go1.setGroup(group1);
        List<UserGoStatus> goStatusList = new ArrayList<>();
        goStatusList.add(new UserGoStatus(user1, go1, Status.GOING));
        goStatusList.add(new UserGoStatus(user2, go1, Status.NOT_GOING));
        goStatusList.add(new UserGoStatus(me, go1, Status.GONE));
        go1.setParticipantsList(goStatusList);
        go1.setName("GO1");
        go1.setOwner(user1.getUid());
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
        groupList.add(group1);
        return groupList;
    }
}
