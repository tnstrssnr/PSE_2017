package edu.kit.pse17.go_app.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

/**
 * Created by tina on 18.06.17.
 */

public abstract class SignInHelper extends AppCompatActivity {

    public static final String SIGN_IN_DATA_CODE = "signInData";
    public static final String ACCOUNT_DATA_CODE = "accountDataCode";

    protected static void signIn(Activity activity, int requestCode, Serializable signInData, Class loginHelper) {
        Intent intent = new Intent(activity, loginHelper);

        if (signInData != null) {
            intent.putExtra(SIGN_IN_DATA_CODE, signInData);
        }

        activity.startActivityForResult(intent, requestCode);
    }

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

}
