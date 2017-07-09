package edu.kit.pse17.go_app.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.repositories.GroupRepository;

/**
 * ViewModel, die alle Daten für eine Gruppe beinhaltet.
 */

public class GroupViewModel extends ViewModel {
    private String groupId;
    /*
    * Liste aller Gos für eine Gruppe
    * */
    private LiveData<List<Go>> data;
    private GroupRepository groupRepo;

    public void init(){}
    /*
    * gibt alle Gos für die Gruppe zurück
    * */
    public LiveData<List<Go>> getGos(String groupId){

    }
    /*
    * Erzeugt ein Neues Go in der Gruppe mit Id groupId,
     * go.goid wird hier nicht benutzt
    * */
    public void createGo(Go go){}
    /*
    * Ein Teilnhemer zur Gruppe hinzufügen mit Hilfe der Email
    * */
    public void addMember(String EMail){}
    /*
    * Ändert die Daten von der Gruppe mit groupId, group.groupId wird nicht benutzt in der Methode
    * */
    public void editGroup(Group group){
    }
    /*
    * True - akzeptiere den Request für die Gruppe groupId
    * False - verwerfe den Request
    * */
    public void answerGroupRequest(boolean answer){}

}
