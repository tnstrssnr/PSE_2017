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

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.viewModel.GoViewModel;

/**
 * This class was created to implement Add Go action as an activity and not as a pop-up dialog as prior to that
 * because you can make a cleaner and more pleasant user interface inside an activity than in a dialog.
 */

public class EditGoActivity extends BaseActivity implements View.OnClickListener {

    public static final String REQUEST_INTET_CODE = "request_code";
    public static final int EDIT_REQUEST = 100;
    public static final int ADD_REQUEST = 200;
    public static final int BACK_PRESSED_RESULT = -1;
    private int currentRequest;
    private int PLACE_PICKER_REQUEST = 1;

    private EditText go_name;
    private EditText go_description;
    private TextView start_date_text;
    private Calendar start_date;
    private TextView end_date_text;
    private Calendar end_date;
    private TextView locationText;

    private DateFormat format = new SimpleDateFormat();


    private Button start_date_button;
    private Button start_time_button;
    private Button end_date_button;
    private Button end_time_button;
    private Button performAddGo;
    private Button pickLocation;
    private TextView heading;
    private LatLng destination;
    private Go currentGo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_go_activity);

        heading = (TextView) findViewById(R.id.heading);

        currentRequest = getIntent().getIntExtra(REQUEST_INTET_CODE, -1);
        if(currentRequest == EDIT_REQUEST){
            heading.setText("Edit GO");
        } else if(currentRequest == ADD_REQUEST){
            heading.setText("Add GO");
        }
        currentGo = GoViewModel.getInstance().getGo().getValue();
        go_name = (EditText) findViewById(R.id.go_name);
        go_description = (EditText) findViewById(R.id.go_description);
        start_date_text = (TextView) findViewById(R.id.start_date_text);
        locationText = (TextView) findViewById(R.id.locationText);

        end_date_text = (TextView) findViewById(R.id.end_date_text);
        start_date_button = (Button) findViewById(R.id.start_date_button);
        end_date_button = (Button)findViewById(R.id.end_date_button);
        start_time_button = (Button) findViewById(R.id.start_time_button);
        end_time_button = (Button) findViewById(R.id.end_time_button);
        performAddGo = (Button) findViewById(R.id.perform_add_go);
        pickLocation = (Button) findViewById(R.id.pickLocationButton);


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

                if(checkAllFieldsRequired() && checkDates() && checkLocation()){
                    //if true then all fields passed the check
                    Intent intent = new Intent().setAction(getString(R.string.go_added_intent_action));
                    intent.putExtra("go_name", go_name.getText().toString())
                            .putExtra("go_description", go_description.getText().toString())
                            .putExtra("start_date", start_date_text.getText().toString())
                            .putExtra("end_date", end_date_text.getText().toString())
                            .putExtra("lat", destination.latitude)
                            .putExtra("lng", destination.longitude);
                    if(currentRequest == ADD_REQUEST) {
                        LocalBroadcastManager.getInstance(getParent()).sendBroadcast(intent);
                    } else if(currentRequest == EDIT_REQUEST){
                        setResult(0, intent);
                    }
                    finish();
                }
            }
        });
        // we pass this as a parameter, because we do not want to hold a reference to the activity, because it is a bad practice
        // and the method needs Activity instance
        pickLocation.setOnClickListener(this);
        if(currentRequest == EDIT_REQUEST){
            insertGoDataInUI();
            Toast.makeText(this, "You must enter both dates and destination again:(", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertGoDataInUI() {
        go_name.setText(currentGo.getName());
        go_description.setText(currentGo.getDescription());
    }

    //method for catching the PlacePicker result
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this,data);
                locationText.setText(place.getName());
                destination = place.getLatLng();
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }
    private void displayDates(){
        start_date_text.setText(format.format(start_date.getTime()));
        end_date_text.setText(format.format(end_date.getTime()));
    }

    private boolean checkLocation() {
        if(locationText.getText().toString().equals(getString(R.string.choose_your_location))){
            Toast.makeText(this,"You should pick a destination place", Toast.LENGTH_LONG).show();
            return false;
        } else{
            //if not Choose Location then it was overwritten by a location
            return true;
        }
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

    @Override
    public void onClick(View v) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(BACK_PRESSED_RESULT);
        finish();
    }
}

