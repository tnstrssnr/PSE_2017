package edu.kit.pse17.go_app.viewModel;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.repositories.GroupRepository;
import edu.kit.pse17.go_app.viewModel.livedata.GroupLiveData;

/**
 * ViewModel, die alle Daten für eine Gruppe beinhaltet.
 */

public class GroupViewModel extends ViewModel {
    private static GroupViewModel currentViewModel;
    private String groupId;
    /*
    * Liste aller Gos für eine Gruppe
    * */
    private GroupLiveData data;
    private GroupRepository groupRepo;

    public GroupViewModel(){
        currentViewModel = this;
    }
    public void init(){
        data = new GroupLiveData();
    }
    /*
    * gibt alle Gos für die Gruppe zurück
    * loads every time on demand
    * */
    public GroupLiveData getGroup(int index){
        Group group = GroupListViewModel.getCurrentGroupListViewModel().getData().get(index);
        Log.d("INDEX", "getGos at index " + index);
        data.setValue(group);
        return data;
    }
    /*
    * return data of current group
    */
    public Group getGroup(){
        return data.getValue();
    }
    /*
    * Erzeugt ein Neues Go in der Gruppe mit Id groupId,
     * go.goid wird hier nicht benutzt
    * */
    public void createGo(Go go){
        Group group = data.getValue();
        List<Go> goList = group.getCurrentGos();
        goList.add(go);
        group.setCurrentGos(goList);
        data.setValue(group);
    }

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

    public static GroupViewModel getCurrentViewModel(){
        return currentViewModel;
    }
}
