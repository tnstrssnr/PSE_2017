package edu.kit.pse17.go_app.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.viewModel.GroupViewModel;

/**
 * Created by Vovas on 17.08.2017.
 */

public class EditGroupFragment extends DialogFragment {
    private EditText groupName;
    private EditText groupDescription;
    private Button performEdit;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_fragment_add_group, null);
        groupDescription = (EditText) view.findViewById(R.id.edit_group_description);
        groupName = (EditText) view.findViewById(R.id.group_name);
        groupName.setText(getArguments().getString(getString(R.string.edit_dialog_current_group_name_key)));
        groupDescription.setText(getArguments().getString(getString(R.string.edit_dialog_current_descr_key)));

        performEdit = (Button) view.findViewById(R.id.perform_add_group);
        performEdit.setText("Save");
        performEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()){
                    GroupViewModel viewModel = GroupViewModel.getCurrentViewModel();
                    viewModel.editGroup(viewModel.getGroup().getValue().getId(), groupName.getText().toString(), groupDescription.getText().toString());
                    dismiss();
                }
            }
        });
        return builder.setTitle("Create a group").setView(view).create();
    }
    private boolean checkInput(){
        if(groupName.getText().toString().isEmpty()){
            Toast.makeText(this.getContext(),"Group name must not be empty.", Toast.LENGTH_LONG).show();
            return  false;
        } else if(groupDescription.getText().toString().isEmpty()){
            Toast.makeText(this.getContext(),"Group description must not be empty.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
}
