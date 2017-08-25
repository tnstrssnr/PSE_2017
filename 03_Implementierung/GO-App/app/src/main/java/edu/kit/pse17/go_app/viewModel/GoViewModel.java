package edu.kit.pse17.go_app.viewModel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
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
    private long goId;
    private int index;
    private String userId;
    private Observer<Group> observer;


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

        if(go == null)
            go = new GoLiveData();
        this.goRepo = GoRepository.getInstance();
        groupViewModel.getGroup().observeForever(observer);
    }
    public void init(final long goId, final String userId, GroupViewModel groupViewModel){
        this.index = index;
        this.userId = userId;
        this.observer = new Observer<Group>() {
            @Override
            public void onChanged(@Nullable Group group) {
                for(Go toDisplay : group.getCurrentGos()){
                    if(toDisplay.getId() == goId){
                        go.setValue(toDisplay);
                    }
                }
                //go.setValue(group.getCurrentGos().get(index));
            }
        };
        this.goId = goId;
        if(go == null)
            go = new GoLiveData();
        this.goRepo = GoRepository.getInstance();
        groupViewModel.getGroup().observeForever(observer);
    }
    /*
    * Teilnahmestatus in einem GO ändern
    * */
    public void changeStatus(String userId, long goId, Status status){
        goRepo.changeStatus(userId, goId, status);
    }
    /*
    * Anfrage für die Standoerte der Go-Teilnehmer, eigener Standort wird gleich in der
    * Methode auch geschickt.
    * */
    public List<Cluster> getClusters(){
        return go.getValue().getLocations();
    }

    public void sendLocation(String userId, long goId, double latitude, double longitude){
        if(goRepo == null){
            goRepo = GoRepository.getInstance();
        }
        goRepo.getLocation(userId,goId,latitude, longitude);
    }
    /*
    * Ändere Daten für Go mit Id goid, id selbst darf nicht geändert werden.
    * */
    public void editGo(long goid, Go newGo){
        goRepo.editGo(goid,GroupViewModel.getCurrentViewModel().getGroup().getValue().getId(),userId,newGo.getName(),
                newGo.getDescription(),newGo.getStart(),newGo.getEnd(), new double[]{newGo.getDesLat(),newGo.getDesLon()}, -1);
    }

    public GoLiveData getGo(){
        return go;
    }

    public  static GoViewModel getInstance() {
        if(currentViewModel == null){
            currentViewModel = new GoViewModel();
        }
        return currentViewModel;
    }

    public void deleteGo(long goId, long groupId) {
        goRepo.deleteGo(goId);
    }
}
