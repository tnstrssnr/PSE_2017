package edu.kit.pse17.go_app.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.iid.FirebaseInstanceId;

import edu.kit.pse17.go_app.commands.login.FirebaseSignInHelper;
import edu.kit.pse17.go_app.commands.login.GoSignInHelper;
import edu.kit.pse17.go_app.commands.login.SignInHelper;
import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.model.User;

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
        Log.d("ID", FirebaseInstanceId.getInstance().getToken());
        setContentView(R.layout.simple_firebase_login);
        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this);

        //check for compatible Google Play Services APK!

        signInBtn = (SignInButton) this.findViewById(R.id.sign_in_button);
        signInBtn.setOnClickListener(this);
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

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == FIREBASE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String[] accountData  = (String[]) data.getSerializableExtra(SignInHelper.ACCOUNT_DATA_CODE);
            String uid = accountData[0];
            String email = accountData[1];
            Toast.makeText(this, email, Toast.LENGTH_SHORT).show();

            //safe UID in local SharedPreferences!


        } else if (requestCode == GO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            User user = (User) data.getSerializableExtra(GoSignInHelper.ACCOUNT_DATA_CODE);

            GroupListActivity.start(this, user);

        }
    }
}
