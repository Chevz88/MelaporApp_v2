package com.chevz.melapor.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import com.chevz.melapor.R;
import com.chevz.melapor.data.local.DatabaseHelper;

public class LoginActivity extends Activity {

    private EditText editUsername, editPassword;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView toRegister = findViewById(R.id.textToRegister);

        dbHelper = new DatabaseHelper(this);

        btnLogin.setOnClickListener(v -> {
            String userStr = editUsername.getText().toString().trim();
            String passStr = editPassword.getText().toString().trim();

            if (userStr.isEmpty() || passStr.isEmpty()) {
                Toast.makeText(this, "Username dan Password wajib diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isValid = dbHelper.checkLogin(userStr, passStr);

            if (isValid) {
                Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("nama", dbHelper.getNamaByUsername(userStr)); // mengirim nama ke MainActivity
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Login gagal. Periksa kembali data anda", Toast.LENGTH_LONG).show();
            }
        });

        toRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }
}
