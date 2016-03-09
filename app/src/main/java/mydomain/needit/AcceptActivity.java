package mydomain.needit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by dimitrke on 07/03/2016.
 */
public class AcceptActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent =  getIntent();
        new ServerPostResponseTask().execute(new Response(UserDetailsProvider.getUserLocation(), intent.getStringExtra("userId"), intent.getStringExtra("userToken")));
        Intent userDetailsActivity = new Intent(this, UserDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userId", intent.getStringExtra("userId"));
        bundle.putString("userToken", intent.getStringExtra("userToken"));
        userDetailsActivity.putExtras(bundle);
        startActivity(userDetailsActivity);
        finish();
    }
}
