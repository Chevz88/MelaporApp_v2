package com.chevz.melapor.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import com.chevz.melapor.R;
import com.chevz.melapor.data.local.DatabaseHelper;
import com.chevz.melapor.data.model.Laporan;

public class MainActivity extends Activity {

    private EditText editNama, editJabatan, editPerusahaan, editKronologi;
    private Spinner spinnerJenis;
    private TextView textFileDipilih, textNamaUser;
    private Uri selectedFileUri;
    private DatabaseHelper dbHelper;
    private String namaUserLogin = "-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ambil nama user dari login
        if (getIntent().hasExtra("nama")) {
            namaUserLogin = getIntent().getStringExtra("nama");
        }

        textNamaUser = findViewById(R.id.textNamaUser);
        textNamaUser.setText("Login sebagai: " + namaUserLogin);

        // Inisialisasi UI
        editNama = findViewById(R.id.editNama);
        editJabatan = findViewById(R.id.editJabatan);
        editPerusahaan = findViewById(R.id.editPerusahaan);
        editKronologi = findViewById(R.id.editKronologi);
        spinnerJenis = findViewById(R.id.spinnerJenis);
        textFileDipilih = findViewById(R.id.textFileDipilih);
        Button btnPilihFile = findViewById(R.id.btnPilihFile);
        Button btnKirim = findViewById(R.id.btnKirim);

        // Init DB
        dbHelper = new DatabaseHelper(this);

        // Aksi pilih file
        btnPilihFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, 100);
        });

        // Aksi kirim
        btnKirim.setOnClickListener(v -> {
            String nama = editNama.getText().toString().trim();
            String jabatan = editJabatan.getText().toString().trim();
            String perusahaan = editPerusahaan.getText().toString().trim();
            String kronologi = editKronologi.getText().toString().trim();
            String jenis = spinnerJenis.getSelectedItem().toString();
            String filePath = (selectedFileUri != null) ? selectedFileUri.toString() : "-";

            if (nama.isEmpty() || jabatan.isEmpty() || perusahaan.isEmpty() || kronologi.isEmpty()) {
                Toast.makeText(this, "Lengkapi semua data", Toast.LENGTH_SHORT).show();
                return;
            }

            // Simpan ke database SQLite
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("INSERT INTO laporan (nama, jabatan, perusahaan, jenis, kronologi, fileUrl) VALUES (?, ?, ?, ?, ?, ?)",
                    new Object[]{nama, jabatan, perusahaan, jenis, kronologi, filePath});

            Toast.makeText(this, "Laporan berhasil disimpan", Toast.LENGTH_LONG).show();

            // Reset form
            editNama.setText("");
            editJabatan.setText("");
            editPerusahaan.setText("");
            editKronologi.setText("");
            textFileDipilih.setText("File dipilih: -");
            spinnerJenis.setSelection(0);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            selectedFileUri = data.getData();
            String fileName = selectedFileUri.getLastPathSegment();
            textFileDipilih.setText("File dipilih: " + fileName);
        }
    }
}
