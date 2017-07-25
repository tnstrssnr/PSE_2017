package edu.kit.pse17.go_app.viewModel.livedata;

import android.arch.lifecycle.LiveData;

import edu.kit.pse17.go_app.model.entities.Go;

/**
 * Created by Vovas on 24.07.2017.
 */

public class GoLiveData extends LiveData<Go> {
    @Override
    public void setValue(Go value) {
        super.setValue(value);
    }
}
