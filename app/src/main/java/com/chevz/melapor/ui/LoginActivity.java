package com.chevz.melapor.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import com.chevz.melapor.R;
import com.chevz.melapor.utils.DatabaseHelper;

public class LoginActivity extends Activity {

    private EditText editUsername, editPassword;
    private Button btnLogin;
    private TextView textToRegister;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        textToRegister = findViewById(R.id.textToRegister);

        btnLogin.setOnClickListener(v -> {
            String username = editUsername.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username dan password harus diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            // Cek login di SQLite
            boolean isValidUser = dbHelper.checkUser(username, password);

            if (isValidUser) {
                Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Username atau password salah", Toast.LENGTH_SHORT).show();
            }
        });

        // Pindah ke halaman register
        textToRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}
