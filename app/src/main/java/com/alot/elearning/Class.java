package com.alot.elearning;

public class Class {
    String id_kelas, nama_kelas, jumlah_kelas, tgl_mulai, tgl_selesai, tgl_ujian, jumlah_kuota;

    public Class(String id_kelas, String nama_kelas, String jumlah_kelas, String tgl_mulai, String tgl_selesai, String tgl_ujian, String jumlah_kuota, String jumlah_kuota_terisa){
        this.id_kelas = id_kelas;
        this.nama_kelas = nama_kelas;
        this.jumlah_kelas = jumlah_kelas;
        this.tgl_mulai = tgl_mulai;
        this.tgl_selesai = tgl_selesai;
        this.tgl_ujian = tgl_ujian;
        this.jumlah_kuota = jumlah_kuota;
    }

    public String getId_kelas() {
        return id_kelas;
    }

    public void setId_kelas(String id_kelas) {
        this.id_kelas = id_kelas;
    }

    public String getNama_kelas() {
        return nama_kelas;
    }

    public void setNama_kelas(String nama_kelas) {
        this.nama_kelas = nama_kelas;
    }

    public String getJumlah_kelas() {
        return jumlah_kelas;
    }

    public void setJumlah_kelas(String jumlah_kelas) {
        this.jumlah_kelas = jumlah_kelas;
    }

    public String getTgl_mulai() {
        return tgl_mulai;
    }

    public void setTgl_mulai(String tgl_mulai) {
        this.tgl_mulai = tgl_mulai;
    }

    public String getTgl_selesai() {
        return tgl_selesai;
    }

    public void setTgl_selesai(String tgl_selesai) {
        this.tgl_selesai = tgl_selesai;
    }

    public String getTgl_ujian() {
        return tgl_ujian;
    }

    public void setTgl_ujian(String tgl_ujian) {
        this.tgl_ujian = tgl_ujian;
    }

    public String getJumlah_kuota() {
        return jumlah_kuota;
    }

    public void setJumlah_kuota(String jumlah_kuota) {
        this.jumlah_kuota = jumlah_kuota;
    }
}
