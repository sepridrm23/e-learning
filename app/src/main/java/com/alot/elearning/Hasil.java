package com.alot.elearning;

public class Hasil {
    String no, id_user, id_angkatan, nama_kelas, nama_angkatan, nama, kabupaten, provinsi, nilai_pos, nilai_pre, nilai;
    public Hasil(String no, String id_user, String id_angkatan, String nama, String kabupaten, String provinsi, String nilai, String nama_angkatan, String nama_kelas, String nilai_pos, String nilai_pre){
        this.no = no;
        this.id_angkatan = id_angkatan;
        this.id_user = id_user;
        this.nama = nama;
        this.kabupaten = kabupaten;
        this.provinsi = provinsi;
        this.nilai = nilai;
        this.nama_angkatan = nama_angkatan;
        this.nama_kelas = nama_kelas;
        this.nilai_pos = nilai_pos;
        this.nilai_pre = nilai_pre;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_angkatan() {
        return id_angkatan;
    }

    public void setId_angkatan(String id_angkatan) {
        this.id_angkatan = id_angkatan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    public String getNama_kelas() {
        return nama_kelas;
    }

    public void setNama_kelas(String nama_kelas) {
        this.nama_kelas = nama_kelas;
    }

    public String getNama_angkatan() {
        return nama_angkatan;
    }

    public void setNama_angkatan(String nama_angkatan) {
        this.nama_angkatan = nama_angkatan;
    }

    public String getNilai_pos() {
        return nilai_pos;
    }

    public void setNilai_pos(String nilai_pos) {
        this.nilai_pos = nilai_pos;
    }

    public String getNilai_pre() {
        return nilai_pre;
    }

    public void setNilai_pre(String nilai_pre) {
        this.nilai_pre = nilai_pre;
    }
}
