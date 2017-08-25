package edu.kit.pse17.go_app.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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

    public static final int EXITED = 1;
    private TextView groupName;
    private TextView groupDescription;
    private RecyclerView members;
    private ListAdapter membersAdapter;
    private GroupViewModel viewModel;
    private ImageView edit;
    private ImageView addMember;
    private ImageView exit;
    private Button deleteGroup;
    private TextView members_count;
    private List<ListItem> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_details);
        groupName = (TextView) findViewById(R.id.group_name);
        groupDescription = (TextView) findViewById(R.id.group_description);
        members = (RecyclerView) findViewById(R.id.group_details_members);
        members.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        members_count = (TextView) findViewById(R.id.members_count);

        //buttons for admin
        edit = (ImageView) findViewById(R.id.edit);
        edit.setVisibility(View.GONE);
        addMember = (ImageView) findViewById(R.id.add_member);
        addMember.setVisibility(View.GONE);
        deleteGroup = (Button) findViewById(R.id.delete_group_btn);
        deleteGroup.setVisibility(View.GONE);

        exit = (ImageView) findViewById(R.id.exit_group);
        exit.setVisibility(View.VISIBLE);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myEmail = getSharedPreferences(getString(R.string.shared_pref_name), MODE_PRIVATE).getString(getString(R.string.user_email), null);
                if(myEmail == null)
                    throw new NullPointerException();


                //TODO if this is the last user, then delete group
                // now the last user is equivalent with admin
                if(viewModel.getGroup().getValue().isAdmin(GroupListActivity.getUserId())){
                    viewModel.deleteGroup(viewModel.getGroup().getValue().getId());
                } else {
                    viewModel.deleteMember(viewModel.getGroup().getValue().getId(), myEmail);
                }
                GroupDetailsActivity.this.setResult(EXITED);
                finish();
            }
        });

        viewModel = ViewModelProviders.of(this).get(GroupViewModel.class);
        viewModel.init(getIntent().getIntExtra("index", -1), GroupListViewModel.getCurrentGroupListViewModel());
        viewModel.getGroup().observe(this, new Observer<Group>() {
            @Override
            public void onChanged(@Nullable Group group) {
                displayData(group);
            }
        });

        String userId = GroupListActivity.getUserId();//getSharedPreferences(getString(R.string.shared_pref_name), MODE_PRIVATE).getString(getString(R.string.user_id), null);
        if(viewModel.getGroup().getValue().isAdmin(userId)){
            addAdminFunctionality();
        }
    }

    private void addAdminFunctionality() {
        edit.setVisibility(View.VISIBLE);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                EditGroupFragment fragment = new EditGroupFragment();
                Bundle args = new Bundle();
                args.putString(getString(R.string.edit_dialog_current_group_name_key), groupName.getText().toString());
                args.putString(getString(R.string.edit_dialog_current_descr_key), groupDescription.getText().toString());
                fragment.setArguments(args);
                fragment.show(fm, "Edit group");
            }
        });
        addMember.setVisibility(View.VISIBLE);
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                AddMemberFragment fragment = new AddMemberFragment();
                Bundle args = new Bundle();
                args.putString(getString(R.string.edit_dialog_current_group_name_key), groupName.getText().toString());
                args.putString(getString(R.string.edit_dialog_current_descr_key), groupDescription.getText().toString());
                fragment.setArguments(args);
                fragment.show(fm, "Add Member");
            }
        });

        deleteGroup.setVisibility(View.VISIBLE);
        deleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteGroup(viewModel.getGroup().getValue().getId());
            }
        });
    }

    private void displayData(Group group) {
        groupName.setText(group.getName());
        groupDescription.setText(group.getDescription());

        data = new ArrayList<>();
        for (GroupMembership member : group.getMembershipList()) {

            ListItem item = new UserMailListItem(member.getUser().getName(), member.getUser().getEmail(), member.isAdmin());
            //ListItem item = new UserMailListItem(member.getUser());
            data.add(item);
        }
        members_count.setText(data.size() + " " + getResources().getString(R.string.limit_of_group_members));

        membersAdapter = new ListAdapter(data, null);
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
