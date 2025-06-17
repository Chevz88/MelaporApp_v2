package com.chevz.melapor.ui;

import android.app.Activity;
import android.content.Intent;
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
        TextView toLogin = findViewById(R.id.textToLogin);

        btnDaftar.setOnClickListener(v -> {
            try {
                JSONObject data = new JSONObject();
                data.put("username", username.getText().toString());
                data.put("password", password.getText().toString());
                data.put("nama", nama.getText().toString());
                data.put("level", "User");

                ApiService.postData("register", data, new ApiService.ApiCallback() {
                    public void onSuccess(String response) {
                        Toast.makeText(RegisterActivity.this, "Berhasil daftar", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    public void onError(String error) {
                        Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // ⬅️ Aksi pindah ke halaman login
        toLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
