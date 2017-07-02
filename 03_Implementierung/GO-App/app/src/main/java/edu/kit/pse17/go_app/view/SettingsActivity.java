package edu.kit.pse17.go_app.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import edu.kit.pse17.go_app.R;

/**
 * Die Aktivität stellt dem User ein Menü zur Verfügung, in dem er verschiedende Einstellungsänderungen
 * vornehmen kann.
 *
 * Die Aufgabe der Aktivität ist dabei, dem Benutzer die View zur Verfügung zu stellen, den User-input entgegenzunehmen
 * und an die Eingabe an die entsprechende Aktivität weiterzuleiten.
 */

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }
}
