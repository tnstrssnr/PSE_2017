package edu.kit.pse17.go_app.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import edu.kit.pse17.go_app.model.Group;
import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * Created by tina on 02.07.17.
 */

public class GroupListViewModel extends ViewModel {

    private String uid;
    private LiveData<List<Group>> data;
    private GroupRepository groupRepo;

    @Inject
    public GroupListViewModel(GroupRepository groupRepo) {
        this.groupRepo = groupRepo;
    }

    public void init(String uid) {
        this.uid = uid;
        this.data = groupRepo.getGroupsForUser(uid);
    }

    public LiveData<List<Group>> getData() {
        return data;
    }

}
