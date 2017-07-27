package edu.kit.pse17.go_app.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import edu.kit.pse17.go_app.R;

/**
 * This class was created to implement Add Go action as an activity and not as a pop-up dialog as prior to that
 * because you can make a cleaner and more pleasant user interface inside an activity than in a dialog.
 */

public class AddGoActivity extends BaseActivity {
    private EditText go_name;
    private EditText go_description;
    private TextView start_date_text;
    private Calendar start_date;
    private TextView end_date_text;
    private Calendar end_date;
    private DateFormat format = new SimpleDateFormat();
    private Button start_date_button;
    private Button start_time_button;
    private Button end_date_button;
    private Button end_time_button;
    private Button performAddGo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_go_activity);

        go_name = (EditText) findViewById(R.id.go_name);
        go_description = (EditText) findViewById(R.id.go_description);
        start_date_text = (TextView) findViewById(R.id.start_date_text);

        end_date_text = (TextView) findViewById(R.id.end_date_text);
        start_date_button = (Button) findViewById(R.id.start_date_button);
        end_date_button = (Button)findViewById(R.id.end_date_button);
        start_time_button = (Button) findViewById(R.id.start_time_button);
        end_time_button = (Button) findViewById(R.id.end_time_button);
        performAddGo = (Button) findViewById(R.id.perform_add_go);

        start_date = Calendar.getInstance();
        end_date = Calendar.getInstance();
        displayDates();
        start_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext());
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        start_date.set(year, month, dayOfMonth);
                        displayDates();
                    }
                });
                datePickerDialog.show();
            }
        });
        start_time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        start_date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        start_date.set(Calendar.MINUTE, minute);
                        displayDates();
                    }
                }, Calendar.HOUR_OF_DAY,Calendar.MINUTE, true /*assuming 24h format here*/);
                timePickerDialog.show();

            }
        });
        end_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext());
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        end_date.set(year, month, dayOfMonth);
                        displayDates();
                    }
                });
                datePickerDialog.show();
            }
        });
        end_time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        end_date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        end_date.set(Calendar.MINUTE, minute);
                        displayDates();
                    }
                }, Calendar.HOUR_OF_DAY,Calendar.MINUTE, true /*assuming 24h format here*/);
                timePickerDialog.show();

            }
        });
        performAddGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAllFieldsRequired() && checkDates()){
                    //if true then all fields passed the check
                    Intent goAdded = new Intent().setAction(getString(R.string.go_added_intent_action));
                    goAdded.putExtra("go_name", go_name.getText().toString())
                            .putExtra("go_description", go_description.getText().toString())
                            .putExtra("start_date", start_date_text.getText().toString())
                            .putExtra("end_date", end_date_text.getText().toString());
                    LocalBroadcastManager.getInstance(getParent()).sendBroadcast(goAdded);
                    finish();
                }
            }
        });

    }

    private void displayDates(){
        start_date_text.setText(format.format(start_date.getTime()));
        end_date_text.setText(format.format(end_date.getTime()));
    }

    private boolean checkDates(){
        boolean startAfterNow = start_date.after(Calendar.getInstance());
        boolean endAfterNow = end_date.after(Calendar.getInstance());
        boolean endAfterStart = end_date.after(start_date);
        boolean condition = startAfterNow && endAfterNow && endAfterStart;
        if(!condition)
            Toast.makeText(this,"Dates must be in the future, and end must be later than start", Toast.LENGTH_LONG).show();
        return condition;
    }

    private boolean checkAllFieldsRequired(){
        boolean condition = !(go_name.getText().toString().isEmpty()
                || go_description.getText().toString().isEmpty()
                || start_date_text.getText().toString().isEmpty()
                || end_date_text.getText().toString().isEmpty());
        if(!condition)
            Toast.makeText(this,"All fields are required", Toast.LENGTH_LONG).show();
        return condition;
    }
}

