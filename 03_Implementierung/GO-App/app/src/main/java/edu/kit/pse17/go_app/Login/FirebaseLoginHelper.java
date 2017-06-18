package edu.kit.pse17.go_app.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.Serializable;

import edu.kit.pse17.go_app.R;

/**
 * Diese Klasse ist für die Kommunikation mit der Firebase und Goofle API zuständig
 * Created by tina on 17.06.17.
 */

public class FirebaseLoginHelper extends LoginHelper implements GoogleApiClient.OnConnectionFailedListener {

    private static final int SIGN_IN_REQUEST_CODE = 9001;

    /**
     * Name des String-Extras des Intents des Activity Results
     */
    public static final String UID_CODE = "uid";

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * startet die Aktivität
     */
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) { // User already signed in
            returnActivityResult(currentUser.getUid());
        } else { // User not signed in
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, SIGN_IN_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_REQUEST_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                firebaseAuth(acct);
            } else {
                returnActivityResult(null);
            }
        }
    }

    private void firebaseAuth(GoogleSignInAccount acct) {
        AuthCredential cred = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(cred)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            returnActivityResult(user.getUid());
                        } else {
                            returnActivityResult(null);
                        }
                    }
                });
    }

    /**
     * wird aufgerufen, falls Verbindung zu google Play Services fehlschlägt
     *
     * @param connectionResult Ergebnis der fehlgeschlagenen Verbindung
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
