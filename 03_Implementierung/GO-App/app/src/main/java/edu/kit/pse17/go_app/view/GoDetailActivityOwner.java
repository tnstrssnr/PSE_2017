package edu.kit.pse17.go_app.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import edu.kit.pse17.go_app.R;

/**
 * Created by tina on 20.06.17.
 */

public class GoDetailActivityOwner extends GoDetailActivity {

    private ImageView edit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        edit = (ImageView) findViewById(R.id.edit);

        edit.setVisibility(View.VISIBLE);
        edit.setClickable(true)
        ;
    }
}
