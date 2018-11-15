package com.alot.elearning;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClassActivity extends AppCompatActivity {
    RecyclerView rView;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        rView = findViewById(R.id.rView);
        linearLayout = findViewById(R.id.line1);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setHasFixedSize(true);
        rView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

//        ArrayList data = new ArrayList<Class>();
//        for (int i=0;i<3;i++) {
//            Class obj = new Class("1","Basis Data Keluarga Indonesia", "2",
//                    "2018-7-10", "2018-7-20","2018-7-19", "30", "10");
//            data.add(obj);
//        }
//        ClassViewAdapter adapter = new ClassViewAdapter(this, data);
//        rView.setAdapter(adapter);

        GetClass();

    }

    public void GetClass() {
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
        Call<GetValue<Class>> call = apiService.getclass(random);
        call.enqueue(new Callback<GetValue<Class>>() {
            @Override
            public void onResponse(@NonNull Call<GetValue<Class>> call, @NonNull Response<GetValue<Class>> response) {
                dialog.dismiss();
                if (response.body() != null) {
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        List<Class> data;
                        data = response.body().getData();
//                        Log.e("data", ""+data.get(0).getId_kelas());
                        if (data.size() != 0) {
                            if (data.get(0).getId_kelas() == null) {
                                rView.setVisibility(View.GONE);
                                linearLayout.setVisibility(View.VISIBLE);
                            } else {
                                rView.setVisibility(View.VISIBLE);
                                linearLayout.setVisibility(View.GONE);
                                ClassViewAdapter adapter = new ClassViewAdapter(ClassActivity.this, data);
                                rView.setAdapter(adapter);
                            }
                        }else {
                            rView.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                        }
                    }else {
                        rView.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                        Snackbar.make(findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    rView.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke server",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetValue<Class>> call, Throwable t) {
                dialog.dismiss();
                rView.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke server",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

}
