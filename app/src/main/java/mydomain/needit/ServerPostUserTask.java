package mydomain.needit;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Michal on 08/03/2016.
 */
public class ServerPostUserTask extends AsyncTask<UserLocation, String, String> {

    @Override
    protected String doInBackground(UserLocation... params) {
        JSONObject jsonObject = new JSONObject();

        UserLocation userLocation = params[0];
        Log.e("sendUser", "sending user " + params[0]);
        try {
            jsonObject.put("userId", userLocation.getUserID());
            jsonObject.put("locationX", userLocation.getLocation().latitude);
            jsonObject.put("locationY", userLocation.getLocation().longitude);
            jsonObject.put("accessToken", userLocation.getAccessToken());
        } catch (JSONException e) {
            Log.e("JSON", "could not send user " + jsonObject);
            e.printStackTrace();
        }
        Log.w("!!!!!", "sending: " + jsonObject.toString());
        ServerUtil.sendHTTPData("http://needit2.azurewebsites.net/api/Users", jsonObject);
        return "";
    }

}
