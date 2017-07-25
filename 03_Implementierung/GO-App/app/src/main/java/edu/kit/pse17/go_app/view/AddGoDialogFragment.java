package edu.kit.pse17.go_app.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import edu.kit.pse17.go_app.R;

/**
 * Created by Vovas on 24.07.2017.
 */

public class AddGoDialogFragment extends DialogFragment {

    private EditText go_name;
    private EditText go_description;
    private EditText start_date;
    private EditText end_date;
    private Button performAddGo;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_add_go, null);
        go_name = (EditText) view.findViewById(R.id.go_name);
        go_description = (EditText) view.findViewById(R.id.go_description);
        start_date = (EditText) view.findViewById(R.id.start_date);
        end_date = (EditText) view.findViewById(R.id.end_date);
        performAddGo = (Button) view.findViewById(R.id.perform_add_go);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity());
        datePickerDialog.show();
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d("DATEPICKER","Now I got set");
            }
        });
        performAddGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goAdded = new Intent().setAction(getString(R.string.go_added_intent_action));
                goAdded.putExtra("go_name", go_name.getText().toString())
                        .putExtra("go_description",go_description.getText().toString())
                        .putExtra("start_date", start_date.getText().toString())
                        .putExtra("end_date", end_date.getText().toString());
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(goAdded);
                dismiss();
            }
        });
        return builder.setTitle("Create a new Go").setView(view).create();
    }



}
