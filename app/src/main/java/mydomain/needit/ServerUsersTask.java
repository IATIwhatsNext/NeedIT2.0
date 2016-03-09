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
public class ServerUsersTask extends AsyncTask<MainActivity, String, List<UserLocation>> {

    @Override
    protected void onPostExecute(List<UserLocation> users) {
        super.onPostExecute(users);
        Log.w("!!!!", users.toString());
        InMemoryDB.setUserLocations(users);
    }

    @Override
    protected List<UserLocation> doInBackground(MainActivity... params) {
        try {
            return parseUserJSON(ServerUtil.getFromServer("http://needit2.azurewebsites.net/api/user"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<UserLocation> parseUserJSON(JSONArray jsonArray) {
        List<UserLocation> result = new LinkedList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                result.add(new UserLocation(jsonObject.getString("userId"), new LatLng(jsonObject.getDouble("locationX"), jsonObject.getDouble("locationY"))));
                Log.w("!!!!!", jsonObject.getString("userId"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
