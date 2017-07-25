package edu.kit.pse17.go_app.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.location.Location;

import java.util.List;

import edu.kit.pse17.go_app.model.Status;
import edu.kit.pse17.go_app.model.entities.Cluster;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.repositories.GoRepository;

/**
 * Die ViewModel Klasse, die alle Daten für ein GO beinhaltet, und die Bearbeitungsaufrufe
 * von Activities auf den Daten ausführt.
 */

public class GoViewModel extends ViewModel{
    /*
    * Liste der Cluster. Wird benutzt um die Standorte anzuzeigen.
    * */
    private LiveData<Go> go;
    /*
    * Go Repository
    * */
    private GoRepository goRepo;

    private String goId;

    public void init(){}
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

    public LiveData<Go> getGo(int index){
        Go goData = GroupViewModel.getCurrentViewModel().getGroup().getCurrentGos().get(index);
        MutableLiveData<Go> liveGo = new MutableLiveData<>();
        liveGo.postValue(goData);
        go = liveGo;
        return liveGo;
    }
}
