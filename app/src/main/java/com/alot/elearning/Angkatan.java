package com.alot.elearning;

public class Angkatan {
    String id_angkatan, id_kelas, nama_angkatan, jumlah_kuota, jumlah_kuota_terisa;

    public Angkatan(String id_angkatan, String id_kelas, String nama_angkatan, String jumlah_kuota, String jumlah_kuota_terisa){
        this.id_angkatan = id_angkatan;
        this.id_kelas = id_kelas;
        this.nama_angkatan = nama_angkatan;
        this.jumlah_kuota = jumlah_kuota;
        this.jumlah_kuota_terisa = jumlah_kuota_terisa;
    }

    public String getId_angkatan() {
        return id_angkatan;
    }

    public void setId_angkatan(String id_angkatan) {
        this.id_angkatan = id_angkatan;
    }

    public String getId_kelas() {
        return id_kelas;
    }

    public void setId_kelas(String id_kelas) {
        this.id_kelas = id_kelas;
    }

    public String getNama_angkatan() {
        return nama_angkatan;
    }

    public void setNama_angkatan(String nama_angkatan) {
        this.nama_angkatan = nama_angkatan;
    }

    public String getJumlah_kuota() {
        return jumlah_kuota;
    }

    public void setJumlah_kuota(String jumlah_kuota) {
        this.jumlah_kuota = jumlah_kuota;
    }

    public String getJumlah_kuota_terisa() {
        return jumlah_kuota_terisa;
    }

    public void setJumlah_kuota_terisa(String jumlah_kuota_terisa) {
        this.jumlah_kuota_terisa = jumlah_kuota_terisa;
    }
}
