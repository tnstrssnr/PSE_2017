package edu.kit.pse17.go_app.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.kit.pse17.go_app.model.Go;
import edu.kit.pse17.go_app.model.GoRepository;

/**
 * Created by tina on 01.07.17.
 */

public class GoViewModel extends ViewModel {

    private long goId;
    private LiveData<Go> go;
    private GoRepository goRepo;

    @Inject
    public GoViewModel(GoRepository goRepo) {
        this.goRepo = goRepo;
    }

    public void init(long goId) {
        this.goId = goId;
        this.go = goRepo.getGo(goId);
    }

    public LiveData<Go> getGo() {
        return this.go;
    }

}
