package edu.kit.pse17.go_app.viewModel;

import android.arch.lifecycle.LiveData;

import edu.kit.pse17.go_app.model.entities.User;
import edu.kit.pse17.go_app.repositories.UserRepository;

/**
 * Created by tina on 06.07.17.
 */

public class UserViewModel {
    /*
    * User id
    * */
    private String uId;
    /*
    * User Daten
    * */
    private LiveData<User> data;
    /*
    * User Repository
    * */
    private UserRepository userRepo;

    public void init(String uId){

    }
    public LiveData<User> getUserData(String uId){

    }
    /*
    * Löscht die User Daten aus dem Gerät und gibt weiter, dass, die Daten aus den
    * Datenbanken gelöscht werden
    */
    public void deleteUser(){
        userRepo.deleteUser(uId);
    }
}
