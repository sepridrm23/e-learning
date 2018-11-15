package com.alot.elearning;

public class User {
    String id_user, username, nip, nama, email, no_hp, no_hp_other, no_wa, no_rekening, nama_bank, provinsi, kabupaten, id_kelas, nama_kelas, id_angkatan, nama_angkatan, tgl_mulai, tgl_selesai, tgl_ujian, pretest;

    public User(){
    }

    public User(String id_user, String username, String nip, String nama, String email, String no_hp, String no_hp_other, String no_wa, String no_rekening, String nama_bank,
                String provinsi, String kabupaten, String id_kelas, String nama_kelas, String id_angkatan, String nama_angkatan, String tgl_mulai, String tgl_selesai, String tgl_ujian, String pretest){
        this.id_user = id_user;
        this.nip = nip;
        this.username = username;
        this.nama = nama;
        this.email = email;
        this.no_hp = no_hp;
        this.no_hp_other = no_hp_other;
        this.no_wa = no_wa;
        this.no_rekening = no_rekening;
        this.nama_bank = nama_bank;
        this.provinsi = provinsi;
        this.kabupaten = kabupaten;
        this.id_kelas = id_kelas;
        this.nama_kelas = nama_kelas;
        this.id_angkatan = id_angkatan;
        this.nama_angkatan = nama_angkatan;
        this.tgl_mulai = tgl_mulai;
        this.tgl_selesai = tgl_selesai;
        this.tgl_ujian = tgl_ujian;
        this.pretest = pretest;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getNo_hp_other() {
        return no_hp_other;
    }

    public void setNo_hp_other(String no_hp_other) {
        this.no_hp_other = no_hp_other;
    }

    public String getNo_wa() {
        return no_wa;
    }

    public void setNo_wa(String no_wa) {
        this.no_wa = no_wa;
    }

    public String getNo_rekening() {
        return no_rekening;
    }

    public void setNo_rekening(String no_rekening) {
        this.no_rekening = no_rekening;
    }

    public String getNama_bank() {
        return nama_bank;
    }

    public void setNama_bank(String nama_bank) {
        this.nama_bank = nama_bank;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
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

    public String getId_angkatan() {
        return id_angkatan;
    }

    public void setId_angkatan(String id_angkatan) {
        this.id_angkatan = id_angkatan;
    }

    public String getNama_angkatan() {
        return nama_angkatan;
    }

    public void setNama_angkatan(String nama_angkatan) {
        this.nama_angkatan = nama_angkatan;
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

    public String getPretest() {
        return pretest;
    }

    public void setPretest(String pretest) {
        this.pretest = pretest;
    }
}
