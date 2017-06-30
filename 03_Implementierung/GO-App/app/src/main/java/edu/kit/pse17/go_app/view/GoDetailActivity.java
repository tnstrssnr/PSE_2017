package edu.kit.pse17.go_app.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import edu.kit.pse17.go_app.view.recyclerView.adapter.UserListAdapter;

import edu.kit.pse17.go_app.R;

/**
 * Created by tina on 20.06.17.
 */

public class GoDetailActivity extends BaseActivity {

    private TextView name;
    private TextView startDate;
    private TextView startTime;
    private TextView description;
    private TextView memberCount;
    private UserListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.go_details);
    }
}
