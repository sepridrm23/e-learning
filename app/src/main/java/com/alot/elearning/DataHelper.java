package com.alot.elearning;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by afi on 26/03/18.
 */

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bkkbn.db";
    private static final int DATABASE_VERSION = 1;
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String sql1 = "create table tbl_materi(id_materi text null, nama_materi text null);";     + "\n"+"onCreate: " + sql2

        String sql = "create table tbl_materi(id_materi text null, nama_materi text null, id_detail_materi text null, isi_detail_materi text null, nama_detail_materi text null, jumlah_detail_materi text null, nomor_urut text null);";
        String sql2 = "create table tbl_soal(id_soal text null, nomor_soal text null, isi_soal text null, a text null, b text null, c text null, d text null, e text null);";
        String sql3 = "create table tbl_jawaban(id_soal text null, jawaban text null, kirim text null);";

        Log.d("Data", "onCreate: " + sql + " "+sql2+ " "+sql3);

        db.execSQL(sql);
        db.execSQL(sql2);
        db.execSQL(sql3);
    }

    public void onInsert(SQLiteDatabase db, String id_materi, String nama_materi, String id_detail_materi, String isi_detail_materi, String nama_detail_materi, String jumlah_detail_materi, String nomor_urut){
        String sql = "INSERT INTO tbl_materi(id_materi, nama_materi, id_detail_materi, isi_detail_materi, nama_detail_materi, jumlah_detail_materi, nomor_urut) VALUES ('"+id_materi+"','"+nama_materi+"','"+id_detail_materi+"','"+isi_detail_materi+"','"+nama_detail_materi+"','"+jumlah_detail_materi+"','"+nomor_urut+"');";
        db.execSQL(sql);
    }

    public void onInsertSoal(SQLiteDatabase db, String id_soal, String nomor_soal, String isi_soal, String a, String b, String c, String d, String e){
        String sql = "INSERT INTO tbl_soal(id_soal, nomor_soal, isi_soal, a, b, c, d, e) VALUES ('"+id_soal+"','"+nomor_soal+"','"+isi_soal+"','"+a+"','"+b+"','"+c+"','"+d+"','"+e+"');";
        db.execSQL(sql);
    }

    public void onInsertJawaban(SQLiteDatabase db, String id_soal, String jawaban){
        String sql = "INSERT INTO tbl_jawaban(id_soal, jawaban, kirim) VALUES ('"+id_soal+"','"+jawaban+"','0');";
        db.execSQL(sql);
    }

    public void onUpdateJawaban(SQLiteDatabase db, String id_soal, String jawaban){
        String sql = "update tbl_jawaban set jawaban='"+jawaban+"' where id_soal='"+id_soal+"'";
        db.execSQL(sql);
    }

    public void onKirimJawaban(SQLiteDatabase db, String id_soal){
        String sql = "update tbl_jawaban set kirim='1' where id_soal='"+id_soal+"'";
        db.execSQL(sql);
    }

    public void onDelete(SQLiteDatabase db, String idmateri){
        String sql = "delete from tbl_materi where id_materi='"+idmateri+"'";
        db.execSQL(sql);
    }

    public void onDeleteUjian(SQLiteDatabase db){
        String sql = "DELETE FROM tbl_soal";
        db.execSQL(sql);
        String sql2 = "DELETE FROM tbl_jawaban";
        db.execSQL(sql2);
    }

    public void onDeleteJawaban(SQLiteDatabase db){
        String sql = "DELETE FROM tbl_jawaban";
        db.execSQL(sql);
    }

    public void onDeleteAll(SQLiteDatabase db){
        String sql = "DELETE FROM tbl_soal";
        db.execSQL(sql);
        String sql2 = "DELETE FROM tbl_jawaban";
        db.execSQL(sql2);
        String sql3 = "DELETE FROM tbl_materi";
        db.execSQL(sql3);
    }

//
//    public void onDeleteAll(SQLiteDatabase db){
//        String sql = "delete from cart";
//        db.execSQL(sql);
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS tbl_materi");
        db.execSQL("DROP TABLE IF EXISTS tbl_soal");
        db.execSQL("DROP TABLE IF EXISTS tbl_jawaban");

        // create new tables
        onCreate(db);
    }
}
