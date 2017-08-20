package edu.kit.pse17.go_app.viewModel.livedata;

import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.kit.pse17.go_app.model.entities.Group;

/**
 * Needed to override the setValue and make it public
 */

public class GroupListLiveData extends LiveData<List<Group>> {

    @Override
    public void setValue(List<Group> value) {
        super.setValue(value);
    }

    @Override
    public void postValue(List<Group> value) {
        super.postValue(value);
    }
}
