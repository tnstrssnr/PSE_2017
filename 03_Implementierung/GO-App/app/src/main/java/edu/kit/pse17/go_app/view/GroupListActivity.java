package edu.kit.pse17.go_app.view;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.model.entities.User;
import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.view.recyclerView.ListAdapter;
import edu.kit.pse17.go_app.view.recyclerView.OnListItemClicked;
import edu.kit.pse17.go_app.view.recyclerView.listItems.GroupListItem;
import edu.kit.pse17.go_app.view.recyclerView.listItems.ListItem;
import edu.kit.pse17.go_app.viewModel.GroupListViewModel;

/**
 *  Hauptansicht der App. Zeigt alle Gruppen eines Benutzers in einer
 *  RecyclerView
 */

public class GroupListActivity extends BaseActivity implements OnListItemClicked, View.OnClickListener {

    private static final String USER_ID_INTENT_CODE = "user_id";

    private ListAdapter adapter;
    private FloatingActionButton addGroupBtn;
    private ImageView options;
    private RecyclerView list;

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

        list = (RecyclerView)  findViewById(R.id.group_recycler);
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        String uid = getIntent().getStringExtra(USER_ID_INTENT_CODE);

        viewModel = ViewModelProviders.of(this).get(GroupListViewModel.class);

        viewModel.init(uid);

        viewModel.getData().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(@Nullable List<Group> groups) {
                displayData(groups);
            }
        });

        //TODO get all user groups


    }

    private void displayData(List<Group> groups) {
        List<ListItem> data = new ArrayList<>();

        for(Group group: groups) {
            ListItem<Integer> item = new GroupListItem(group);
            data.add(item);
        }

        adapter = new ListAdapter(data, this);
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
