package edu.kit.pse17.go_app.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.kit.pse17.go_app.R;

/**
 * Created by Vovas on 24.07.2017.
 */

public class AddGroupDialogFragment extends DialogFragment{
    private EditText groupName;
    private EditText groupDescription;
    private Button performAdd;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_add_group, null);
        groupDescription = (EditText) view.findViewById(R.id.edit_group_description);
        groupName = (EditText) view.findViewById(R.id.group_name);
        performAdd = (Button) view.findViewById(R.id.perform_add_group);
        performAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent().setAction("group_added")
                        .putExtra("group_name", groupName.getText().toString()).putExtra("group_description", groupDescription.getText().toString()));
                dismiss();
            }
        });
        return builder.setTitle("Create a group").setView(view).create();
    }


}
