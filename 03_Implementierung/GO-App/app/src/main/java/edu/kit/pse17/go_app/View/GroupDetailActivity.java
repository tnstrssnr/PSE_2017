package edu.kit.pse17.go_app.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.View.RecyclerView.Adapter.GroupListAdapter;
import edu.kit.pse17.go_app.View.RecyclerView.ListItems.GroupListItem;
import edu.kit.pse17.go_app.View.RecyclerView.OnListItemClicked;

/**
 * Created by tina on 19.06.17.
 */

public class GroupDetailActivity extends AppCompatActivity implements OnListItemClicked {

    private TextView groupName;
    private ImageView groupIcon;
    private TextView groupDescription;
    private GroupListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_details);

        RecyclerView list = (RecyclerView)  findViewById(R.id.group_recycler);
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //TODO get all user groups
        List data = new ArrayList<GroupListItem>();

        adapter = new GroupListAdapter(data, this);
        list.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(int position) {

    }

    public void configureView() {

    }
}
