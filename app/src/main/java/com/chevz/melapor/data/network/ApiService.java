package com.chevz.melapor.data.network;

import android.os.AsyncTask;
import java.io.*;
import java.net.*;
import org.json.JSONObject;

public class ApiService {

    // Ganti dengan URL Google Apps Script kamu
    private static final String BASE_URL = "https://script.google.com/macros/s/AKfycbyB6UBbh3LW7QGltFAF3XJGA5YnZefUv38gQ7CG5CQ/dev";

    public interface ApiCallback {
        void onSuccess(String response);
        void onError(String error);
    }

    public static void postData(String action, JSONObject data, ApiCallback callback) {
        new AsyncTask<Void, Void, String>() {
            private String error;

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(BASE_URL + "?action=" + action);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    conn.setDoOutput(true);

                    OutputStream os = conn.getOutputStream();
                    os.write(data.toString().getBytes("UTF-8"));
                    os.close();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    reader.close();

                    return result.toString();
                } catch (Exception e) {
                    error = e.getMessage();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    callback.onSuccess(result);
                } else {
                    callback.onError(error);
                }
            }
        }.execute();
    }

    // Kirim laporan dari objek Laporan (opsional - bisa dipanggil dari MainActivity)
    public static void kirimLaporan(com.chevz.melapor.data.model.Laporan laporan, ApiCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put("nama", laporan.getNama());
            data.put("jabatan", laporan.getJabatan());
            data.put("perusahaan", laporan.getPerusahaan());
            data.put("jenis", laporan.getJenis());
            data.put("kronologi", laporan.getKronologi());
            data.put("fileUrl", laporan.getFileUrl());

            postData("kirim", data, callback);
        } catch (Exception e) {
            callback.onError(e.getMessage());
        }
    }
}
