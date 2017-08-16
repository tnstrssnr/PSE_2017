package edu.kit.pse17.go_app.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import edu.kit.pse17.go_app.model.entities.User;
import edu.kit.pse17.go_app.repositories.UserRepository;

/**
 * ViewModel, die alle Benutzerdaten beinhaltet. Ist als Singleton implementiert.
 */

public class UserViewModel extends ViewModel{
    /*
    * Die einzige Instanz der Klasse
    * */
    private UserViewModel currentUserViewModel;

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
    /*
    * Gibt zurück die einzige Instanz der UserViewModel
    * */
    public static User getUserInstance(){
        return null;
    }

    public LiveData<User> getUserData(String uId){
        return null;
    }
    /*
    * Steht für andere VievModel Klassen zur Verfügung, wenn sie current UserId brauchen.
    *
    * */
    public String getUserId(){
        return null;
    }
    /*
    * Löscht die User Daten aus dem Gerät und gibt weiter, dass, die Daten aus den
    * Datenbanken gelöscht werden
    */
    public void deleteUser(String userId){
        userRepo.deleteUser(userId);
    }
    /*
    * Wird von Firbase benutzt, um die Login Credentials zu speichern
    * */
    public void setUserCredentials(User user){}

    /*
    * Wird bei dem SignOut Aufruf benötigt, um die gespeicherten
    * Credentials lokal zu löschen.
    * */
    public void deleteUserCredentials(){

    }

}
