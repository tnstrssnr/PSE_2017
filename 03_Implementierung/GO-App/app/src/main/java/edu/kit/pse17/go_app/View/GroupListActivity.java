package edu.kit.pse17.go_app.View;

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

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.View.RecyclerView.Adapter.GroupListAdapter;
import edu.kit.pse17.go_app.View.RecyclerView.ListItems.GroupListItem;
import edu.kit.pse17.go_app.View.RecyclerView.Adapter.ListAdapter;
import edu.kit.pse17.go_app.View.RecyclerView.OnListItemClicked;

/**
 *  Hauptansicht der App. Zeigt alle Gruppen eines Benutzers
 */

public class GroupListActivity extends BaseActivity implements OnListItemClicked, View.OnClickListener {

    private ListAdapter adapter;
    private FloatingActionButton addGroupBtn;
    private ImageView options;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, GroupListActivity.class));
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

        RecyclerView list = (RecyclerView)  findViewById(R.id.group_recycler);
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //TODO get all user groups
        List data = new ArrayList<GroupListItem>();

        adapter = new GroupListAdapter(data, this);
        list.setAdapter(adapter);

    }

    /**
     * ClickListener für addGroupButton
     * @param v
     */
    @Override
    public void onClick(View v) {

    }

    /**
     * ClickListener für RecyclerView-Elemente
     *
     * @param position Position des ListItems, auf das geklickt wurde
     */
    @Override
    public void onItemClicked(int position) {

    }
}
