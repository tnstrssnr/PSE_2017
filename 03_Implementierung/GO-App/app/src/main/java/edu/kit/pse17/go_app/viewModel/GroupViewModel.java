package edu.kit.pse17.go_app.viewModel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
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
    private Observer<List<Group>> observer;
    /*
    * Liste aller Gos für eine Gruppe
    * */
    private GroupLiveData data;
    private GroupRepository groupRepo;
    private int index;

    public GroupViewModel(){
        currentViewModel = this;
        if(data == null)
            data = new GroupLiveData();

    }
    public void init(final int index, GroupListViewModel mainViewModel){

        this.observer = new Observer<List<Group>>() {

            @Override
            public void onChanged(@Nullable List<Group> groups) {
                Log.d("GO ADDED GroupVM", "I got called so observer should function");
                data.setValue(groups.get(index));

            }
        };
        mainViewModel.getGroups().observeForever(observer);
        this.index = index;
        if(data == null)
            data = new GroupLiveData();
        this.groupRepo = GroupRepository.getInstance();
    }
    /*
    * gibt alle Gos für die Gruppe zurück
    * loads every time on demand
    * */
    public GroupLiveData getGroup(){
        Group group = GroupListViewModel.getCurrentGroupListViewModel().getData().get(index);
        Log.d("INDEX", "getGos at index " + index);
        data.setValue(group);
        return data;
    }

    /*
    * Erzeugt ein Neues Go in der Gruppe mit Id groupId,
     * go.goid wird hier nicht benutzt
    * */
    public void onGoCreated(Go go){
        groupRepo.onGoCreated(go, data.getValue().getId());

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

    @Override
    protected void onCleared() {
        super.onCleared();
        groupRepo.getData().removeObserver(observer);
    }
}
