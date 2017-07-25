package edu.kit.pse17.go_app.viewModel.livedata;

import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.kit.pse17.go_app.model.entities.Group;

/**
 * Created by Vovas on 24.07.2017.
 */

public class GroupListLiveData extends LiveData<List<Group>> {
    @Override
    public void setValue(List<Group> value) {
        super.setValue(value);
    }
}
