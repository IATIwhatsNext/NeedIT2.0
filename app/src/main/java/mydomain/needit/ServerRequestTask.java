package mydomain.needit;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Michal on 08/03/2016.
 */
public class ServerRequestTask extends AsyncTask<MainActivity, String, List<Request>> {
    private MainActivity activity;

    @Override
    protected void onPostExecute(List<Request> requests) {
        super.onPostExecute(requests);
        InMemoryDB.setRequests(requests);
        Log.w("test", requests.toString());

    }

    @Override
    protected List<Request> doInBackground(MainActivity... params) {
        try {
            return parseUserJSON(ServerUtil.getFromServer("http://needit2.azurewebsites.net/api/user/requests"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<Request> parseUserJSON(JSONArray jsonArray) {
        List<Request> result = new LinkedList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                result.add(new Request(new UserLocation(jsonObject.getString("userId"),
                        new LatLng(jsonObject.getDouble("locationX"),
                                jsonObject.getDouble("locationY"))),
                        jsonObject.getString("request")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
