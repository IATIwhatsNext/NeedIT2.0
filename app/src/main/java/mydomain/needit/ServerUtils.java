package mydomain.needit;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Michal on 08/03/2016.
 */
public class ServerUtils {
    public static Map<String, LatLng> getUsersFromServer() throws IOException {
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

    public static Map<String, LatLng> parseUserJSON(JSONArray jsonArray) {
        Map<String, LatLng> result = new HashMap<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                result.put(jsonObject.getString("userId"), new LatLng(jsonObject.getLong("locationX"), jsonObject.getLong("locationY")));
                Log.w("!!!!!", jsonObject.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}

