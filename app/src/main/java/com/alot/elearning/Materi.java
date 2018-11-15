package com.alot.elearning;

public class Materi {
    String id_materi, id_kelas, nama_materi, check, jumlah_file;

    public Materi(String id_materi, String id_kelas, String nama_materi, String check, String jumlah_file){
        this.id_materi = id_materi;
        this.id_kelas = id_kelas;
        this.nama_materi = nama_materi;
        this.check = check;
        this.jumlah_file = jumlah_file;
    }

    public String getId_materi() {
        return id_materi;
    }

    public void setId_materi(String id_materi) {
        this.id_materi = id_materi;
    }

    public String getId_kelas() {
        return id_kelas;
    }

    public void setId_kelas(String id_kelas) {
        this.id_kelas = id_kelas;
    }

    public String getNama_materi() {
        return nama_materi;
    }

    public void setNama_materi(String nama_materi) {
        this.nama_materi = nama_materi;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getJumlah_file() {
        return jumlah_file;
    }

    public void setJumlah_file(String jumlah_file) {
        this.jumlah_file = jumlah_file;
    }
}
