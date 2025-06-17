
package com.chevz.melapor.data.network;

import android.util.Log;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RegisterService {
    public static void daftar(String username, String password, String nama, String level) {
        try {
            URL url = new URL("https://script.google.com/macros/s/YOUR_SCRIPT_ID/exec");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String data = "username=" + URLEncoder.encode(username, "UTF-8") +
                          "&password=" + URLEncoder.encode(password, "UTF-8") +
                          "&nama=" + URLEncoder.encode(nama, "UTF-8") +
                          "&level=" + URLEncoder.encode(level, "UTF-8");

            OutputStream os = conn.getOutputStream();
            os.write(data.getBytes());
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();
            Log.d("REGISTER", "Response: " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
