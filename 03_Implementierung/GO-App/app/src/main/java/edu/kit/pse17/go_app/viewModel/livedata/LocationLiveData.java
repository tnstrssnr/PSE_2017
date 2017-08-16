package edu.kit.pse17.go_app.viewModel.livedata;

import android.arch.lifecycle.LiveData;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Vovas on 16.08.2017.
 */

public class LocationLiveData extends LiveData<LatLng> {
    @Override
    public void setValue(LatLng value) {
        super.setValue(value);
    }
}
