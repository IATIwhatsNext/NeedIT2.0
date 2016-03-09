package mydomain.needit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executors;

import static android.graphics.BitmapFactory.*;

public class UserDetailsActivity extends AppCompatActivity {

    public String userFirstName;
    public String userLastName;
    public String userId;
    public String userToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        Intent intent = getIntent();
        userId = intent.getStringExtra("reqUserId");
        userToken = intent.getStringExtra("reqUserToken");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = getFacebookProfilePicture(userId);
                UserDetailsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageView userImage = (ImageView) findViewById(R.id.userPic);
                        userImage.setImageBitmap(bitmap);
                    }
                });
            }
        });
        if (userToken!= "null" && userToken.length() > 0) {
            String[] userInfo = userToken.split(";");
            if(userInfo.length ==3 ) {
                TextView faceLink = (TextView) findViewById(R.id.userLinkToFacebookTxt);
                faceLink.setText("Facebook profile link: " + userInfo[2]);
                TextView userNameTxt = (TextView) findViewById(R.id.userNameTxt);
                userNameTxt.setText("Name: "+ userInfo[1]);
            }
        }
    }

    public void onBackToMap(View v) {
        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivity);
    };

    public static Bitmap getFacebookProfilePicture(String userID) {
        Bitmap bitmap = null;
        URL imageURL;

        try {
            imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
            bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();

        }

        return bitmap;
    }


}



