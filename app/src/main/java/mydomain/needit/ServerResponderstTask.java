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
            return getUserResponses("userID");//todo:real user id
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Response> getUserResponses(String userID) throws IOException {
        try {
            URL url = new URL("http://needit2.azurewebsites.net/api/user/Responses?id=" + userID);
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
