package com.chevz.melapor.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import com.chevz.melapor.R;
import com.chevz.melapor.data.network.ApiService;
import com.chevz.melapor.utils.DatabaseHelper;

import org.json.JSONObject;

public class RegisterActivity extends Activity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);

        EditText username = findViewById(R.id.editUsername);
        EditText password = findViewById(R.id.editPassword);
        EditText nama = findViewById(R.id.editNama);
        Button btnDaftar = findViewById(R.id.btnDaftar);

        btnDaftar.setOnClickListener(v -> {
            try {
                String userStr = username.getText().toString().trim();
                String passStr = password.getText().toString().trim();
                String namaStr = nama.getText().toString().trim();

                if (userStr.isEmpty() || passStr.isEmpty() || namaStr.isEmpty()) {
                    Toast.makeText(this, "Lengkapi semua field", Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONObject data = new JSONObject();
                data.put("username", userStr);
                data.put("password", passStr);
                data.put("nama", namaStr);
                data.put("level", "User");

                // Kirim ke Google Apps Script
                ApiService.postData("register", data, new ApiService.ApiCallback() {
                    @Override
                    public void onSuccess(String response) {
                        // Simpan ke SQLite lokal
                        boolean localSaved = dbHelper.insertUser(userStr, passStr, namaStr, "User");
                        if (localSaved) {
                            Toast.makeText(RegisterActivity.this, "Berhasil daftar & tersimpan lokal", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Daftar berhasil (online), tapi gagal simpan lokal", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(RegisterActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
