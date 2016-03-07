package mydomain.needit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.support.v7.widget.SwitchCompat;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    protected boolean isAvailable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Switch availableIndicationSwitch = (Switch) findViewById(R.id.availableIndication);
        availableIndicationSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAvailable = isChecked;
            }
        });

        Button helpBtn = (Button) findViewById(R.id.helpBtn);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //should move to the help page
                Snackbar.make(view, "I Need HELP!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }
}
