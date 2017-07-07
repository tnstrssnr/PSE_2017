package edu.kit.pse17.go_app.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import edu.kit.pse17.go_app.R;

/**
 * Klasse dekoriert die GroupDetailActivity und fügt ihr die Admin-Funktionalitäten hinzu.
 *
 * Diese bestehen aus zwei zusätzlichen Schaltflächen, die einerseits die Änderungsansicht der Gruppe aufrufen ("edit"),
 * anderersetis gibt es eine zusätzliche Schaltfläche zum Hinzugügen eiens neuen Gruppenmitglieds ("addMember")
 *
 */

public class GroupDetailActivityOwner extends GroupDetailActivity {

    /**
     * Schaltfläche, bei deren Betätigung die EditGroupActivity gestartet wird.
     */
    private ImageView edit;

    /**
     * Schaltfläche, bei deren Betätigung ein DialogFragment zum Hinzufügen von Gruppenmitgliedern
     * öffnet.
     */
    private ImageView addMember; //onClick: Dialog mit add User oder add Admin

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        edit  = (ImageView) findViewById(R.id.edit);
        addMember = (ImageView) findViewById(R.id.add_member);

        edit.setVisibility(View.VISIBLE);
        edit.setClickable(true);

        addMember.setVisibility(View.VISIBLE);
        edit.setClickable(true);
    }
}
