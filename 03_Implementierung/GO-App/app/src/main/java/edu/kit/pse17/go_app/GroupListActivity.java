package edu.kit.pse17.go_app;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import edu.kit.pse17.go_app.RecyclerView.GroupListItem;
import edu.kit.pse17.go_app.RecyclerView.ListAdapter;
import edu.kit.pse17.go_app.RecyclerView.OnListItemClicked;

/**
 *  Hauptansicht der App. Zeigt alle Gruppen eines Benutzers
 */
public class GroupListActivity extends AppCompatActivity implements OnListItemClicked, View.OnClickListener {

    private ListAdapter adapter;
    private FloatingActionButton addGroupBtn;
    private ImageView options;

    /**
     * starts the Activity
     * @param activity Activity from which the groupListActivity is started
     */
    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, GroupListActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_list_activity);

        RecyclerView list = (RecyclerView)  findViewById(R.id.group_recycler);
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //TODO get all user groups
        List data = new ArrayList<GroupListItem>();

        adapter = new ListAdapter(data, this);
        list.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClicked(int position) {

    }
}
