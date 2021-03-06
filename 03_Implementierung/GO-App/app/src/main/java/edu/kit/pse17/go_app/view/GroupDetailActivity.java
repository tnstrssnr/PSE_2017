package edu.kit.pse17.go_app.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.view.recyclerView.ListAdapter;
import edu.kit.pse17.go_app.view.recyclerView.OnListItemClicked;
import edu.kit.pse17.go_app.view.recyclerView.listItems.GOListItem;
import edu.kit.pse17.go_app.view.recyclerView.listItems.ListItem;
import edu.kit.pse17.go_app.viewModel.GroupListViewModel;
import edu.kit.pse17.go_app.viewModel.GroupViewModel;

/**
 * die Activity ist zusammen mit der Layout File group_details.xml Teil des Views, der dem user die Details eines Gruppe anzeigt.
 *
 * Die Activity ist hauptsächlich für die Darstellung von Informationen zuständig. Die einzige Datenmanipulation, die hier vorgenommen werden kann,
 * ist die Änderung das Austreten des Users aus der Gruppe.
 */

public class GroupDetailActivity extends BaseActivity implements OnListItemClicked {

    private static final String INDEX_INTENT_CODE = "index";
    public static final String GROUP_NAME_INTENT_CODE = "group_name";
    /**
     * Viewmodel Instanz, in der die dargestellten Daten der Aktivität gespeichert werden, um sie bei
     * Konfigurationsänderungen zu erhalten.
     */
    private ListAdapter adapter;
    private RecyclerView goList;
    private GroupViewModel viewModel;
    private TextView groupName;
    private ImageView groupIcon;
    private TextView groupDescription;
    private FloatingActionButton onGoAdded;
    private GoAddedBroadcastReceiver receiver;
    private int index;//index of this group in the list it was selected
    /*
    * Viewmodel, die für die Daten in der Aktivity zuständig ist.
    * */
    private GroupViewModel groupViewModel;

    public static void start(Activity activity, int index){

        activity.startActivity(new Intent().putExtra(INDEX_INTENT_CODE, index));

    }

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

        setContentView(R.layout.group_view);
        index = getIntent().getIntExtra(INDEX_INTENT_CODE, -1);
        goList = (RecyclerView) findViewById(R.id.gos_recycler_view);
        goList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        groupName = (TextView) findViewById(R.id.edit_group_name);
        groupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(v.getContext(), GroupDetailsActivity.class).putExtra("index", index), 0);
            }
        });
        onGoAdded = (FloatingActionButton) findViewById(R.id.add_go_button);
        onGoAdded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), EditGoActivity.class)
                        .putExtra(EditGoActivity.REQUEST_INTET_CODE,EditGoActivity.ADD_REQUEST));
            }
        });
        viewModel = ViewModelProviders.of(this).get(GroupViewModel.class);
        viewModel.init(index, GroupListViewModel.getCurrentGroupListViewModel());
        viewModel.getGroup().observe(this, new Observer<Group>() {
                    @Override
                    public void onChanged(@Nullable Group group) {
                        Log.d("GroupDetailActivity", "Broadcast data changed in VM");
                        displayData(group);
                    }
                });

        //displayData(viewModel.getGos(index).getValue());
        receiver = new GoAddedBroadcastReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(getString(R.string.go_added_intent_action)));

        showRequestDialogIfNeeded();



    }

    private void showRequestDialogIfNeeded() {
        String userId = GroupListActivity.getUserId();
        //getSharedPreferences(getString(R.string.shared_pref_name), MODE_PRIVATE).getString("uid", null);
        if(viewModel.getGroup().getValue().isRequest(userId)){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.accept_dialog_title)
                .setMessage(R.string.dialog_explanation_message);
        builder.setNegativeButton(R.string.decline, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                GroupDetailActivity.this.finish();
                //TODO send decline to server!
                viewModel.answerGroupRequest(false);
            }
        });
        builder.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //TODO send accept request to server
                viewModel.answerGroupRequest(true);
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    }

    /**
     * Weißt die Daten des Gos den entsprechenden Laoyut-Komponenten zu.
     *
     * @param group Die Gruppe dessen Daten in der View angezeigt werden sollen
     */
    private void displayData(Group group) {
        groupName.setText(group.getName());
        List<ListItem> data = new ArrayList<>();

        for(Go go: group.getCurrentGos()) {
            ListItem item = new GOListItem(go);
            data.add(item);
        }

        adapter = new ListAdapter(data, this);
        goList.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getParent()).unregisterReceiver(receiver);
    }

    @Override
    public void onItemClicked(Activity activity, int position) {

    }

    @Override
    public Class getNextActivity() {
        return GoDetailActivity.class;
    }

    private class GoAddedBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("GroupDetailActivity", "Go added broadcast received");
            Go go = new Go();
            String goName = intent.getStringExtra("go_name");
            go.setName(goName);
            String goDescription = intent.getStringExtra("go_description");
            go.setDescription(goDescription);
            String start_time = intent.getStringExtra("start_date");
            go.setStart(start_time);
            String end_time = intent.getStringExtra("end_date");
            go.setEnd(end_time);
            double goLat = intent.getDoubleExtra("lat",0);
            go.setDesLat(goLat);
            double goLon = intent.getDoubleExtra("lng",0);
            go.setDesLon(goLon);
            //viewModel.
            String userId = GroupListActivity.getUserId();//getSharedPreferences(getString(R.string.shared_pref_name), MODE_PRIVATE).getString(getString(R.string.user_id),null);
            viewModel.createGo(goName, goDescription, start_time, end_time,goLat, goLon,  viewModel.getGroup().getValue().getId(), userId);
            //viewModel.onGoAdded(go);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0){
            if(resultCode == GroupDetailsActivity.EXITED){
                this.finish();
            }
        }
    }
}
