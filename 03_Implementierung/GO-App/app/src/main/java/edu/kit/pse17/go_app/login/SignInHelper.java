package edu.kit.pse17.go_app.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

/**
 * Abstrakte Klasse, die als Schablone für den Anmelde-Prozess ihrer Unterklassen dient
 *
 * Created by tina on 18.06.17.
 */

public abstract class SignInHelper extends AppCompatActivity {

    /**
     *  Name des Intent-Extra, das Anmeldedaten an die SignIn-Aktivität übergibt
     */
    public static final String SIGN_IN_DATA_CODE = "signInData";

    /**
     *  Name des Intent-Extra, das als Ergebnis der Anmelde-Aktivität zurückgegeben wird
     */
    public static final String ACCOUNT_DATA_CODE = "accountDataCode";

    /**
     * Schablonenmethode für den Anmelde-Prozess der konkreten SignInHelper
     * @param activity Aktivity, die die anmeldung aufruft
     * @param requestCode Request-Code des Aktivitäts-Aufrufs
     * @param signInData AnmeldeDaten die ggfs an Anmelde-Aktivität übergeben werden müssen
     * @param signinHelper  Referenz auf die Unterklasse, die Methode ausführt
     */
    public static void signIn(Activity activity, int requestCode, Serializable signInData, Class signinHelper) {
        Intent intent = new Intent(activity, signinHelper);

        if (signInData != null) {
            intent.putExtra(SIGN_IN_DATA_CODE, signInData);
        }

        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * gibt das Ergebnis der Anmelde-Aktivität an das aufrufende Objekt zurück
     * @param accountData Ergebnis der Anmelde-Aktivität
     */
    protected void returnActivityResult(Serializable accountData) {

        Intent intent = new Intent();

        if (accountData != null) {
            intent.putExtra(ACCOUNT_DATA_CODE, accountData);
            setResult(Activity.RESULT_OK, intent);
        } else {
            setResult(Activity.RESULT_CANCELED, intent);
        }
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startSignInProcess();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureSignIn();
    }

    /**
     * wird von Unterklassen implementiert und in Schablonenmethode aufgerufen
     */
    protected abstract void configureSignIn();

    /**
     * wird von Unterklassen implementiert und in Schablonenmethode aufgerufen
     */
    protected abstract void startSignInProcess();
}
