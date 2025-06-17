package com.chevz.melapor.data.model;

public class Laporan {
    private String nama;
    private String jabatan;
    private String perusahaan;
    private String jenis;
    private String kronologi;
    private String fileUrl;

    // Constructor kosong
    public Laporan() {
    }

    // Constructor lengkap
    public Laporan(String nama, String jabatan, String perusahaan, String jenis, String kronologi, String fileUrl) {
        this.nama = nama;
        this.jabatan = jabatan;
        this.perusahaan = perusahaan;
        this.jenis = jenis;
        this.kronologi = kronologi;
        this.fileUrl = fileUrl;
    }

    // Getters
    public String getNama() {
        return nama;
    }

    public String getJabatan() {
        return jabatan;
    }

    public String getPerusahaan() {
        return perusahaan;
    }

    public String getJenis() {
        return jenis;
    }

    public String getKronologi() {
        return kronologi;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    // Setters
    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public void setPerusahaan(String perusahaan) {
        this.perusahaan = perusahaan;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public void setKronologi(String kronologi) {
        this.kronologi = kronologi;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
