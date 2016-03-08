package mydomain.needit;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Michal on 08/03/2016.
 */
public class ServerUsersTask extends AsyncTask<MainActivity, String, List<UserLocation>> {
    private MainActivity activity;

    @Override
    protected void onPostExecute(List<UserLocation> stringLatLngMap) {
        super.onPostExecute(stringLatLngMap);
        Log.w("!!!!", stringLatLngMap.toString());
        activity.updateMap(stringLatLngMap);
    }

    @Override
    protected List<UserLocation> doInBackground(MainActivity... params) {
        activity = params[0];
        try {
            return getUsersFromServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<UserLocation> getUsersFromServer() throws IOException {
        try {
            URL url = new URL("http://needit2.azurewebsites.net/api/user");
            Log.w("!!!!!", "here");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            Log.w("!!!!!", "connected");
            try {
                Log.w("!!!!!", "trying");

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                Log.w("!!!!!", "read data: " + bufferedReader.toString());
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                Log.w("!!!!!", stringBuilder.toString());
                bufferedReader.close();
                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                return parseUserJSON(jsonArray);
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.w("!!!!!", e.toString());
            return null;
        }
    }

    public static List<UserLocation> parseUserJSON(JSONArray jsonArray) {
        List<UserLocation> result = new LinkedList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                result.add(new UserLocation(jsonObject.getString("userID"), new LatLng(jsonObject.getLong("locationX"), jsonObject.getLong("locationY"))));
                Log.w("!!!!!", jsonObject.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
