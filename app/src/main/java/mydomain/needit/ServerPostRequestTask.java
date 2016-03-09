package mydomain.needit;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Michal on 08/03/2016.
 */
public class ServerPostRequestTask extends AsyncTask<Request, String, String> {

    @Override
    protected String doInBackground(Request... params) {
        JSONObject jsonObject = new JSONObject();
        Request request = params[0];
        try {
            jsonObject.put("userId", request.getUserLocation().getUserID());
            jsonObject.put("locationX", request.getUserLocation().getLocation().latitude);
            jsonObject.put("locationY", request.getUserLocation().getLocation().longitude);
            jsonObject.put("request", request.getRequest());
            jsonObject.put("accessToken", request.getUserLocation().getAccessToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ServerUtil.sendHTTPData("http://needit2.azurewebsites.net/api/user/request", jsonObject);
        return "";
    }

}
