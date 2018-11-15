package com.alot.elearning;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SoalActivity extends AppCompatActivity {
    ProgressDialog dialog;
    RecyclerView rView;
    List<Ujian> data = new ArrayList<>();
    List<Jawaban> jawabans = new ArrayList<>();
//    String flag;
    int counter;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soal);
        Button btnKirim = findViewById(R.id.btnKirim);
        rView = findViewById(R.id.rView);

        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setHasFixedSize(true);
        rView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        dialog = new ProgressDialog(this);

        user = Utilities.getUser(this);

//        flag = getIntent().getStringExtra("FLAG");
//        if (flag.equals("1")){
//            btnKirim.setVisibility(View.GONE);
//        }else {
//            btnKirim.setVisibility(View.VISIBLE);
//        }

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHelper dbHelper = new DataHelper(SoalActivity.this);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM tbl_jawaban", null);
                cursor.moveToFirst();
                int jmlJawaban = cursor.getCount();
                if (cursor.getCount() != 0) {
                    for (int cc = 0; cc < cursor.getCount(); cc++){
                        cursor.moveToPosition(cc);
//                        Log.e("id soal", cursor.getString(0)+cursor.getString(1));
                        jawabans.add(new Jawaban(
                                cursor.getString(0), cursor.getString(1)
                        ));
                    }
                }

                cursor = db.rawQuery("SELECT * FROM tbl_soal ORDER BY CAST(nomor_soal AS INTEGER)", null);
                cursor.moveToFirst();
                int jmlSoal = cursor.getCount();

                if (jmlSoal==jmlJawaban) {
                    counter = 0;
                    SetJawaban(jawabans.get(counter).getIdsoal(), jawabans.get(counter).getJawaban());
                }else {
                    Snackbar.make(findViewById(android.R.id.content), "Ada soal yang belum terjawab. Silahkan periksa ulang jawaban Anda",
                            Snackbar.LENGTH_LONG).show();
                }
            }
        });

        LoadData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(uiUpdated, new IntentFilter("ALERT_STATUS_CHANGE"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(uiUpdated);
    }

    private BroadcastReceiver uiUpdated = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String id = intent.getStringExtra("IDSOAL");
            String jawaban = intent.getStringExtra("JAWABAN");
            updateMateri(id, jawaban);
        }
    };

    void updateMateri(String id, String jawaban) {
        if (data != null) {
            for (Ujian ujian : data) {
                if (ujian.getId_soal().equals(id)){
//                    Log.e("boo", id+" "+jumlah+" "+data.size());
                    ujian.setId_kelas(jawaban+"0");
                }
            }
//            SoalViewAdapter adapter = new SoalViewAdapter(SoalActivity.this, data);
//            rView.setAdapter(adapter);
        }
    }

    private void LoadData(){
        data = new ArrayList<>();
        DataHelper dbHelper = new DataHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_soal ORDER BY CAST(nomor_soal AS INTEGER)", null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            for (int cc = 0; cc < cursor.getCount(); cc++) {
//                Log.e("nomor", cursor.getString(1));
                cursor.moveToPosition(cc);
                SQLiteDatabase db2 = dbHelper.getReadableDatabase();
                Cursor cursor2 = db2.rawQuery("SELECT * FROM tbl_jawaban WHERE id_soal = '"+cursor.getString(0)+"'", null);
                cursor2.moveToFirst();
                if (cursor2.getCount() != 0) {
                    data.add(new Ujian(
                            cursor.getString(0), cursor2.getString(1)+cursor2.getString(2), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7)
                    ));
                }else {
                    data.add(new Ujian(
                            cursor.getString(0), "00", cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7)
                    ));
                }
            }
            SoalViewAdapter adapter = new SoalViewAdapter(SoalActivity.this, data);
            rView.setAdapter(adapter);
        }
    }

    public void SetJawaban(final String idsoal, final String jawaban) {
        if (counter==0) {
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();
        }

        String random = Utilities.getRandom(5);

        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.getBaseURLPhp())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        final APIServices apiService = retrofit.create(APIServices.class);
        Call<SetValue> call;
        if (user.getPretest().equals("0")) {
            call = apiService.setjawaban(random, user.getId_user(), idsoal, jawaban, user.getId_angkatan(), user.getTgl_mulai());
        }else {
            call = apiService.setjawaban(random, user.getId_user(), idsoal, jawaban, user.getId_angkatan(), user.getTgl_ujian());
        }
        call.enqueue(new Callback<SetValue>() {
            @Override
            public void onResponse(@NonNull Call<SetValue> call, @NonNull Response<SetValue> response) {
                if (response.body() != null) {
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        counter++;
                        if (counter == jawabans.size()) {
//                            Log.e("1 end", ""+counter);
                            DataHelper dbHelper = new DataHelper(SoalActivity.this);
                            SQLiteDatabase db = dbHelper.getReadableDatabase();
                            dbHelper.onKirimJawaban(db, jawabans.get(counter-1).getIdsoal());
//                            Snackbar.make(findViewById(android.R.id.content), "Jawaban berhasil di kirim",
//                                    Snackbar.LENGTH_LONG).show();
//                            dialog.dismiss();
//                            LoadData();
                            SetHasil(dialog);

//                            new Timer().schedule(new TimerTask() {
//                                @Override
//                                public void run() {
//                                    finish();
//                                }
//                            }, 3000);
                        } else {
//                            Log.e("1", ""+counter+"|"+jawabans.get(counter).getIdsoal());
                            DataHelper dbHelper = new DataHelper(SoalActivity.this);
                            SQLiteDatabase db = dbHelper.getReadableDatabase();
                            dbHelper.onKirimJawaban(db, jawabans.get(counter-1).getIdsoal());
                            SetJawaban(jawabans.get(counter).getIdsoal(), jawabans.get(counter).getJawaban());
                        }
                    }else if(success == 2){
                        counter++;
                        if (counter == jawabans.size()) {
//                            Log.e("2 end", ""+counter);
                            DataHelper dbHelper = new DataHelper(SoalActivity.this);
                            SQLiteDatabase db = dbHelper.getReadableDatabase();
                            dbHelper.onKirimJawaban(db, jawabans.get(counter-1).getIdsoal());
//                            Snackbar.make(findViewById(android.R.id.content), "Jawaban berhasil di kirim",
//                                    3000).show();
//                            dialog.dismiss();
//                            LoadData();
                            SetHasil(dialog);

//                            new Timer().schedule(new TimerTask() {
//                                @Override
//                                public void run() {
//                                    finish();
//                                }
//                            }, 3000);
                        } else {
//                            Log.e("2", ""+counter+"|"+jawabans.get(counter).getIdsoal());
                            DataHelper dbHelper = new DataHelper(SoalActivity.this);
                            SQLiteDatabase db = dbHelper.getReadableDatabase();
                            dbHelper.onKirimJawaban(db, jawabans.get(counter-1).getIdsoal());
                            SetJawaban(jawabans.get(counter).getIdsoal(), jawabans.get(counter).getJawaban());
                        }
                    }else{
                        dialog.dismiss();
                        Log.e("failed", response.body().getMessage());
                        Snackbar.make(findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    dialog.dismiss();
                    Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke server",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SetValue> call, Throwable t) {
                dialog.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke server",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public void SetHasil(final ProgressDialog dialog) {
        String random = Utilities.getRandom(5);

        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.getBaseURLPhp())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        final APIServices apiService = retrofit.create(APIServices.class);
        Call<SetValue> call;
        if (user.getPretest().equals("0")) {
            call = apiService.sethasil(random, user.getId_user(), user.getId_angkatan(), user.getTgl_mulai(), user.getId_kelas(), "0");
        }else {
            call = apiService.sethasil(random, user.getId_user(), user.getId_angkatan(), user.getTgl_ujian(), user.getId_kelas(), "1");
        }
        call.enqueue(new Callback<SetValue>() {
            @Override
            public void onResponse(@NonNull Call<SetValue> call, @NonNull Response<SetValue> response) {
                dialog.dismiss();
                if (response.body() != null) {
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        if (user.getPretest().equals("0")){
                            Snackbar.make(findViewById(android.R.id.content), "Jawaban ujian pretest berhasil di kirim",
                                    2000).show();
//                        Log.e("boo", "am in!!");
//                        LoadData();

                            List<User> temp_user = new ArrayList<>();
                            temp_user.add(new User(
                                    user.getId_user(), user.getUsername(), user.getNip(), user.getNama(), user.getEmail(), user.getNo_hp(),
                                    user.getNo_hp_other() , user.getNo_wa(), user.getNo_rekening(), user.getNama_bank(), user.getProvinsi(),
                                    user.getKabupaten(), user.getId_kelas(), user.getNama_kelas(), user.getId_angkatan(), user.getNama_angkatan(),
                                    user.getTgl_mulai(), user.getTgl_selesai(), user.getTgl_ujian(), "1"
                            ));

                            for (User add : temp_user) {
                                Utilities.setUser(SoalActivity.this, add);
                            }

                            DataHelper dbHelper = new DataHelper(SoalActivity.this);
                            SQLiteDatabase db = dbHelper.getReadableDatabase();
                            dbHelper.onDeleteJawaban(db);
                        }else {
                            Snackbar.make(findViewById(android.R.id.content), "Jawaban ujian postest berhasil di kirim",
                                    2000).show();
//                        Log.e("boo", "am in!!");
//                        LoadData();
                            DataHelper dbHelper = new DataHelper(SoalActivity.this);
                            SQLiteDatabase db = dbHelper.getReadableDatabase();
                            dbHelper.onDeleteUjian(db);
                        }

                        new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, 2000);
                    }else{
                        Snackbar.make(findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke server",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SetValue> call, Throwable t) {
                dialog.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke server",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

}
