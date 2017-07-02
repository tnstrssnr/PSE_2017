package edu.kit.pse17.go_app.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import edu.kit.pse17.go_app.model.Cluster;
import edu.kit.pse17.go_app.repositories.LocationRepository;

/**
 * Created by tina on 02.07.17.
 */

public class LocationViewModel extends ViewModel {

    private long goId;
    private LiveData<List<Cluster>> locationData;
    private LocationRepository locationRepo;

    @Inject
    public LocationViewModel(LocationRepository locationRepo) {
        this.locationRepo = locationRepo;
    }

    public void init(long goId) {
        this.goId = goId;
        this.locationData = locationRepo.getLocationData(goId);
    }
}
