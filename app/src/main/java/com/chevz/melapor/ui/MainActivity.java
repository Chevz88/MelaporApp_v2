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
    private String usernameIntent = "-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi SQLite
        dbHelper = new DatabaseHelper(this);

        // Ambil username dari intent login
        usernameIntent = getIntent().getStringExtra("username");
        String namaUser = dbHelper.getNamaByUsername(usernameIntent);

        // View binding
        textNamaUser = findViewById(R.id.textNamaUser);
        textNamaUser.setText("Login sebagai: " + namaUser);

        editNama = findViewById(R.id.editNama);
        editJabatan = findViewById(R.id.editJabatan);
        editPerusahaan = findViewById(R.id.editPerusahaan);
        editKronologi = findViewById(R.id.editKronologi);
        spinnerJenis = findViewById(R.id.spinnerJenis);
        textFileDipilih = findViewById(R.id.textFileDipilih);
        Button btnPilihFile = findViewById(R.id.btnPilihFile);
        Button btnKirim = findViewById(R.id.btnKirim);

        // Aksi pilih file
        btnPilihFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, 100);
        });

        // Aksi kirim laporan
        btnKirim.setOnClickListener(v -> {
            String nama = editNama.getText().toString();
            String jabatan = editJabatan.getText().toString();
            String perusahaan = editPerusahaan.getText().toString();
            String jenis = spinnerJenis.getSelectedItem().toString();
            String kronologi = editKronologi.getText().toString();
            String fileUrl = selectedFileUri != null ? selectedFileUri.toString() : "-";

            if (nama.isEmpty() || jabatan.isEmpty() || perusahaan.isEmpty() || kronologi.isEmpty()) {
                Toast.makeText(this, "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show();
                return;
            }

            // Simpan ke SQLite
            Laporan laporan = new Laporan(nama, jabatan, perusahaan, jenis, kronologi, fileUrl);
            boolean saved = dbHelper.insertLaporan(laporan);

            if (saved) {
                Toast.makeText(this, "Laporan berhasil disimpan", Toast.LENGTH_SHORT).show();
                clearForm();
            } else {
                Toast.makeText(this, "Gagal menyimpan laporan", Toast.LENGTH_SHORT).show();
            }

            // TODO: Sinkronisasi Google Sheet di tahap berikutnya
        });
    }

    // Terima hasil pilih file
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            selectedFileUri = data.getData();
            String fileName = selectedFileUri.getLastPathSegment();
            textFileDipilih.setText("File dipilih: " + fileName);
        }
    }

    // Reset isian form
    private void clearForm() {
        editNama.setText("");
        editJabatan.setText("");
        editPerusahaan.setText("");
        editKronologi.setText("");
        textFileDipilih.setText("File dipilih: -");
        spinnerJenis.setSelection(0);
        selectedFileUri = null;
    }
}
