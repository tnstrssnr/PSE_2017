package edu.kit.pse17.go_app.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.widget.TextView;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.viewModel.GoViewModel;

/**
 * die Activity ist zusammen mit der Layout File go_detail.xml Teil des Views, der dem user die Details eines Gos anzeigt.
 *
 * Die Activity ist hauptsächlich für die Darstellung von Informationen zuständig. Die einzige Datenmanipulation, die hier vorgenommen werden kann,
 * ist die Änderung des Teilnahmestatus des Users.
 */

public class GoDetailActivity extends BaseActivity {
    private TextView title;
    private TextView description;
    private TextView startTime;
    private TextView endTime;
    private TextView memberCount;
    private static final String INDEX_INTENT_CODE = "index";
    /*
    * Knopf um den Teilnahmestatus in dem GO zu ändern.
    * */
    private FloatingActionButton changeStatus;

    /**
     * Viewmodel Instanz, in der die dargestellten Daten der Aktivität gespeichert werden, um sie bei
     * Konfigurationsänderungen zu erhalten.
     */
    private GoViewModel viewModel;

    /**
     * Lifecycle-Methode der Activity, die beim Erzeugen aufgreufen wird. Dem ContentView der App wird das richtige
     * XML Layout zugewiesen und die Informationen die das ViewModel bereitstellt den Layout_komponenten zur Darstellung
     * übergeben.
     *
     * Es die Livedata des ViewModels auf zum Beobachten registriert, um bei Änderungen die View updaten zu können.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.go_details);
        int index = getIntent().getIntExtra(INDEX_INTENT_CODE, -1);
        viewModel = ViewModelProviders.of(this).get(GoViewModel.class);
        viewModel.getGo(index).observe(this, new Observer<Go>() {

            @Override
            public void onChanged(@Nullable Go  go) {
                //update UI
                displayData(go);
            }
        });
    }

    /**
     * Weißt die Daten des Gos den entsprechenden Laoyut-Komponenten zu.
     *
     * @param go Das Go dessen Daten in der View angezeigt werden sollen
     */
    private void displayData(Go go) {
        //TODO: make XML elements display Go Daata

    }
}
