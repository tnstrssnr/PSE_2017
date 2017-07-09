package edu.kit.pse17.go_app.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * Stellt die Daten für die GroupDetailActivity View-Komponente zur Verfügung und übernimmt
 * die Kommunikation mit der Programmlogik, um die richtigen Daten an die View weiterzugeben.
 *
 * Das ViewModel hat keine Abhängigkeit zu der View und wird, anders als die Views,
 * bei Konfigurationsänderungen nicht zerstört, sondern bleibt erhalten.
 */


public class GroupListViewModel extends ViewModel {
    /*
    * speichert die current Instanz von GrouplistViewModel, damit die Daten von Commands
    * zugreifbar sind
    * */
    public static GroupListViewModel currentViewModel;
    /**
     * Die Id des Benutzers, die in dem ViewModel gespeichert ist.
     */
    private String uId;

    /**
     * Das Datenobjekt, das an die View weitergegeben werden soll, gespeichert als LiveData-Objekt,
     * wodurch es für die View observable ist.
     */
    private LiveData<List<Group>> data;

    /**
     * Das Grouprepository, das für die beschaffung der Daten zuständig ist.
     */
    private GroupRepository groupRepo;

    @Inject
    public GroupListViewModel(GroupRepository groupRepo) {
        this.groupRepo = groupRepo;
    }

    public void init(String uId) {
        this.uId = uId;
        this.data = groupRepo.getGroupsForUser(uId);
    }
    /*
    * Gibt zurück eine Liste aller Gruppen für ein User
    * */
    public LiveData<List<Group>> getGroups(String userId) {
        return this.data;
    }
    /*
    * Erzeugt eine neue Gruppe und leitet das weiter, damit die Daten auf dem Server persistiert werden
    * group.id wird nicht berücksichtigt bei der Erstellung.
    * */
    public void createGroup(Group group){}
    /*
    * Gibt zurück die Instanz des Viewmodels
    * */
    public static GroupListViewModel getCurrentGroupListViewModel(){
    }

}
