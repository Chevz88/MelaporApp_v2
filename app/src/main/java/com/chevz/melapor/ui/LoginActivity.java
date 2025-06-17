package com.chevz.melapor.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import com.chevz.melapor.R;
import com.chevz.melapor.data.network.ApiService;
import org.json.JSONObject;

public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username = findViewById(R.id.editUsername);
        EditText password = findViewById(R.id.editPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView toRegister = findViewById(R.id.textToRegister);

        btnLogin.setOnClickListener(v -> {
            try {
                JSONObject data = new JSONObject();
                data.put("username", username.getText().toString());
                data.put("password", password.getText().toString());

                ApiService.postData("login", data, new ApiService.ApiCallback() {
                    public void onSuccess(String response) {
                        if (response.contains("LOGIN")) {
                            Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Gagal login", Toast.LENGTH_SHORT).show();
                        }
                    }
                    public void onError(String error) {
                        Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        toRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}
