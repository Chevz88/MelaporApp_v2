package com.chevz.melapor.data.model;

public class Laporan {
    private String nama;
    private String jabatan;
    private String perusahaan;
    private String jenisMasalah;
    private String kronologi;
    private String fileUri;

    public Laporan(String nama, String jabatan, String perusahaan, String jenisMasalah, String kronologi, String fileUri) {
        this.nama = nama;
        this.jabatan = jabatan;
        this.perusahaan = perusahaan;
        this.jenisMasalah = jenisMasalah;
        this.kronologi = kronologi;
        this.fileUri = fileUri;
    }

    // Getter & Setter
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(String perusahaan) {
        this.perusahaan = perusahaan;
    }

    public String getJenisMasalah() {
        return jenisMasalah;
    }

    public void setJenisMasalah(String jenisMasalah) {
        this.jenisMasalah = jenisMasalah;
    }

    public String getKronologi() {
        return kronologi;
    }

    public void setKronologi(String kronologi) {
        this.kronologi = kronologi;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }
}
