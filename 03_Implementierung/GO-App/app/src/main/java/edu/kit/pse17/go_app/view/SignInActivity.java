package edu.kit.pse17.go_app.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.login.FirebaseSignInHelper;
import edu.kit.pse17.go_app.login.GoSignInHelper;
import edu.kit.pse17.go_app.login.SignInHelper;
import edu.kit.pse17.go_app.model.entities.User;

/**
 * Die Klasse stellt die View für den LogIn- und SignIn-Prozess bereit.
 */

public class SignInActivity extends BaseActivity implements View.OnClickListener {

    private static final int FIREBASE_REQUEST_CODE = 1;
    private static final int GO_REQUEST_CODE = 2;

    private SignInButton signInBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d("ID", FirebaseInstanceId.getInstance().getToken());
        String token = FirebaseInstanceId.getInstance().getToken();
        //Bundle extras = getIntent().getExtras();
        //Set<String> set = extras.keySet();

        if(getSharedPreferences(getString(R.string.shared_pref_name), MODE_PRIVATE).contains("uid")){
            GroupListActivity.start(this, retrieveUserDataFromSharedPreferences());
            this.finish();
        } else {
        setContentView(R.layout.simple_firebase_login);
        //Log.d("REACHED","");
        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this);

        //check for compatible Google Play Services APK!

        signInBtn = (SignInButton) this.findViewById(R.id.sign_in_button);
        signInBtn.setOnClickListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //check for compatible GPS APK
    }

    /**
     * Click-Listener, der auf Klicken des Signin Buttons wartet --> SignIn wird gestartet
     * @param v geklickter View
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == this.signInBtn.getId()) {
            FirebaseSignInHelper.signIn(this, FIREBASE_REQUEST_CODE, null, FirebaseSignInHelper.class);
            FirebaseSignInHelper.signIn(this, GO_REQUEST_CODE, null, FirebaseSignInHelper.class);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == FIREBASE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String[] accountData  = (String[]) data.getSerializableExtra(SignInHelper.ACCOUNT_DATA_CODE);
            String uid = accountData[0];
            String email = accountData[1];
            String displayName = accountData[2];
            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
            Toast.makeText(this, email, Toast.LENGTH_SHORT).show();

            //save UID in local SharedPreferences!
            getSharedPreferences(getString(R.string.shared_pref_name), MODE_PRIVATE).edit()
                    .putString(getString(R.string.user_id), uid).putString(getString(R.string.user_email),email).putString(getString(R.string.user_display_name), displayName).apply();

        } else if (requestCode == GO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String[] accountData = (String[]) data.getSerializableExtra(GoSignInHelper.ACCOUNT_DATA_CODE);
            User toCreate = new User(accountData[0], accountData[1], accountData[2]);
            //UserRepository.getInstance().createUser(toCreate);
            GroupListActivity.start(this, new User(accountData[0], accountData[1], accountData[2]));
            this.finish();
        }
    }

    private User retrieveUserDataFromSharedPreferences(){
        SharedPreferences prefs = getSharedPreferences(getString(R.string.shared_pref_name), MODE_PRIVATE);
        String uid = prefs.getString(getString(R.string.user_id), null);
        String email = prefs.getString(getString(R.string.user_email),null);
        String displayName = prefs.getString(getString(R.string.user_display_name),null);
        return new User(uid, email, displayName);
    }
}
