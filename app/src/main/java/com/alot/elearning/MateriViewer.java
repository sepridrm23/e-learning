package com.alot.elearning;

public class MateriViewer {
    String id_detailmateri, isi_materi, nama_materi, no_urut;

    public MateriViewer(String id_detailmateri, String isi_materi, String nama_materi, String no_urut){
        this.id_detailmateri = id_detailmateri;
        this.isi_materi = isi_materi;
        this.nama_materi = nama_materi;
        this.no_urut = no_urut;
    }

    public String getId_detailmateri() {
        return id_detailmateri;
    }

    public void setId_detailmateri(String id_detailmateri) {
        this.id_detailmateri = id_detailmateri;
    }

    public String getIsi_materi() {
        return isi_materi;
    }

    public void setIsi_materi(String isi_materi) {
        this.isi_materi = isi_materi;
    }

    public String getNama_materi() {
        return nama_materi;
    }

    public void setNama_materi(String nama_materi) {
        this.nama_materi = nama_materi;
    }

    public String getNo_urut() {
        return no_urut;
    }

    public void setNo_urut(String no_urut) {
        this.no_urut = no_urut;
    }
}
