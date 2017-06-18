package edu.kit.pse17.go_app.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.User;

/**
 * Die Klasse zeigt dem User den Login-Screen an und koordiniert den LogIn Prozess
 * Created by tina on 17.06.17.
 */

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int FIREBASE_REQUEST_CODE = 1;
    private static final int GO_REQUEST_CODE = 2;

    private SignInButton signInBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_firebase_login);

        signInBtn = (SignInButton) this.findViewById(R.id.sign_in_button);
        signInBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == this.signInBtn.getId()) {
            FirebaseLoginHelper.signIn(this, FIREBASE_REQUEST_CODE, null, FirebaseLoginHelper.class);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == FIREBASE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String uid = (String) data.getSerializableExtra(LoginHelper.ACCOUNT_DATA_CODE);
            Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();

        } else if (requestCode == GO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            User user = (User) data.getSerializableExtra(GoLoginHelper.USER_CODE);
        }
    }
}
