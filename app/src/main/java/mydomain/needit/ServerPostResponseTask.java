package mydomain.needit;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Michal on 08/03/2016.
 */
public class ServerPostResponseTask extends AsyncTask<Response, String, String> {

    @Override
    protected String doInBackground(Response... params) {
        JSONObject jsonObject = new JSONObject();
        Response request = params[0];
        try {
            jsonObject.put("userId", request.getUserLocation().getUserID());
            jsonObject.put("locationX", request.getUserLocation().getLocation().latitude);
            jsonObject.put("locationY", request.getUserLocation().getLocation().longitude);
            jsonObject.put("responseToUser", request.getResponseToUser());
            jsonObject.put("accessToken", request.getUserLocation().getAccessToken());
            jsonObject.put("responseToaccessToken", request.getResponseToaccessToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ServerUtil.sendHTTPData("http://needit2.azurewebsites.net/api/user/response", jsonObject);
        return "";
    }

}
