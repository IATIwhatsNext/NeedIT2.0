package mydomain.needit;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PostUtils.sendHTTPData("http://needit2.azurewebsites.net/api/user/response", jsonObject);
        return "";
    }

}
