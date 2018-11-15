package com.alot.elearning;

public class Jawaban {
    String idsoal, jawaban;

    Jawaban(String idsoal, String jawaban){
        this.idsoal = idsoal;
        this.jawaban = jawaban;
    }

    public String getIdsoal() {
        return idsoal;
    }

    public void setIdsoal(String idsoal) {
        this.idsoal = idsoal;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }

}
