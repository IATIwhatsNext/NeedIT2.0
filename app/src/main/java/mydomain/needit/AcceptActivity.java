package mydomain.needit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by dimitrke on 07/03/2016.
 */
public class AcceptActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent =  getIntent();
        new ServerPostResponseTask().execute(new Response(UserDetailsProvider.getUserLocation(), intent.getStringExtra("userId")));
        Intent userDetailsActivity = new Intent(this, UserDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userId", intent.getStringExtra("userId"));
        userDetailsActivity.putExtras(bundle);
        startActivity(userDetailsActivity);
        finish();
    }
}
