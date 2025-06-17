package com.chevz.melapor.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;

import com.chevz.melapor.R;
import com.chevz.melapor.data.model.Laporan;
import com.chevz.melapor.utils.DatabaseHelper;

public class MainActivity extends Activity {

    private EditText editNama, editJabatan, editPerusahaan, editKronologi;
    private Spinner spinnerJenis;
    private TextView textFileDipilih, textNamaUser;
    private Uri selectedFileUri;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init view
        editNama = findViewById(R.id.editNama);
        editJabatan = findViewById(R.id.editJabatan);
        editPerusahaan = findViewById(R.id.editPerusahaan);
        editKronologi = findViewById(R.id.editKronologi);
        spinnerJenis = findViewById(R.id.spinnerJenis);
        textFileDipilih = findViewById(R.id.textFileDipilih);
        textNamaUser = findViewById(R.id.textNamaUser);
        Button btnPilihFile = findViewById(R.id.btnPilihFile);
        Button btnKirim = findViewById(R.id.btnKirim);

        dbHelper = new DatabaseHelper(this);

        // Set nama user dari intent (jika login berhasil)
        String namaLogin = getIntent().getStringExtra("nama");
        if (namaLogin != null) {
            textNamaUser.setText("Login sebagai: " + namaLogin);
        }

        // Pilih file
        btnPilihFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, 100);
        });

        // Kirim laporan
        btnKirim.setOnClickListener(v -> {
            String nama = editNama.getText().toString();
            String jabatan = editJabatan.getText().toString();
            String perusahaan = editPerusahaan.getText().toString();
            String jenis = spinnerJenis.getSelectedItem().toString();
            String kronologi = editKronologi.getText().toString();
            String fileUrl = (selectedFileUri != null) ? selectedFileUri.toString() : "";

            if (nama.isEmpty() || jabatan.isEmpty() || perusahaan.isEmpty() || kronologi.isEmpty()) {
                Toast.makeText(this, "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show();
                return;
            }

            Laporan laporan = new Laporan(nama, jabatan, perusahaan, jenis, kronologi, fileUrl);
            boolean success = dbHelper.insertLaporan(laporan);

            if (success) {
                Toast.makeText(this, "Laporan berhasil disimpan", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Gagal menyimpan laporan", Toast.LENGTH_LONG).show();
            }
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
