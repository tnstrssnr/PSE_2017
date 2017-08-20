package edu.kit.pse17.go_app.viewModel.livedata;

import android.arch.lifecycle.LiveData;

import edu.kit.pse17.go_app.model.entities.Group;

/**
 * Needed to override the setValue and make it public
 */

public class GroupLiveData extends LiveData<Group> {
    @Override
    public void setValue(Group value) {
        super.setValue(value);
    }

    @Override
    protected void postValue(Group value) {
        super.postValue(value);
    }
}
