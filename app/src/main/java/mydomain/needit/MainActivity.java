package mydomain.needit;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    protected boolean isAvailable = false;


    /**
     * Send a  notification service.
     */
    public void startNotification(View view) {

        Intent intentService = new Intent(this, NotificationService.class);
        startService(intentService);
    }

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

        startNotification(this.getWindow().getDecorView().findViewById(android.R.id.content));
        Button helpBtn = (Button) findViewById(R.id.helpBtn);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //should move to the help page
                Snackbar.make(view, "I Need HELP!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intentService = new Intent(this, NotificationService.class);
        stopService(intentService);
    }
}
