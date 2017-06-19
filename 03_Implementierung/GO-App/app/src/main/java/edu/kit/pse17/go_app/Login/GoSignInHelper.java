package edu.kit.pse17.go_app.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Die Klasse ist für die Anmeldung eines Beutzers am GO-Server zuständig.
 * Sie implemetiert die Methoden configureSignIn() und startSignInProcess() zur Schablonenmethode signIn() der Oberklasse SignInHelper.
 *
 * Created by tina on 17.06.17.
 */

public class GoSignInHelper extends SignInHelper {

    @Override
    protected void configureSignIn() {

    }

    @Override
    protected void startSignInProcess() {

    }

    /**
     * wird aufgerufen, falls ein Benutzer sich zum ersten Mal anmeldet --> es muss ein neuer Eintrag in der Db angelegt werden
     * @param uid User ID des FirebaseUser-Objekts des Benutzers
     * @param email E-mail des FirbaseUser-Objekts des Benutzers
     */
    private void createNewUser(String uid, String email) {

    }

    /**
     * bevor ein neuer Account erstellt wird, muss der Benutzer bestätigen, dass er mit der Standorterfassung einverstanden ist
     */
    private void onFirstSignIn() {
        //needs to show DialogBox, before creating new Account
    }

}
