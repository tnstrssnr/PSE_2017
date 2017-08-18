package edu.kit.pse17.go_app.view;

import android.arch.lifecycle.LifecycleActivity;

/**
 * Die Base-Activity ist Oberklasse für alle weiteren Activities und kümmert sich um Funktionalität,
 * die alle Activities gemeinsam haben.
 *
 * BaseActivity erbt von LifecycleActivty, was eine Lifecycle-Owner Klasse ist. Dies erlaubt es Objekte, die Lifecycle-Aware sind
 * (z.B. LiveData-Objekten) den Lifecycle der Activity zu beobachten und je nach Zustand der Activity ein entsprechendes UI-Update zu triggern.
 */

public class BaseActivity extends LifecycleActivity {

    //TODO implement
    // handle ProgressDialogs
    public Class getNextActivity(){
        return null;
    };




}
