package com.chevz.melapor.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import com.chevz.melapor.R;

public class SplashActivity extends Activity {

    private static final int SPLASH_TIMEOUT = 3000; // durasi 3 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // fullscreen tanpa status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        // Delay sebelum pindah ke LoginActivity
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish(); // tutup splash agar tidak bisa di-back
        }, SPLASH_TIMEOUT);
    }
}
