package com.chevz.melapor.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import com.chevz.melapor.R;
import com.chevz.melapor.data.network.ApiService;
import com.chevz.melapor.utils.DatabaseHelper;

import org.json.JSONObject;

public class LoginActivity extends Activity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        EditText username = findViewById(R.id.editUsername);
        EditText password = findViewById(R.id.editPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView toRegister = findViewById(R.id.textToRegister);

        btnLogin.setOnClickListener(v -> {
            String userStr = username.getText().toString().trim();
            String passStr = password.getText().toString().trim();

            if (userStr.isEmpty() || passStr.isEmpty()) {
                Toast.makeText(this, "Username dan password wajib diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject data = new JSONObject();
                data.put("username", userStr);
                data.put("password", passStr);

                // Coba login online
                ApiService.postData("login", data, new ApiService.ApiCallback() {
                    @Override
                    public void onSuccess(String response) {
                        if (response.contains("LOGIN")) {
                            Toast.makeText(LoginActivity.this, "Login berhasil (online)", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            // Jika gagal login online → coba SQLite
                            if (dbHelper.checkLogin(userStr, passStr)) {
                                Toast.makeText(LoginActivity.this, "Login lokal berhasil", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Login gagal (online & lokal)", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(String error) {
                        // Offline atau error → cek lokal
                        if (dbHelper.checkLogin(userStr, passStr)) {
                            Toast.makeText(LoginActivity.this, "Login lokal berhasil (offline)", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login gagal & offline", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Kesalahan sistem", Toast.LENGTH_SHORT).show();
            }
        });

        toRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}
