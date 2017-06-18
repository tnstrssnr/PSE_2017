package edu.kit.pse17.go_app.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import edu.kit.pse17.go_app.User;

/**
 * Die Klasse ist für die Anmeldung eines Beutzers am GO-Server zuständig
 * Created by tina on 17.06.17.
 */

public class GoLoginHelper extends AppCompatActivity {

    /**
     * RequestCode für SignIn-Aktivität
     */
    public static final String USER_CODE = "user";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Starts SignIn process to the GO System and retrieves user information from database
     * @param activity Activity, from which Signin process was started
     * @param requestCode RequestCode for the Activity
     * @param uid The User ID of the user to sign in
     */
    protected static void signIn(Activity activity, int requestCode, String uid) {
        //TODO implement
    }

    private void returnActivityResult(User user) {
        Intent intent = new Intent();
        intent.putExtra(USER_CODE, user);
        setResult(Activity.RESULT_OK);
        finish();
    }
}
