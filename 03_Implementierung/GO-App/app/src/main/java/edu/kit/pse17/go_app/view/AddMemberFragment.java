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

public class AddMemberFragment extends DialogFragment {
    private EditText email;
    private Button performAdd;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_member, null);
        email = (EditText) view.findViewById(R.id.email_edit_text);

        performAdd = (Button) view.findViewById(R.id.perform_add_member);
        performAdd.setText("Add user");
        performAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()) {
                    GroupViewModel viewModel = GroupViewModel.getCurrentViewModel();
                    viewModel.addMember(email.getText().toString());
                    dismiss();
                }
            }
        });
        return builder.setTitle("Create a group").setView(view).create();
    }

    private boolean checkInput(){
        if(email.getText().toString().isEmpty()){
            Toast.makeText(this.getContext(),"Email field must not be empty.", Toast.LENGTH_LONG).show();
            return  false;
        } else {
            return true;
        }
    }
}
