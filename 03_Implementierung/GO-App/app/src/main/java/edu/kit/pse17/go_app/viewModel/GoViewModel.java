package edu.kit.pse17.go_app.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

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
    private String userId;
    private Observer<Group> observer;

    private Observer<LatLng> locationObserver;


    public GoViewModel(){
        if(go == null)
            go = new GoLiveData();
        currentViewModel = this;
    }

    public void init(final int index, final String userId, GroupViewModel groupViewModel){
        this.index = index;
        this.userId = userId;
        this.observer = new Observer<Group>() {
            @Override
            public void onChanged(@Nullable Group group) {
                go.setValue(group.getCurrentGos().get(index));
            }
        };
        this.locationObserver = new Observer<LatLng>() {
            @Override
            public void onChanged(@Nullable LatLng latLng) {
                sendLocation(userId, go.getValue().getId(), latLng.latitude, latLng.longitude);
            }
        };
        groupViewModel.getGroup().observeForever(observer);
        GroupListViewModel.getCurrentGroupListViewModel().getLocation().observeForever(locationObserver);
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
    public LiveData<List<Cluster>> getCluster(String userId,long goId, LatLng location){
        return null;
    }
    private void sendLocation(String userId, long goId, double latitude, double longitude){
        goRepo.getLocation(userId,goId,latitude, longitude);
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
