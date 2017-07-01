package edu.kit.pse17.go_app.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.kit.pse17.go_app.model.Group;
import edu.kit.pse17.go_app.model.GroupRepository;

/**
 * Created by tina on 01.07.17.
 */

public class GroupViewModel extends ViewModel {

    private long groupId;
    private LiveData<Group> group;
    private GroupRepository groupRepo;

    @Inject
    public GroupViewModel(GroupRepository groupRepo) {
        this.groupRepo = groupRepo;
    }

    public void init(long groupId) {
        this.groupId = groupId;
        this.group = groupRepo.getGroup(groupId);
    }

    public LiveData<Group> getGroup() {
        return this.group;
    }

}
