package com.alot.elearning;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UjianActivity extends AppCompatActivity {
    Button btnUnduh, btnIsi, btnKirim;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ujian);
        btnUnduh = findViewById(R.id.btnUnduhUjian);
        btnIsi = findViewById(R.id.btnIsiUjian);
        btnKirim = findViewById(R.id.btnKirimUjian);
        Button btnHasil = findViewById(R.id.btnHasilUjian);

//        final DataHelper dbHelper = new DataHelper(UjianActivity.this);
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM tbl_soal", null);
//        cursor.moveToFirst();
//        if (cursor.getCount() == 0) {
//            btnUnduh.setText("Unduh Soal Ujian");
//        }else {
//            btnUnduh.setText("Soal Ujian Telah Diunduh");
//        }

        btnUnduh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHelper dbHelper = new DataHelper(UjianActivity.this);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM tbl_soal", null);
                cursor.moveToFirst();
                if (!user.getId_angkatan().isEmpty()) {
                    if (cursor.getCount() == 0) {
                        if (Utilities.isNetworkAvailable(UjianActivity.this)) {
                            Date date_now = new Date();
                            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                            Date date_mulai = new Date();
                            try {
                                date_mulai = format.parse(user.getTgl_mulai());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if (date_now.before(date_mulai)) {
                                Snackbar.make(findViewById(android.R.id.content), "Diklat Teknis dimulai pada tanggal " + user.getTgl_mulai(),
                                        Snackbar.LENGTH_LONG).show();
                            }else {
                                GetSoal(user.getId_user(), user.getId_kelas(), user.getId_angkatan(), user.getTgl_ujian());
                            }
                        }else {
                            Snackbar.make(findViewById(android.R.id.content), "Tidak ada koneksi internet",
                                    Snackbar.LENGTH_LONG).show();
                        }
                    }else {
                        Snackbar.make(findViewById(android.R.id.content), "Soal ujian telah diunduh",
                                Snackbar.LENGTH_LONG).show();
                    }
                }else {
                    Snackbar.make(findViewById(android.R.id.content), "Anda belum mengikuti Diklat Teknis manapun. Silahkan ikuti salah satu Diklat Teknis",
                            Snackbar.LENGTH_LONG).show();
                }
            }
        });

        btnIsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!user.getId_angkatan().isEmpty()) {
                    if (user.getPretest().equals("1")) {
                        Date date_now = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        Date date_ujian = new Date();
                        Date date_selesai = new Date();
                        try {
                            date_ujian = format.parse(user.getTgl_ujian());
                            date_selesai = format.parse(user.getTgl_selesai());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

//                    Log.e("date", user.getTgl_ujian()+" | "+date_ujian+" | "+date_selesai+" | "+user.getTgl_selesai()+" | "+date_now);

                        if (date_now.before(date_ujian)) {
                            Snackbar.make(findViewById(android.R.id.content), "Ujian dilakukan pada tanggal " + user.getTgl_ujian() + " - " + user.getTgl_selesai(),
                                    Snackbar.LENGTH_LONG).show();
                        } else if (date_now.after(date_selesai)) {
                            Snackbar.make(findViewById(android.R.id.content), "Ujian telah selesai",
                                    Snackbar.LENGTH_LONG).show();
                        } else {
//                        DataHelper dbHelper = new DataHelper(UjianActivity.this);
//                        SQLiteDatabase db = dbHelper.getReadableDatabase();
//                        Cursor cursor = db.rawQuery("SELECT * FROM tbl_soal", null);
//                        cursor.moveToFirst();
                            DataHelper dbHelper = new DataHelper(UjianActivity.this);
                            SQLiteDatabase db = dbHelper.getReadableDatabase();
                            Cursor cursor = db.rawQuery("SELECT * FROM tbl_soal", null);
                            cursor.moveToFirst();
                            if (cursor.getCount() == 0) {
                                Snackbar.make(findViewById(android.R.id.content), "Belum ada soal di unduh. Silahkan unduh soal terlebih dahulu",
                                        Snackbar.LENGTH_LONG).show();
                            } else {
                                Intent intent = new Intent(UjianActivity.this, SoalActivity.class);
//                                intent.putExtra("FLAG", "1");
                                startActivity(intent);
                            }
                        }
                    }else {
                        DataHelper dbHelper = new DataHelper(UjianActivity.this);
                        SQLiteDatabase db = dbHelper.getReadableDatabase();
                        Cursor cursor = db.rawQuery("SELECT * FROM tbl_soal", null);
                        cursor.moveToFirst();
                        if (cursor.getCount() == 0) {
                            Snackbar.make(findViewById(android.R.id.content), "Belum ada soal di unduh. Silahkan unduh soal terlebih dahulu",
                                    Snackbar.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(UjianActivity.this, SoalActivity.class);
//                            intent.putExtra("FLAG", "1");
                            startActivity(intent);
                        }
                    }
                }else {
                    Snackbar.make(findViewById(android.R.id.content), "Anda belum mengikuti Diklat Teknis manapun. Silahkan ikuti salah satu Diklat Teknis",
                            Snackbar.LENGTH_LONG).show();
                }
            }
        });

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!user.getId_angkatan().isEmpty()) {
                    if (user.getPretest().equals("1")) {
                        Date date_now = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        Date date_ujian = new Date();
                        Date date_selesai = new Date();
                        try {
                            date_ujian = format.parse(user.getTgl_ujian());
                            date_selesai = format.parse(user.getTgl_selesai());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (date_now.before(date_ujian)) {
                            Snackbar.make(findViewById(android.R.id.content), "Ujian dilakukan pada tanggal " + user.getTgl_ujian() + " - " + user.getTgl_selesai(),
                                    Snackbar.LENGTH_LONG).show();
                        } else if (date_now.after(date_selesai)) {
                            Snackbar.make(findViewById(android.R.id.content), "Ujian telah selesai",
                                    Snackbar.LENGTH_LONG).show();
                        } else {
//                    DataHelper dbHelper = new DataHelper(UjianActivity.this);
//                    SQLiteDatabase db = dbHelper.getReadableDatabase();
                            DataHelper dbHelper = new DataHelper(UjianActivity.this);
                            SQLiteDatabase db = dbHelper.getReadableDatabase();
                            Cursor cursor = db.rawQuery("SELECT * FROM tbl_jawaban", null);
                            cursor.moveToFirst();
                            if (cursor.getCount() == 0) {
                                Snackbar.make(findViewById(android.R.id.content), "Belum ada soal terjawab. Silahkan jawab soal terlebih dahulu",
                                        Snackbar.LENGTH_LONG).show();
                            } else {
                                Intent intent = new Intent(UjianActivity.this, SoalActivity.class);
//                                intent.putExtra("FLAG", "2");
                                startActivity(intent);
                            }
                        }
                    }else {
                        DataHelper dbHelper = new DataHelper(UjianActivity.this);
                        SQLiteDatabase db = dbHelper.getReadableDatabase();
                        Cursor cursor = db.rawQuery("SELECT * FROM tbl_jawaban", null);
                        cursor.moveToFirst();
                        if (cursor.getCount() == 0) {
                            Snackbar.make(findViewById(android.R.id.content), "Belum ada soal terjawab. Silahkan jawab soal terlebih dahulu",
                                    Snackbar.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(UjianActivity.this, SoalActivity.class);
//                            intent.putExtra("FLAG", "2");
                            startActivity(intent);
                        }
                    }
                }else {
                    Snackbar.make(findViewById(android.R.id.content), "Anda belum mengikuti Diklat Teknis manapun. Silahkan ikuti salah satu Diklat Teknis",
                            Snackbar.LENGTH_LONG).show();
                }
            }
        });

        btnHasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!user.getId_angkatan().isEmpty()) {
                if (Utilities.isNetworkAvailable(UjianActivity.this)) {
                    startActivity(new Intent(UjianActivity.this, HasilActivity.class));
                }else {
                    Snackbar.make(findViewById(android.R.id.content), "Tidak ada koneksi internet",
                            Snackbar.LENGTH_LONG).show();
                }
//                }else {
//                    Snackbar.make(findViewById(android.R.id.content), "Anda belum mengikuti pelatihan manapun. Silahkan ikuti salah satu pelatihan",
//                            Snackbar.LENGTH_LONG).show();
//                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = Utilities.getUser(this);
        if (user.getPretest().equals("0")){
            btnIsi.setText("Isi Soal Ujian Pretest");
            btnKirim.setText("Kirim Ujian Pretest");
        }else {
            btnIsi.setText("Isi Soal Ujian Postest");
            btnKirim.setText("Kirim Ujian Postest");
        }
    }

    public void GetSoal(String iduser, String idkelas, String idangkatan, String tglujian) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();

        String random = Utilities.getRandom(5);

        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.getBaseURLPhp())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        final APIServices apiService = retrofit.create(APIServices.class);
        Call<GetValue<Ujian>> call = apiService.getujian(random, iduser, idkelas, idangkatan, tglujian);
        call.enqueue(new Callback<GetValue<Ujian>>() {
            @Override
            public void onResponse(@NonNull Call<GetValue<Ujian>> call, @NonNull Response<GetValue<Ujian>> response) {
                dialog.dismiss();
                if (response.body() != null) {
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        List<Ujian> data = response.body().getData();

                        if (data.size() == 0){
                            Snackbar.make(findViewById(android.R.id.content), "Belum ada soal. Silahkan coba lagi lain kali",
                                    Snackbar.LENGTH_LONG).show();
                        }else {
                            DataHelper dbHelper = new DataHelper(UjianActivity.this);
                            SQLiteDatabase db = dbHelper.getReadableDatabase();
                            for (int a = 0; a < data.size(); a++) {
                                dbHelper.onInsertSoal(db, data.get(a).getId_soal(), data.get(a).getNomor_soal(), data.get(a).getIsi_soal(), data.get(a).getA(), data.get(a).getB(), data.get(a).getC(), data.get(a).getD(), data.get(a).getE());
                            }
                            Snackbar.make(findViewById(android.R.id.content), "Soal ujian berhasil diunduh",
                                    Snackbar.LENGTH_LONG).show();
//                            btnUnduh.setText("Soal ujian telah diunduh");
                        }
                    }else if (success == 2){
                        Snackbar.make(findViewById(android.R.id.content), "Anda telah selesai mengerjakan ujian",
                                Snackbar.LENGTH_LONG).show();
                    }else {
                        Snackbar.make(findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke server",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetValue<Ujian>> call, Throwable t) {
                dialog.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke server",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

}
