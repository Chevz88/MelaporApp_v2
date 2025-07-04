package com.chevz.melapor.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import com.chevz.melapor.R;
import com.chevz.melapor.utils.DatabaseHelper;

public class RegisterActivity extends Activity {

    private EditText editUsername, editPassword, editNama;
    private Button btnDaftar;
    private TextView textToLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editNama = findViewById(R.id.editNama);
        btnDaftar = findViewById(R.id.btnDaftar);
        textToLogin = findViewById(R.id.textToLogin);

        btnDaftar.setOnClickListener(v -> {
            String username = editUsername.getText().toString().trim();
            String password = editPassword.getText().toString().trim();
            String nama = editNama.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || nama.isEmpty()) {
                Toast.makeText(this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show();
                return;
            }

            // Simpan user ke SQLite
            boolean isInserted = dbHelper.insertUser(username, password, nama, "User");

            if (isInserted) {
                Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                finish(); // kembali ke Login
            } else {
                Toast.makeText(this, "Gagal mendaftar (mungkin username sudah digunakan)", Toast.LENGTH_SHORT).show();
            }
        });

        // Aksi pindah ke halaman login
        textToLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
