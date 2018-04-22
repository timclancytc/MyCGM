package projects.tmc.mycgm;

import android.app.DownloadManager;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DexcomDataFetcher {

    private static final String TAG = "DexcomDataFetcher";

//    //Sandbox
//    private static final String BaseUrl = "https://sandbox-api.dexcom.com";

    //Production
    private static final String BaseUrl = "https://api.dexcom.com";

    private static final String API_KEY = "JolxaxR4NTAVkDfaPrEKwU4b7i2n9GX4";


    public void calibrationEventFetcher(Date startDate, Date endDate) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.dexcom.com/v1/users/self/calibrations?startDate=2017-06-16T08:00:00&endDate=2017-06-17T08:00:00")
                .get()
                .addHeader("authorization", "Bearer {your_access_token}")
                .build();

        Response response = client.newCall(request).execute();
    }

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }

            out.close();
            return out.toByteArray();

        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<EventCalibration> fetchCalibrationEvents(){

        List<EventCalibration> items = new ArrayList<>();

        try {
            String url = Uri.parse("https://api.flickr.com/services/rest/")
                    .buildUpon()
                    .appendQueryParameter("method", "flickr.photos.getRecent")
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .appendQueryParameter("extras", "url_s")
                    .build().toString();

            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON:" + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseCalibrationEvent(items, jsonBody);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items:" + ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
            je.printStackTrace();
        }

        return items;
    }

    private void parseCalibrationEvent(List<EventCalibration> items, JSONObject jsonBody)
            throws IOException, JSONException, ParseException {
        JSONObject calibrationsJsonObject = jsonBody.getJSONObject("calibrations");
        JSONArray calibrationsJsonArray = calibrationsJsonObject.getJSONArray("");

        for (int i = 0; i < calibrationsJsonArray.length(); i++) {
            JSONObject calibrationJsonObject = calibrationsJsonArray.getJSONObject(i);

            EventCalibration item = new EventCalibration();
            String systemDateString = calibrationJsonObject.getString("systemTime");
            item.setSystemTime(DateFormat.getDateInstance().parse(systemDateString));
            String displayDateString = calibrationJsonObject.getString("displayTime");
            item.setDisplayTime(DateFormat.getDateInstance().parse(displayDateString));
            item.setUnit(calibrationJsonObject.getString("unit"));
            item.setValue(calibrationJsonObject.getInt("value"));
        }
    }

}
