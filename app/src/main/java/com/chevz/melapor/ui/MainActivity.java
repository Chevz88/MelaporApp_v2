package com.chevz.melapor.ui;

import android.app.Activity;
import android.content.Intent;
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
    private String namaUser;  // untuk menampung nama dari LoginActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ambil nama user dari intent login
        namaUser = getIntent().getStringExtra("nama");

        // Inisialisasi view
        editNama = findViewById(R.id.editNama);
        editJabatan = findViewById(R.id.editJabatan);
        editPerusahaan = findViewById(R.id.editPerusahaan);
        editKronologi = findViewById(R.id.editKronologi);
        spinnerJenis = findViewById(R.id.spinnerJenis);
        textFileDipilih = findViewById(R.id.textFileDipilih);
        textNamaUser = findViewById(R.id.textNamaUser);  // ID tambahan di XML
        Button btnPilihFile = findViewById(R.id.btnPilihFile);
        Button btnKirim = findViewById(R.id.btnKirim);

        dbHelper = new DatabaseHelper(this);

        // Tampilkan nama user
        if (namaUser != null) {
            textNamaUser.setText("Login sebagai: " + namaUser);
        }

        btnPilihFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, 100);
        });

        btnKirim.setOnClickListener(v -> {
            String nama = editNama.getText().toString().trim();
            String jabatan = editJabatan.getText().toString().trim();
            String perusahaan = editPerusahaan.getText().toString().trim();
            String kronologi = editKronologi.getText().toString().trim();
            String jenis = spinnerJenis.getSelectedItem().toString();
            String fileName = (selectedFileUri != null) ? selectedFileUri.getLastPathSegment() : "";

            if (nama.isEmpty() || jabatan.isEmpty() || perusahaan.isEmpty() || kronologi.isEmpty()) {
                Toast.makeText(this, "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show();
                return;
            }

            // Simpan ke SQLite
            Laporan laporan = new Laporan(nama, jabatan, perusahaan, jenis, kronologi, fileName);
            boolean inserted = dbHelper.insertLaporan(laporan);

            if (inserted) {
                Toast.makeText(this, "Laporan berhasil disimpan", Toast.LENGTH_SHORT).show();
                clearForm();
            } else {
                Toast.makeText(this, "Gagal menyimpan laporan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearForm() {
        editNama.setText("");
        editJabatan.setText("");
        editPerusahaan.setText("");
        editKronologi.setText("");
        spinnerJenis.setSelection(0);
        textFileDipilih.setText("File dipilih: -");
        selectedFileUri = null;
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
