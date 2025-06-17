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
    private TextView toRegister;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        toRegister = findViewById(R.id.textToRegister);

        // Aksi login
        btnLogin.setOnClickListener(v -> {
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Isi semua data", Toast.LENGTH_SHORT).show();
                return;
            }

            // Cek login di SQLite
            boolean isValid = dbHelper.checkLogin(username, password);

            if (isValid) {
                Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("username", username); // dikirim ke MainActivity
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Login gagal. Cek username dan password.", Toast.LENGTH_SHORT).show();
            }
        });

        // Aksi ke halaman registrasi
        toRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}
