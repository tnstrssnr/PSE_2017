package edu.kit.pse17.go_app.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.model.Group;
import edu.kit.pse17.go_app.view.recyclerView.adapter.GroupListAdapter;
import edu.kit.pse17.go_app.view.recyclerView.listItems.GroupListItem;
import edu.kit.pse17.go_app.view.recyclerView.OnListItemClicked;
import edu.kit.pse17.go_app.viewModel.GroupViewModel;

/**
 * Created by tina on 19.06.17.
 */

public class GroupDetailActivity extends BaseActivity implements OnListItemClicked {

    private TextView groupName;
    private ImageView groupIcon;
    private TextView groupDescription;
    private GroupListAdapter adapter;

    private GroupViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_details);

        viewModel = ViewModelProviders.of(this).get(GroupViewModel.class);

        viewModel.getGroup().observe(this, new Observer<Group>() {
            @Override
            public void onChanged(@Nullable Group group) {
                //update UI
            }
        });

    }

    @Override
    public void onItemClicked(int position) {

    }

}
