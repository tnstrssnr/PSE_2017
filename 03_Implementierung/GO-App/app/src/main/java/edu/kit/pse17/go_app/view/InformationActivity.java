package edu.kit.pse17.go_app.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import edu.kit.pse17.go_app.R;

/**
 * Diese Activity ist zuständig für die Darstellung eines Informationstextes.
 * Lizenz und About Seite werden damit dargestellt.
 */

public class InformationActivity extends BaseActivity {
    private String text;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        String intentCode = getIntent().getStringExtra(GroupListActivity.INFORMATION_ACTIVITY_CODE);
        if(intentCode.equals(GroupListActivity.ABOUT_ACTIVITY_CODE)) {
            text = getString(R.string.about_text);
        }
        else if(intentCode.equals(GroupListActivity.LICENSE_ACTIVITY_CODE)) {
            text = getString(R.string.license_text);
        }
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(text);

    }
}
