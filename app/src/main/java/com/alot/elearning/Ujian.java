package com.alot.elearning;

public class Ujian {
    String id_soal, id_kelas, nomor_soal, isi_soal, a, b, c, d, e;

    public Ujian(String id_soal, String id_kelas, String nomor_soal, String isi_soal, String a, String b, String c, String d, String e){
        this.id_soal = id_soal;
        this.id_kelas = id_kelas;
        this.nomor_soal = nomor_soal;
        this.isi_soal = isi_soal;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;

    }

    public String getId_soal() {
        return id_soal;
    }

    public void setId_soal(String id_soal) {
        this.id_soal = id_soal;
    }

    public String getId_kelas() {
        return id_kelas;
    }

    public void setId_kelas(String id_kelas) {
        this.id_kelas = id_kelas;
    }

    public String getNomor_soal() {
        return nomor_soal;
    }

    public void setNomor_soal(String nomor_soal) {
        this.nomor_soal = nomor_soal;
    }

    public String getIsi_soal() {
        return isi_soal;
    }

    public void setIsi_soal(String isi_soal) {
        this.isi_soal = isi_soal;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }
}
