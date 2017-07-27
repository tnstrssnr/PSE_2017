package edu.kit.pse17.go_app.view;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.model.entities.User;
import edu.kit.pse17.go_app.view.recyclerView.ListAdapter;
import edu.kit.pse17.go_app.view.recyclerView.OnListItemClicked;
import edu.kit.pse17.go_app.view.recyclerView.listItems.GroupListItem;
import edu.kit.pse17.go_app.view.recyclerView.listItems.ListItem;
import edu.kit.pse17.go_app.viewModel.GroupListViewModel;

/**
 *  Hauptansicht der App. Zeigt alle Gruppen eines Benutzers in einer
 *  RecyclerView
 */

public class GroupListActivity extends BaseActivity implements View.OnClickListener, OnListItemClicked{

    private static final String USER_ID_INTENT_CODE = "user_id";
    private static String DIALOG_TAG = "dialog_tag";

    private ListAdapter adapter;
    private FloatingActionButton addGroup;
    private ImageView settings;
    private RecyclerView groupList;
    public static Drawable default_group_icon;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private ActionBar actionBar;
    private GroupAddedBroadcastReceiver receiver;
    //activity which is started upon clicking an element in recyclerview
    private Class nextActivity = GroupDetailActivity.class;

    private GroupListViewModel viewModel;

    public static void start(Activity activity, User user) {
        Intent intent = new Intent(activity, GroupListActivity.class);

        intent.putExtra(USER_ID_INTENT_CODE, user.getUid());

        activity.startActivity(intent);
    }

    /**
     * RecyclerView und passender Listadapter werden erzeugt
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_list_activity);
        default_group_icon = getDrawable(R.drawable.ic_group_white_24dp);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.heading);
        toolbarTitle.setText("ZHOPA");
        toolbarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to group details
            }
        });
        setActionBar(toolbar);

        groupList = (RecyclerView)  findViewById(R.id.group_recycler);
        groupList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        String uid = getIntent().getStringExtra(USER_ID_INTENT_CODE);

        viewModel = ViewModelProviders.of(this).get(GroupListViewModel.class);
        viewModel.init(uid);
        viewModel.getGroups().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(@Nullable List<Group> groups) {
                Log.d("LIVE DATA", "ON CHANGE CALLBACK");
                displayData(groups);
            }
        });
        addGroup = (FloatingActionButton) findViewById(R.id.addGroupBtn);
        addGroup.setOnClickListener(this);
        //TODO get all user groups


    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new GroupAddedBroadcastReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("group_added"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if(item.getItemId() ==  R.id.about){
            //show ABOUT
        } else if(item.getItemId() == R.id.action_settings){
            //open settings activity
            startActivity(new Intent(this, SettingsActivity.class));
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void displayData(List<Group> groups) {
        List<ListItem> data = new ArrayList<>();

        for(Group group: groups) {
            ListItem<Integer> item = new GroupListItem(group);
            data.add(item);
        }

        adapter = new ListAdapter(data, this);
        groupList.setAdapter(adapter);
    }

    /**
     * ClickListener für addGroupButton
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.addGroupBtn) {
            //TODO: MAYBE CHANGE TO SUPPORT FRAGMENT MANAGER, BUT IT DOESN'T COMPILE NOW
            Log.d("addGroupBtn", "I am pressed");
            FragmentManager fm = getFragmentManager();
            AddGroupDialogFragment addGroupDialogFragment = new AddGroupDialogFragment();
            addGroupDialogFragment.show(fm, DIALOG_TAG);
        }
    }

    /**
     * ClickListener für RecyclerView-Elemente
     *
     * @param position Position des ListItems, auf das geklickt wurde
     */
    @Override
    public void onItemClicked(Activity activity, int position) {
        Log.d("CLICKED", "onItemClicked");
    }

    private class GroupAddedBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            Group toCreate = new Group();
            toCreate.setName(intent.getStringExtra("group_name"));
            toCreate.setDescription(intent.getStringExtra("group_description"));
            viewModel.createGroup(toCreate);
            Log.d("Received broadcast", intent.getAction() + toCreate.getName());
        }
    }
    public Class getNextActivity() {
        return nextActivity;
    }
}
