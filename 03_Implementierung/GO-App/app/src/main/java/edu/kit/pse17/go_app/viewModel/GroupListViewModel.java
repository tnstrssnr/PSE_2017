package edu.kit.pse17.go_app.viewModel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.repositories.GroupRepository;
import edu.kit.pse17.go_app.viewModel.livedata.GroupListLiveData;

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

    private static GroupListViewModel currentViewModel;
    /**
     * Die Id des Benutzers, die in dem ViewModel gespeichert ist.
     */
    private String uId;

    /**
     * Das Datenobjekt, das an die View weitergegeben werden soll, gespeichert als LiveData-Objekt,
     * wodurch es für die View observable ist.
     */
    private GroupListLiveData data;
    /*boolean which says if an Observer in Groupviewmodel was already added, for the data of this
    in order to avoid adding the same observer multiple times
    might also do it later in GroupListLiveData class
    */
    private boolean alreadyAdded = false;
    /**
     * Das Grouprepository, das für die beschaffung der Daten zuständig ist.
     */
    private GroupRepository groupRepo;
    private Observer<List<Group>> observer ;


    @Inject
    public GroupListViewModel() {
        currentViewModel = this;

        this.observer = new Observer<List<Group>>() {

            @Override
            public void onChanged(@Nullable List<Group> groups) {
                Log.d("GO ADDED GroupListVM", "I got called so observer should function");
                /*if(GroupViewModel.getCurrentViewModel() != null && !alreadyAdded) {
                    data.observeForever(GroupViewModel.getCurrentViewModel().getObserver());
                    alreadyAdded = true;
                }*/
                data.setValue(groups);
                //for now need to check with a custom boolean if an observer was added to data, to not add multiple observers later


            }
        };
        this.groupRepo = GroupRepository.getInstance();
        groupRepo.getData().observeForever(observer);

    }

    public void init(String uId) {
        this.uId = uId;
    }
    /*
    * Gibt zurück eine Liste aller Gruppen für ein User
    * */

    public GroupListLiveData getGroups() {
        if(data == null){
            data = new GroupListLiveData();
            data.setValue(groupRepo.fetchData());
        }
        return data;

    }
    protected List<Group> getData(){
        return data.getValue();
    }

    /*
    * Erzeugt eine neue Gruppe und leitet das weiter, damit die Daten auf dem Server persistiert werden
    * group.id wird nicht berücksichtigt bei der Erstellung.
    * */

    public void createGroup(Group group){
        //now just add a group to live data here, later maybe change to server applicable stuff
        //groupRepo.createGroup(group);
        List<Group> newdata = data.getValue();
        newdata.add(group);
        data.setValue(newdata);


        Log.d("VIewModel", "HasObservers? " + data.hasObservers());


    }

    /*
    * Gibt zurück die Instanz des Viewmodels
    * */

    public static GroupListViewModel getCurrentGroupListViewModel(){
        return currentViewModel;
    }

}
