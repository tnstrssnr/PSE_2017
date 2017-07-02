package edu.kit.pse17.go_app.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.kit.pse17.go_app.model.Go;
import edu.kit.pse17.go_app.model.GoRepository;

/**
 * Stellt die Daten für die GoDetailActivity View-Komponente zur Verfügung und übernimmt die Kommunikation
 * mit der Programmlogik, um die richtigen Daten an die View weiterzugeben.
 *
 * Das ViewModel hat keine Abhängigkeit zu der View und wird, anders als die Views, bei Konfigurationsänderungen nicht zerstört,
 * sondern bleibt erhalten.
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
