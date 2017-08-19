package edu.kit.pse17.go_app.view;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.model.entities.User;
import edu.kit.pse17.go_app.view.recyclerView.ListAdapter;
import edu.kit.pse17.go_app.view.recyclerView.OnListItemClicked;
import edu.kit.pse17.go_app.view.recyclerView.listItems.GroupListItem;
import edu.kit.pse17.go_app.view.recyclerView.listItems.ListItem;
import edu.kit.pse17.go_app.viewModel.GroupListViewModel;
import edu.kit.pse17.go_app.viewModel.UserViewModel;

/**
 *  Hauptansicht der App. Zeigt alle Gruppen eines Benutzers in einer
 *  RecyclerView
 */

public class GroupListActivity extends BaseActivity implements View.OnClickListener, OnListItemClicked {

    private static final String USER_ID_INTENT_CODE = "user_id";
    private static final int SIGN_OUT_CODE = 1;
    public static String INFORMATION_ACTIVITY_CODE = "code";
    private static String DIALOG_TAG = "dialog_tag";
    public static String ABOUT_ACTIVITY_CODE = "about";
    public static String LICENSE_ACTIVITY_CODE = "license";

    GoogleApiClient mGoogleApiClient;
    private ListAdapter adapter;
    private FloatingActionButton addGroup;
    private ImageView settings;
    private RecyclerView groupList;
    public static Drawable default_group_icon;
    public static Drawable default_user_icon;
    public static Drawable default_admin_icon;
    public static Drawable default_go_icon;
    public static Drawable user_going_icon;
    public static Drawable user_not_going_icon;
    public static Drawable user_gone_icon;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private ActionBar actionBar;
    private GroupAddedBroadcastReceiver receiver;
    //activity which is started upon clicking an element in recyclerview
    private Class nextActivity = GroupDetailActivity.class;

    private GroupListViewModel viewModel;
    private boolean permission_granted = false;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private TextView locoloco;

    private String uid;
    private static String globalUid;
    private static String globalEmail;

    public static void start(Activity activity, User user) {
        Intent intent = new Intent(activity, GroupListActivity.class);

        intent.putExtra(USER_ID_INTENT_CODE, user.getUid());
        globalUid = user.getUid();
        globalEmail = user.getEmail();
        activity.startActivity(intent);
    }

    /**
     * RecyclerView und passender Listadapter werden erzeugt
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_list_activity);
        configureGoogleClient();

        initializeIconsFromResources();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.heading);
        toolbarTitle.setText("Your Groups");
        toolbarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to group details
            }
        });
        setActionBar(toolbar);

        groupList = (RecyclerView) findViewById(R.id.group_recycler);
        groupList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //uid = getIntent().getStringExtra(USER_ID_INTENT_CODE);

        viewModel = ViewModelProviders.of(this).get(GroupListViewModel.class);
        viewModel.init(getUserId());
        viewModel.getGroups(globalUid, globalEmail,"null").observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(@Nullable List<Group> groups) {
                Log.d("LIVE DATA", "ON CHANGE CALLBACK");
                displayData(groups);
            }
        });
        addGroup = (FloatingActionButton) findViewById(R.id.addGroupBtn);
        addGroup.setOnClickListener(this);

        createLocationRequest();
        seupLocationCallback();
        startPollingDeviceLocation();

    }

    private void initializeIconsFromResources() {
        default_group_icon = getDrawable(R.drawable.ic_group_blue_24dp);
        default_go_icon = getDrawable(R.drawable.go96);
        default_user_icon = getDrawable(R.drawable.ic_person_outline_black_24dp);
        default_admin_icon = getDrawable(R.drawable.ic_person_outline_red_24dp);
        user_not_going_icon = getDrawable(R.drawable.ic_person_outline_red_24dp);
        user_going_icon = getDrawable(R.drawable.ic_person_outline_green_24dp);
        user_gone_icon = getDrawable(R.drawable.ic_person_outline_blue_24dp);
    }

    /*
    *
    * */
    private void startPollingDeviceLocation() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                checkPermissionsAndRequestLocations();
            }
        });
    }
    /*
    * method to save the last location, which was sent to the device
    * */
    private void seupLocationCallback() {
        mLocationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                //TODO uncomment when server is up
                // HERE WE GET THE LOCATION OF THE DEVICE AND PASS IT ON
                viewModel.updateLocation(new LatLng(locationResult.getLastLocation().getLatitude(),
                                                    locationResult.getLastLocation().getLongitude()));
            }
        };
    }

    private void checkPermissionsAndRequestLocations() {
        if (ActivityCompat.checkSelfPermission(GroupListActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(GroupListActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            ActivityCompat.requestPermissions(GroupListActivity.this, permissions, 1);
        } else {
            //permission was granted
            permission_granted = true;
        }

        if(permission_granted) {
            LocationServices.getFusedLocationProviderClient(GroupListActivity.this).requestLocationUpdates(mLocationRequest,mLocationCallback , null);
        }
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(8000);
        mLocationRequest.setFastestInterval(6000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new GroupAddedBroadcastReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("group_added"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        mLocationCallback = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if(item.getItemId() ==  R.id.about_menu_item){
            startActivity(new Intent(this, InformationActivity.class)
                    .putExtra(INFORMATION_ACTIVITY_CODE, ABOUT_ACTIVITY_CODE));
        }
        else if(item.getItemId() == R.id.license_menu_item){
            startActivity(new Intent(this, InformationActivity.class)
                    .putExtra(INFORMATION_ACTIVITY_CODE, LICENSE_ACTIVITY_CODE));
        }
        else if(item.getItemId() == R.id.log_out_button){
            logOut();

        } else if(item.getItemId() == R.id.delete_profile_button){
            UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
            String userId = getSharedPreferences(getString(R.string.shared_pref_name),MODE_PRIVATE).getString(getString(R.string.user_id),null);
            if(userId == null) {
                throw new NullPointerException();
            }
            userViewModel.deleteUser(userId);
            logOut();
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void displayData(List<Group> groups) {
        List<ListItem> data = new ArrayList<>();

        for(Group group: groups) {
            ListItem<Integer> item = new GroupListItem(group);
            data.add(item);
        }

        adapter = new ListAdapter(data, null);
        groupList.setAdapter(adapter);
    }

    /**
     * ClickListener für addGroupButton
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.addGroupBtn) {
            //TODO: MAYBE CHANGE TO SUPPORT FRAGMENT MANAGER, BUT IT DOESN'T COMPILE NOW
            Log.d("addGroupBtn", "I am pressed");
            FragmentManager fm = getFragmentManager();
            AddGroupDialogFragment addGroupDialogFragment = new AddGroupDialogFragment();
            addGroupDialogFragment.show(fm, DIALOG_TAG);
        }
    }

    /**
     * ClickListener für RecyclerView-Elemente
     *
     * @param position Position des ListItems, auf das geklickt wurde
     */
    @Override
    public void onItemClicked(Activity activity, int position) {
        Log.d("CLICKED", "onItemClicked");
    }

    private class GroupAddedBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            Group toCreate = new Group();
            toCreate.setName(intent.getStringExtra("group_name"));
            toCreate.setDescription(intent.getStringExtra("group_description"));
            viewModel.createGroup(toCreate);
            Log.d("Received broadcast", intent.getAction() + toCreate.getName());
        }
    }
    public Class getNextActivity() {
        return nextActivity;
    }

    private void configureGoogleClient(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(LocationServices.API)
                .build();

    }

    private void logOut() {
        SharedPreferences preferences = getSharedPreferences(getString(R.string.shared_pref_name), MODE_PRIVATE);
        if (preferences.contains("uid")) {
            preferences.edit().clear().commit();
            //sign out of firebase
            FirebaseAuth.getInstance().signOut();
            //sign out of google
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            //start the app like new
            startActivity(new Intent(this, SignInActivity.class));
            this.finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for(int i : grantResults){
            if(i == PackageManager.PERMISSION_GRANTED){
                //some location permission was granted
                permission_granted = true;
                checkPermissionsAndRequestLocations();
                break;
            }else{
                permission_granted = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GroupListActivity.this.finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setMessage("You must allow the app to access device's location" +
                        " in your device's settings for the app to function correctly." +
                        " Or delete the apps data in settings and start the app again");
                dialog.show();
            }
        }
    }

    public static String getUserId(){
        return globalUid;
    }

}
