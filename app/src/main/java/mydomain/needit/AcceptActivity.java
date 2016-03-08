package mydomain.needit;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by dimitrke on 07/03/2016.
 */
public class AcceptActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        new ServerPostResponseTask().execute(new Response(new UserLocation()));
        //todo: popup user details
        System.out.println("AcceptActivity loaded..... ");
    }
}
