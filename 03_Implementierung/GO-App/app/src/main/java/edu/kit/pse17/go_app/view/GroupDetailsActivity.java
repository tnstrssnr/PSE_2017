package edu.kit.pse17.go_app.view;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.model.entities.GroupMembership;
import edu.kit.pse17.go_app.view.recyclerView.ListAdapter;
import edu.kit.pse17.go_app.view.recyclerView.OnListItemClicked;
import edu.kit.pse17.go_app.view.recyclerView.listItems.ListItem;
import edu.kit.pse17.go_app.view.recyclerView.listItems.UserMailListItem;
import edu.kit.pse17.go_app.viewModel.GroupListViewModel;
import edu.kit.pse17.go_app.viewModel.GroupViewModel;

/**
 * Created by Vovas on 24.07.2017.
 */

public class GroupDetailsActivity extends BaseActivity implements OnListItemClicked {

    private TextView groupName;
    private TextView groupDescription;
    private RecyclerView members;
    private ListAdapter membersAdapter;
    private GroupViewModel viewModel;
    private ImageView edit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_details);
        groupName = (TextView) findViewById(R.id.group_name);
        groupDescription = (TextView) findViewById(R.id.group_description);
        members = (RecyclerView) findViewById(R.id.grou_details_members);
        edit = (ImageView) findViewById(R.id.edit);
        edit.setVisibility(View.INVISIBLE);
        ImageView add_member = (ImageView) findViewById(R.id.add_member);
        add_member.setVisibility(View.INVISIBLE);
        viewModel = ViewModelProviders.of(this).get(GroupViewModel.class);
        viewModel.init(getIntent().getIntExtra("index", -1), GroupListViewModel.getCurrentGroupListViewModel());
        viewModel.getGroups().observe(this, new Observer<Group>() {
            @Override
            public void onChanged(@Nullable Group group) {
                displayData(group);
            }
        });
    }

    private void displayData(Group group) {
        groupName.setText(group.getName());
        groupDescription.setText(group.getDescription());
        List<ListItem> data = new ArrayList<>();
        for (GroupMembership member : group.getMembershipList()) {
            ListItem item = new UserMailListItem(member.getUser().getName(), member.getUser().getEmail());
            data.add(item);
        }
        membersAdapter = new ListAdapter(data, this);
        members.setAdapter(membersAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //wee need to notify
    }

    @Override
    public void onItemClicked(Activity activity, int position) {

    }
}
