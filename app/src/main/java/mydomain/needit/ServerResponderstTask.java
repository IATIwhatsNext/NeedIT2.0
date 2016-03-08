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
public class ServerResponderstTask extends AsyncTask<MainActivity, String, List<Response>> {

    @Override
    protected void onPostExecute(List<Response> responses) {
        super.onPostExecute(responses);
        InMemoryDB.setResponses(responses);
        Log.w("!!!!", responses.toString());
    }

    @Override
    protected List<Response> doInBackground(MainActivity... params) {
        try {
            return parseUserJSON(ServerUtil.getFromServer("http://needit2.azurewebsites.net/api/user/Responses?id=" + "userID"));//todo:real user id
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Response> parseUserJSON(JSONArray jsonArray) {
        List<Response> result = new LinkedList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                result.add(new Response(
                        new UserLocation(jsonObject.getString("userId"), new LatLng(jsonObject.getLong("locationX"), jsonObject.getLong("locationY"))), jsonObject.getString("responseToUser")));
                Log.w("!!!!!", jsonObject.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
