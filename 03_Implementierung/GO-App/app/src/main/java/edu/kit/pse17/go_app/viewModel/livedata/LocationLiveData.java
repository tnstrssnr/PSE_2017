package edu.kit.pse17.go_app.viewModel.livedata;

import android.arch.lifecycle.LiveData;

import com.google.android.gms.maps.model.LatLng;

/**
 * Needed to override the setValue and make it public
 */

public class LocationLiveData extends LiveData<LatLng> {
    @Override
    public void setValue(LatLng value) {
        super.setValue(value);
    }

    @Override
    protected void postValue(LatLng value) {
        super.postValue(value);
    }
}
