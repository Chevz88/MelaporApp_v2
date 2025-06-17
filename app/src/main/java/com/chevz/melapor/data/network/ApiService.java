package com.chevz.melapor.data.network;

import android.os.AsyncTask;
import org.json.JSONObject;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiService {
    public interface ApiCallback {
        void onSuccess(String response);
        void onError(String error);
    }

    public static void postData(String route, JSONObject data, ApiCallback callback) {
        new AsyncTask<Void, Void, String>() {
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://script.google.com/macros/s/AKfycbxq2gq4NZ_k_NVV4xRyVWHh3LMusBI1I8FozIs1PXow07D8jRjliYQEAhgp1jbUhXxu/exec?route=" + route);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);
                    OutputStream os = conn.getOutputStream();
                    os.write(data.toString().getBytes("UTF-8"));
                    os.close();
                    java.util.Scanner s = new java.util.Scanner(conn.getInputStream()).useDelimiter("\\A");
                    return s.hasNext() ? s.next() : "";
                } catch (Exception e) {
                    return "ERROR: " + e.getMessage();
                }
            }
            protected void onPostExecute(String result) {
                if (result.startsWith("ERROR:")) {
                    callback.onError(result);
                } else {
                    callback.onSuccess(result);
                }
            }
        }.execute();
    }
}
