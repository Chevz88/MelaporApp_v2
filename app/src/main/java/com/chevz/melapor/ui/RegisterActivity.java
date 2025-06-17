package com.chevz.melapor.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import com.chevz.melapor.R;
import com.chevz.melapor.data.local.DatabaseHelper;

public class RegisterActivity extends Activity {

    private EditText editUsername, editPassword, editNama;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editNama     = findViewById(R.id.editNama);
        Button btnDaftar = findViewById(R.id.btnDaftar);

        dbHelper = new DatabaseHelper(this);

        btnDaftar.setOnClickListener(v -> {
            String username = editUsername.getText().toString().trim();
            String password = editPassword.getText().toString().trim();
            String nama     = editNama.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || nama.isEmpty()) {
                Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean localSaved = dbHelper.insertUser(username, password, nama, "User");

            if (localSaved) {
                Toast.makeText(this, "Berhasil daftar", Toast.LENGTH_SHORT).show();
                finish(); // Kembali ke Login
            } else {
                Toast.makeText(this, "Gagal menyimpan ke database", Toast.LENGTH_LONG).show();
            }
        });
    }
}
