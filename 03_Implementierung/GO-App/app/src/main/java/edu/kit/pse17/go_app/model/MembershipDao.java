package edu.kit.pse17.go_app.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import java.util.List;

/**
 * Created by tina on 02.07.17.
 */

@Dao
public interface MembershipDao {

    @Insert
    public void save(GroupMembership memberShip);

    public List<User> getMembership(Group group);

    public List<Group> getMembership(User user);

    public List<User> getAdmins(Group group);

    public List<User> getRequests(Group group);

    public void updateMembership(GroupMembership membership);

    public void deleteMembership(GroupMembership membership);

}
