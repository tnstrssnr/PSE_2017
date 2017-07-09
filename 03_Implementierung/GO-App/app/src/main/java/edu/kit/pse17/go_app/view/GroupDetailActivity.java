package edu.kit.pse17.go_app.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.widget.ImageView;
import android.widget.TextView;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.view.recyclerView.OnListItemClicked;
import edu.kit.pse17.go_app.viewModel.GroupListViewModel;

/**
 * die Activity ist zusammen mit der Layout File group_details.xml Teil des Views, der dem user die Details eines Gruppe anzeigt.
 *
 * Die Activity ist hauptsächlich für die Darstellung von Informationen zuständig. Die einzige Datenmanipulation, die hier vorgenommen werden kann,
 * ist die Änderung das Austreten des Users aus der Gruppe.
 */

public class GroupDetailActivity extends BaseActivity implements OnListItemClicked {

    /**
     * Viewmodel Instanz, in der die dargestellten Daten der Aktivität gespeichert werden, um sie bei
     * Konfigurationsänderungen zu erhalten.
     */
    private GroupListViewModel viewModel;
    private TextView groupName;
    private ImageView groupIcon;
    private TextView groupDescription;
    private FloatingActionButton createGo;
    /*
    * Viewmodel, die für die Daten in der Aktivity zuständig ist.
    * */
    private GroupViewModel groupViewModel;

    /**
     * Lifecycle-Methode der Activity, die beim Erzeugen aufgreufen wird. Dem ContentView der App wird das richtige
     * XML Layout zugewiesen und die Informationen die das ViewModel bereitstellt den Layout-Komponenten zur Darstellung
     * übergeben.
     *
     * Es die Livedata des ViewModels auf zum Beobachten registriert, um bei Änderungen die View updaten zu können.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_details);

        viewModel = ViewModelProviders.of(this).get(GroupListViewModel.class);

        viewModel.getGroup().observe(this, new Observer<Group>() {
                    @Override
                    public void onChanged(@Nullable Group group) {
                        displayData(group);
                    }
                });

        displayData(viewModel.getGroup().getValue());

    }

    @Override
    public void onItemClicked(int position) {

    }

    /**
     * Weißt die Daten des Gos den entsprechenden Laoyut-Komponenten zu.
     *
     * @param group Das Go dessen Daten in der View angezeigt werden sollen
     */
    private void displayData(Group group) {

    }

}
