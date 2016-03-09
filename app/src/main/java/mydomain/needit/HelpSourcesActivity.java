package mydomain.needit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HelpSourcesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_sources);
        getSupportActionBar().setTitle(R.string.help_sources_title);
    }

    public enum HelpType {
        COCKROACH, CAR_CABLE, SUGAR, CHILDREN ,MOVING
    }

    public void onHelpButtonPress(View v) {
        String neededHelp = "";
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
            case R.id.childrenBtn:
                neededHelp = HelpType.CHILDREN.toString();
                break;
            case R.id.moveBtn:
                neededHelp = HelpType.MOVING.toString();
                break;
            default:
                neededHelp = ""+ v.getId();
                break;
        }

        //TODO call to server post new  help request - do we have access to location etc?
        new ServerPostRequestTask().execute(new Request(UserDetailsProvider.getUserLocation(), neededHelp)); //todo: need to send real location and ID

        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("backgroundForHelpBtn", R.drawable.im_ok);

        mainActivity.putExtras(bundle); //Put your id
        startActivity(mainActivity);
    }
}
