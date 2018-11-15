package com.alot.elearning;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AngkatanActivity extends AppCompatActivity {
    RecyclerView rView;
    String ID, NAMA, MULAI, SELESAI, UJIAN;
    List<Angkatan> data = new ArrayList<>();
    List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        rView = findViewById(R.id.rView);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setHasFixedSize(true);
        rView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        ID = getIntent().getStringExtra("ID");
        NAMA = getIntent().getStringExtra("NAMA");
        MULAI = getIntent().getStringExtra("MULAI");
        SELESAI = getIntent().getStringExtra("SELESAI");
        UJIAN = getIntent().getStringExtra("UJIAN");

//        ArrayList data = new ArrayList<Angkatan>();
//        for (int i=0;i<3;i++) {
//            Angkatan obj = new Angkatan("1","1", "Angkatan "+i+1,
//                    "10", "2");
//            data.add(obj);
//        }
//        AngkatanViewAdapter adapter = new AngkatanViewAdapter(this, data);
//        rView.setAdapter(adapter);

        GetAngkatan();

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
            String id = intent.getStringExtra("ID");
            String jumlah = intent.getStringExtra("JUMLAH");
            updateMateri(id, jumlah);
        }
    };

    void updateMateri(String id, String jumlah) {
        if (data != null) {
            for (Angkatan angkatan : data) {
                if (angkatan.getId_angkatan().equals(id)){
//                    Log.e("boo", id+" "+jumlah+" "+data.size());
                    angkatan.setJumlah_kuota_terisa(jumlah);
                }
            }
            AngkatanViewAdapter adapter = new AngkatanViewAdapter(AngkatanActivity.this, data, users);
            rView.setAdapter(adapter);
        }
    }

    public void GetAngkatan() {
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
        Call<GetValue<Angkatan>> call = apiService.getangkatan(random, ID);
        call.enqueue(new Callback<GetValue<Angkatan>>() {
            @Override
            public void onResponse(@NonNull Call<GetValue<Angkatan>> call, @NonNull Response<GetValue<Angkatan>> response) {
                dialog.dismiss();
                if (response.body() != null) {
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        data = response.body().getData();
                        users.add(new User(
                                "","","","","","","","","","","",
                                "", ID, NAMA,"","", MULAI, SELESAI, UJIAN, ""
                        ));
                        AngkatanViewAdapter adapter = new AngkatanViewAdapter(AngkatanActivity.this, data, users);
                        rView.setAdapter(adapter);
                    }else  {
                        Snackbar.make(findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke server",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetValue<Angkatan>> call, Throwable t) {
                dialog.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke server",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

}
