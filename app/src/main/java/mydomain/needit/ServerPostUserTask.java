package mydomain.needit;

import android.os.AsyncTask;

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
        try {
            jsonObject.put("userId", userLocation.getUserID());
            jsonObject.put("locationX", userLocation.getLocation().latitude);
            jsonObject.put("locationY", userLocation.getLocation().longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ServerUtil.sendHTTPData("http://needit2.azurewebsites.net/api/Users", jsonObject);
        return "";
    }

}
