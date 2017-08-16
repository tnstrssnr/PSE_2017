package edu.kit.pse17.go_app.login;


/**
 * Created by tina on 17.06.17.
 */

/**
 * Die Klasse ist für die Anmeldung eines Beutzers am Go-Server zuständig.
 * Sie implemetiert die Methoden configureSignOut() und startSignInProcess() zur Schablonenmethode signIn() der Oberklasse SignInHelper.
 */

public class GoSignInHelper extends SignInHelper {

    @Override
    protected void configureSignIn() {

    }

    @Override
    protected void startSignInProcess() {

    }

    /**
     * Diese Methode wird aufgerufen, falls ein Benutzer sich zum ersten Mal anmeldet --> es muss ein neuer Eintrag in der Db angelegt werden
     * @param uid User ID des FirebaseUser-Objekts des Benutzers
     * @param email E-mail des FirbaseUser-Objekts des Benutzers
     */
    private void createNewUser(String uid, String email) {

    }

    /**
     * Bevor ein neuer Account erstellt wird, muss der Benutzer bestätigen, dass er mit der Standorterfassung einverstanden ist
     */
    private void onFirstSignIn() {
        //needs to show DialogBox, before creating new Account
    }

}
