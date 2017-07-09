package edu.kit.pse17.go_app.login;

import android.content.Intent;
import android.support.annotation.NonNull;

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

import edu.kit.pse17.go_app.R;

/**
 * Created by tina on 17.06.17.
 */

/**
 * Diese Klasse ist für die Kommunikation mit Firebase und Google API während des Login-Prozesses zuständig.
 * Sie implementiert die Methoden configureSignIn() und startSignInProcess() zur Schablonenmethode signIn() der Oberklasse SignInHelper.
 *
 */

public class FirebaseSignInHelper extends SignInHelper implements GoogleApiClient.OnConnectionFailedListener {

    private static final int SIGN_IN_REQUEST_CODE = 9001;

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;

    /**
     * Implementierung gehört zur Schablonenmethode signIn()
     */
    @Override
    protected void configureSignIn() {
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
     * Implementierung gehört zur Schablonenmethode signIn()
     * Die Methode startet die signIn Aktivität der GoogleSignInApi
     */
    @Override
    protected void startSignInProcess() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) { // User already signed in
            returnActivityResult(new String[]{currentUser.getUid(), currentUser.getEmail()});
        } else { // User not signed in
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, SIGN_IN_REQUEST_CODE);
        }
    }

    /**
     * Methode erwartet das Resultat der signInAktivität der GoogleSignInApi. War die Aktivität erfolgreich,
     * wird die Authentifizierung mit Firebase gestartet.
     * @param requestCode Request Code, mit dem Aktivität gestartet wurde
     * @param resultCode Result Code der Aktivität
     * @param data Intent, den die Aktivität übergibt
     */
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
                            returnActivityResult(new String[]{user.getUid(), user.getEmail()});
                        } else {
                            returnActivityResult(null);
                        }
                    }
                });
    }

    /**
     * Diese Methode wird aufgerufen, falls Verbindung zu Google Play Services fehlschlägt
     *
     * @param connectionResult Ergebnis der fehlgeschlagenen Verbindung
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    /*
    * Diese Methode wird bei dem Sign Out aufgerufen. User credentials (wie Tokens) sind nach
    * der Ausführung nicht mehr gültig.
    * */
    public void signOut(){

    }

}
