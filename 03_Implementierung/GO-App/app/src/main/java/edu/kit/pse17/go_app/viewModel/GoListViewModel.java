package edu.kit.pse17.go_app.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.repositories.GoRepository;

/**
 * Created by tina on 02.07.17.
 */

public class GoListViewModel extends ViewModel {

    private String userId;
    private LiveData<List<Go>> data;
    private GoRepository goRepo;

    @Inject
    public GoListViewModel(GoRepository goRepo) {
        this.goRepo = goRepo;
    }

    public void init(String uid) {
        this.userId = uid;
        this.data = goRepo.getGosForUser(uid);
    }
}
