package edu.kit.pse17.go_app.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.viewModel.UserViewModel;

/**
 * Die Aktivität stellt dem User ein Menü zur Verfügung, in dem er verschiedende
 * Einstellungsänderungen vornehmen kann.
 *
 * Die Aufgabe der Aktivität ist dabei, dem Benutzer die View zur Verfügung zu stellen,
 * den User-input entgegenzunehmen und an die Eingabe an die entsprechenden Klassen weiterzuleiten.
 */

public class SettingsActivity extends BaseActivity {

    /*
    * Button zum Sign Out
    * */
    private Button signOutButton;
    /*
    * Name des Benutzers
    * */
    private TextView name;
    /*
    * UserViewModel
    * */
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }
    /*
    * Löst Ausloggen von dem Benutzer und schaltet um auf die LoginActivity
    * */
    public void signOut(){}
    /*
    * Startet den Löschung des Benutzers aus dem System und allen Daten
    * */
    public void deleteProfile(){
        signOut();
        userViewModel.deleteUser();
    }

}
