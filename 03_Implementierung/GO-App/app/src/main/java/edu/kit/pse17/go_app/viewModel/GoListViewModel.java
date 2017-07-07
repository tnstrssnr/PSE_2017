package edu.kit.pse17.go_app.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.repositories.GoRepository;

/**
 * Stellt die Daten für die GoDetailActivity View-Komponente zur Verfügung und übernimmt die Kommunikation
 * mit der Programmlogik, um die richtigen Daten an die View weiterzugeben.
 *
 * Das ViewModel hat keine Abhängigkeit zu der View und wird, anders als die Views, bei Konfigurationsänderungen nicht zerstört,
 * sondern bleibt erhalten.
 */

public class GoListViewModel extends ViewModel {
    /*
    * Group id
    * */
    private long groupId;

    /*
    * List aller GOs in der Gruppe
    * */
    private LiveData<List<Go>> go;

    /*
    * GO Repository
    * */
    private GoRepository goRepo;

    @Inject
    public GoListViewModel(GoRepository goRepo) {
        this.goRepo = goRepo;
    }

    public void init(long groupId) {
        this.groupId = groupId;
        this.go = goRepo.getGo(groupId);
    }

    public LiveData<List<Go>> getGo() {
        return this.go;
    }

}
