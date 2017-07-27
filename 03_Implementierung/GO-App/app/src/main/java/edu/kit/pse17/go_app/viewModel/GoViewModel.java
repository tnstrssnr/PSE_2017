package edu.kit.pse17.go_app.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.location.Location;
import android.support.annotation.Nullable;

import java.util.List;

import edu.kit.pse17.go_app.model.Status;
import edu.kit.pse17.go_app.model.entities.Cluster;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.repositories.GoRepository;
import edu.kit.pse17.go_app.viewModel.livedata.GoLiveData;

/**
 * Die ViewModel Klasse, die alle Daten für ein GO beinhaltet, und die Bearbeitungsaufrufe
 * von Activities auf den Daten ausführt.
 */

public class GoViewModel extends ViewModel{

    private static GoViewModel currentViewModel;

    /*
    * Liste der Cluster. Wird benutzt um die Standorte anzuzeigen.
    * */
    private GoLiveData go;
    /*
    * Go Repository
    * */
    private GoRepository goRepo;

    private int index;

    private Observer<Group> observer;

    private String goId;

    public GoViewModel(){
        if(go == null)
            go = new GoLiveData();
        currentViewModel = this;
    }

    public void init(final int index, GroupViewModel groupViewModel){
        this.index = index;
        this.observer = new Observer<Group>() {
            @Override
            public void onChanged(@Nullable Group group) {
                go.setValue(group.getCurrentGos().get(index));
            }
        };

        groupViewModel.getGroup().observeForever(observer);
        if(go == null)
            go = new GoLiveData();
        this.goRepo = GoRepository.getInstance();

    }
    /*
    * Teilnahmestatus in einem GO ändern
    * */
    public void changeStatus(String userId, String goId, Status status){

    }
    /*
    * Anfrage für die Standoerte der Go-Teilnehmer, eigener Standort wird gleich in der
    * Methode auch geschickt.
    * */
    public LiveData<List<Cluster>> getCluster(String userId,String groupId, Location location){
        return null;
    }
    /*
    * Ändere Daten für Go mit Id goid, id selbst darf nicht geändert werden.
    * */
    public void editGo(String goid, Go go){}

    public GoLiveData getGo(){
        return go;
    }

    public  static GoViewModel getInstance(){
        return currentViewModel;
    }
}
