package mydomain.needit;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Michal on 08/03/2016.
 */
public class ServerUtil {

    public static String sendHTTPData(String urlpath, JSONObject json) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlpath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
            streamWriter.write(json.toString());
            streamWriter.flush();
            StringBuilder stringBuilder = new StringBuilder();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response = null;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();

                Log.d("test", stringBuilder.toString());
                return stringBuilder.toString();
            } else {
                Log.e("test", connection.getResponseMessage());
                return null;
            }
        } catch (Exception exception) {
            Log.e("test", exception.toString());
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static JSONArray getFromServer(String urlString) throws IOException {
        try {
            URL url = new URL(urlString);
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
                return jsonArray;
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.w("test!", e.toString());
            return null;
        }
    }
}
