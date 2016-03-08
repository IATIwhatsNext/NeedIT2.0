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
            return getRequestsFromServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Request> getRequestsFromServer() throws IOException {
        try {
            URL url = new URL("http://needit2.azurewebsites.net/api/user/requests");
            Log.w("test!", "here");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            Log.w("test!", "connected");
            try {
                Log.w("test!", "trying");

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                Log.w("test!", "read data: " + bufferedReader.toString());
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                Log.w("test!", stringBuilder.toString());
                bufferedReader.close();
                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                return parseUserJSON(jsonArray);
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.w("test!", e.toString());
            return null;
        }
    }

    public static List<Request> parseUserJSON(JSONArray jsonArray) {
        List<Request> result = new LinkedList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                result.add(new Request(new UserLocation(jsonObject.getString("userId"),
                                                        new LatLng(jsonObject.getLong("locationX"),
                                                        jsonObject.getLong("locationY"))),
                                jsonObject.getString("response")));
                Log.w("test", jsonObject.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
