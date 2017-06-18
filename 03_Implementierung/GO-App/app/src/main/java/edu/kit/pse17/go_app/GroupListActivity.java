package edu.kit.pse17.go_app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import edu.kit.pse17.go_app.RecyclerView.ListAdapter;
import edu.kit.pse17.go_app.RecyclerView.ListItem;
import edu.kit.pse17.go_app.RecyclerView.OnListItemClicked;

/**
 *  Hauptansicht der App. Zeigt alle Gruppen eines Benutzers
 */
public class GroupListActivity extends AppCompatActivity implements OnListItemClicked, View.OnClickListener {

    private ListAdapter adapter;

    /**
     * starts the Activity
     * @param activity Activity from which the groupListActivity is started
     */
    public void start(Activity activity) {
        activity.startActivity(new Intent(activity, GroupListActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        RecyclerView list = (RecyclerView)  findViewById(R.id.activity_group_list_recycler);
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        adapter = new ListAdapter(new ArrayList<ListItem>(), this);
        list.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClicked(int position) {

    }
}
