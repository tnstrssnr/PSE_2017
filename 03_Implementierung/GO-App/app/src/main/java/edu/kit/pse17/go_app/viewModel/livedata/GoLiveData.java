package edu.kit.pse17.go_app.viewModel.livedata;

import android.arch.lifecycle.LiveData;

import edu.kit.pse17.go_app.model.entities.Go;

/**
 * Needed to override the setValue and make it public
 */

public class GoLiveData extends LiveData<Go> {
    @Override
    public void setValue(Go value) {
        super.setValue(value);
    }

    @Override
    public void postValue(Go value) {
        super.postValue(value);
    }
}
