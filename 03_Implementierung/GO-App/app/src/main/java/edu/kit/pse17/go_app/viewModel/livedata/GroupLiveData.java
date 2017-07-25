package edu.kit.pse17.go_app.viewModel.livedata;

import android.arch.lifecycle.LiveData;

import edu.kit.pse17.go_app.model.entities.Group;

/**
 * Created by Vovas on 24.07.2017.
 */

public class GroupLiveData extends LiveData<Group> {
    @Override
    public void setValue(Group value) {
        super.setValue(value);
    }
}
