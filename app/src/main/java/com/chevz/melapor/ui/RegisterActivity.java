package com.chevz.melapor.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import com.chevz.melapor.R;
import com.chevz.melapor.data.network.ApiService;
import org.json.JSONObject;

public class RegisterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText username = findViewById(R.id.editUsername);
        EditText password = findViewById(R.id.editPassword);
        EditText nama = findViewById(R.id.editNama);
        Button btnDaftar = findViewById(R.id.btnDaftar);

        btnDaftar.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();
            String name = nama.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Semua kolom wajib diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            btnDaftar.setEnabled(false); // mencegah klik ganda

            try {
                JSONObject data = new JSONObject();
                data.put("username", user);
                data.put("password", pass);
                data.put("nama", name);
                data.put("level", "User");

                ApiService.postData("register", data, new ApiService.ApiCallback() {
                    @Override
                    public void onSuccess(String response) {
                        btnDaftar.setEnabled(true);
                        Toast.makeText(RegisterActivity.this, "Berhasil daftar", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(String error) {
                        btnDaftar.setEnabled(true);
                        Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                btnDaftar.setEnabled(true);
                Toast.makeText(this, "Terjadi kesalahan: " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
    }
}
