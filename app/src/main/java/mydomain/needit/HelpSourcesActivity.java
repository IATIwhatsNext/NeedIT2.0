package mydomain.needit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HelpSourcesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_sources);
      //  getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("What help do you need?");
    }

    public enum HelpType {
        COCKROACH,CAR_CABLE,SUGAR,
     }

    public void onHelpButtonPress(View v) {
        String neededHelp ;
        switch (v.getId()) {
            case R.id.cockroachBtn:
                neededHelp = HelpType.COCKROACH.toString();
                break;
            case R.id.carServiceBtn:
               neededHelp = HelpType.CAR_CABLE.toString();
                break;
            case R.id.classSugarBtn:
                neededHelp = HelpType.SUGAR.toString();
                break;
        }

        //TODO call to server post new  help request
        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivity);
    }
}
