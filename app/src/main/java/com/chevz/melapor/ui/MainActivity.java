package com.chevz.melapor.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;

import com.chevz.melapor.R;
import com.chevz.melapor.model.Laporan;
import com.chevz.melapor.utils.DatabaseHelper;

public class MainActivity extends Activity {

    private EditText editNama, editJabatan, editPerusahaan, editKronologi;
    private Spinner spinnerJenis;
    private TextView textFileDipilih, textNamaUser;
    private Uri selectedFileUri;
    private DatabaseHelper dbHelper;
    private String loggedInUser = "-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);

        // Ambil data user dari intent
        if (getIntent() != null && getIntent().hasExtra("nama")) {
            loggedInUser = getIntent().getStringExtra("nama");
        }

        // Inisialisasi view
        textNamaUser = findViewById(R.id.textNamaUser);
        editNama = findViewById(R.id.editNama);
        editJabatan = findViewById(R.id.editJabatan);
        editPerusahaan = findViewById(R.id.editPerusahaan);
        editKronologi = findViewById(R.id.editKronologi);
        spinnerJenis = findViewById(R.id.spinnerJenis);
        textFileDipilih = findViewById(R.id.textFileDipilih);
        Button btnPilihFile = findViewById(R.id.btnPilihFile);
        Button btnKirim = findViewById(R.id.btnKirim);

        textNamaUser.setText("Login sebagai: " + loggedInUser);

        btnPilihFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, 100);
        });

        btnKirim.setOnClickListener(v -> {
            String nama = editNama.getText().toString();
            String jabatan = editJabatan.getText().toString();
            String perusahaan = editPerusahaan.getText().toString();
            String kronologi = editKronologi.getText().toString();
            String jenis = spinnerJenis.getSelectedItem().toString();
            String filePath = selectedFileUri != null ? selectedFileUri.toString() : "";

            if (nama.isEmpty() || jabatan.isEmpty() || perusahaan.isEmpty() || kronologi.isEmpty()) {
                Toast.makeText(this, "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show();
                return;
            }

            Laporan laporan = new Laporan(nama, jabatan, perusahaan, jenis, kronologi, filePath, loggedInUser);
            long result = dbHelper.insertLaporan(laporan);
            if (result > 0) {
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
