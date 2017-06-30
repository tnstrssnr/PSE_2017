package edu.kit.pse17.go_app.view;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import edu.kit.pse17.go_app.model.Group;
import edu.kit.pse17.go_app.model.User;
import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.view.recyclerView.adapter.GroupListAdapter;
import edu.kit.pse17.go_app.view.recyclerView.adapter.ListAdapter;
import edu.kit.pse17.go_app.view.recyclerView.OnListItemClicked;

/**
 *  Hauptansicht der App. Zeigt alle Gruppen eines Benutzers
 */

public class GroupListActivity extends BaseActivity implements OnListItemClicked, View.OnClickListener {

    private static final String USER_ID_INTENT_CODE = "user_id";

    private ListAdapter adapter;
    private FloatingActionButton addGroupBtn;
    private ImageView options;

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

        RecyclerView list = (RecyclerView)  findViewById(R.id.group_recycler);
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        String uid = getIntent().getStringExtra(USER_ID_INTENT_CODE);

        //TODO get all user groups
        List data = Group.getAllGroups(uid);

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
