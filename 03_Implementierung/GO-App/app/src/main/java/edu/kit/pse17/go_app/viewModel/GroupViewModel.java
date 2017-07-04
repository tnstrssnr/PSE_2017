package edu.kit.pse17.go_app.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * Stellt die Daten für die GroupDetailActivity View-Komponente zur Verfügung und übernimmt die Kommunikation
 * mit der Programmlogik, um die richtigen Daten an die View weiterzugeben.
 *
 * Das ViewModel hat keine Abhängigkeit zu der View und wird, anders als die Views, bei Konfigurationsänderungen nicht zerstört,
 * sondern bleibt erhalten.
 */


public class GroupViewModel extends ViewModel {

    /**
     * Die Id der Gruppe, die in dem ViewModel gespeichert ist.
     */
    private long groupId;

    /**
     * Das Datenobjekt, das an die View weitergegeben werden soll, gespeichert als LiveData-Objekt,
     * wodurch es für die View observable ist.
     */
    private LiveData<Group> group;

    /**
     * Das Grouprepository, das für die beschaffung der Daten zuständig ist.
     */
    private GroupRepository groupRepo;

    @Inject
    public GroupViewModel(GroupRepository groupRepo) {
        this.groupRepo = groupRepo;
    }

    public void init(long groupId) {
        this.groupId = groupId;
        this.group = groupRepo.getGroup(groupId);
    }

    public LiveData<Group> getGroup() {
        return this.group;
    }

}
