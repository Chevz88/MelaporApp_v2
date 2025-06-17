package com.chevz.melapor.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;

import com.chevz.melapor.R;
import com.chevz.melapor.data.model.Laporan;
import com.chevz.melapor.data.network.ApiService;

import org.json.JSONObject;

public class MainActivity extends Activity {

    private EditText editJabatan, editPerusahaan, editKronologi;
    private Spinner spinnerJenis;
    private TextView textFileDipilih, textNamaUser;
    private Uri selectedFileUri;
    private String namaUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ambil nama dari SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        namaUser = prefs.getString("nama", "Pengguna");

        // Inisialisasi view
        textNamaUser = findViewById(R.id.textNamaUser);
        editJabatan = findViewById(R.id.editJabatan);
        editPerusahaan = findViewById(R.id.editPerusahaan);
        editKronologi = findViewById(R.id.editKronologi);
        spinnerJenis = findViewById(R.id.spinnerJenis);
        textFileDipilih = findViewById(R.id.textFileDipilih);
        Button btnPilihFile = findViewById(R.id.btnPilihFile);
        Button btnKirim = findViewById(R.id.btnKirim);

        // Tampilkan nama
        textNamaUser.setText("Halo, " + namaUser);

        // Pilih file
        btnPilihFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, 100);
        });

        // Kirim laporan
        btnKirim.setOnClickListener(v -> {
            String jabatan = editJabatan.getText().toString();
            String perusahaan = editPerusahaan.getText().toString();
            String kronologi = editKronologi.getText().toString();
            String jenis = spinnerJenis.getSelectedItem().toString();
            String fileUrl = (selectedFileUri != null) ? selectedFileUri.toString() : "";

            if (jabatan.isEmpty() || perusahaan.isEmpty() || kronologi.isEmpty()) {
                Toast.makeText(this, "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject data = new JSONObject();
                data.put("nama", namaUser);
                data.put("jabatan", jabatan);
                data.put("perusahaan", perusahaan);
                data.put("jenis_pengaduan", jenis);
                data.put("kronologi", kronologi);
                data.put("fileUrl", fileUrl);

                ApiService.postData("lapor", data, new ApiService.ApiCallback() {
                    public void onSuccess(String response) {
                        Toast.makeText(MainActivity.this, "Laporan berhasil dikirim", Toast.LENGTH_SHORT).show();
                        clearForm();
                    }

                    public void onError(String error) {
                        Toast.makeText(MainActivity.this, "Gagal mengirim laporan", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Menangkap file dari galeri / dokumen
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (reqCode == 100 && resultCode == RESULT_OK && data != null) {
            selectedFileUri = data.getData();
            String fileName = selectedFileUri.getLastPathSegment();
            textFileDipilih.setText("File dipilih: " + fileName);
        }
    }

    // Reset form
    private void clearForm() {
        editJabatan.setText("");
        editPerusahaan.setText("");
        editKronologi.setText("");
        textFileDipilih.setText("Belum ada file dipilih");
    }
}
