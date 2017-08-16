package edu.kit.pse17.go_app.view;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.model.entities.UserGoStatus;
import edu.kit.pse17.go_app.view.recyclerView.ListAdapter;
import edu.kit.pse17.go_app.view.recyclerView.listItems.ListItem;
import edu.kit.pse17.go_app.view.recyclerView.listItems.UserStatusListItem;
import edu.kit.pse17.go_app.viewModel.GoViewModel;

/**
 * Created by Vovas on 27.07.2017.
 */

public class GoDetailsFragment extends android.support.v4.app.Fragment {
    private TextView description;
    private TextView membersCount;
    private RecyclerView members;
    private ListAdapter adapter;
    private Go go;
    private List<ListItem> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("GodetailsFragment", "onCreateView called now");
        View view = inflater.inflate(R.layout.go_details_tab, container, false);
        description = (TextView) view.findViewById(R.id.go_description);
        membersCount = (TextView) view.findViewById(R.id.member_count);
        members = (RecyclerView) view.findViewById(R.id.go_members);
        members.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //displayData(go);
        GoViewModel.getInstance().getGo().observeForever( new Observer<Go>() {
        @Override
        public void onChanged(@Nullable Go go) {
            Log.d("Fragement", "Fragment caught livedata change");
            displayData(go);
        }
    });
    }

    public void setGo(Go go){
        this.go = go;
    }
    public void displayData(Go go){
        description.setText(go.getDescription());
        String count = go.getParticipantsList().size() + " participants";
        membersCount.setText(count);
        Log.d("GoDetailA", "Display data " + go.getParticipantsList().size());
        list = new ArrayList<>();
        for (UserGoStatus status : go.getParticipantsList()) {
            ListItem listItem = new UserStatusListItem(status.getUser().getName(), status.getStatus());
            list.add(listItem);
        }
        adapter = new ListAdapter(list, null);
        members.setAdapter(adapter);
    }

}
