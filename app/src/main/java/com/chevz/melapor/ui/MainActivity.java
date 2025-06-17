package com.chevz.melapor.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;

import com.chevz.melapor.R;
import com.chevz.melapor.data.model.Laporan;
import com.chevz.melapor.data.network.ApiService;

import org.json.JSONObject;

public class MainActivity extends Activity {

    private EditText editNama, editJabatan, editPerusahaan, editKronologi;
    private Spinner spinnerJenis;
    private TextView textFileDipilih, textWelcome;
    private Uri selectedFileUri;
    private String fileUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNama = findViewById(R.id.editNama);
        editJabatan = findViewById(R.id.editJabatan);
        editPerusahaan = findViewById(R.id.editPerusahaan);
        editKronologi = findViewById(R.id.editKronologi);
        spinnerJenis = findViewById(R.id.spinnerJenis);
        textFileDipilih = findViewById(R.id.textFileDipilih);
        textWelcome = findViewById(R.id.textWelcome); // Optional TextView di layout
        Button btnPilihFile = findViewById(R.id.btnPilihFile);
        Button btnKirim = findViewById(R.id.btnKirim);

        // Ambil nama user dari intent login
        String namaUser = getIntent().getStringExtra("nama");
        if (namaUser != null) {
            textWelcome.setText("Selamat datang, " + namaUser);
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
            String kronologi = editKronologi.getText().toString();
            String jenis = spinnerJenis.getSelectedItem().toString();

            if (nama.isEmpty() || jabatan.isEmpty() || perusahaan.isEmpty() || kronologi.isEmpty()) {
                Toast.makeText(this, "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show();
                return;
            }

            Laporan laporan = new Laporan();
            laporan.nama = nama;
            laporan.jabatan = jabatan;
            laporan.perusahaan = perusahaan;
            laporan.jenisPengaduan = jenis;
            laporan.kronologi = kronologi;
            laporan.fileUrl = fileUrl; // Bisa dikembangkan untuk upload

            ApiService.kirimLaporan(laporan);

            Toast.makeText(this, "Laporan berhasil dikirim!", Toast.LENGTH_LONG).show();
        });
    }

    // Terima file yang dipilih
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            selectedFileUri = data.getData();
            String fileName = selectedFileUri.getLastPathSegment();
            fileUrl = selectedFileUri.toString(); // disimpan di string
            textFileDipilih.setText("File dipilih: " + fileName);
        }
    }
}
