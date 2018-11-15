package com.alot.elearning;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HasilActivity extends AppCompatActivity{
    TextView tvPelatihan;
    RecyclerView rView;
    LinearLayout linearLayout, linearLayout2;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);
        tvPelatihan = findViewById(R.id.tvPelatihan);
        rView = findViewById(R.id.rView);
        linearLayout = findViewById(R.id.line2);
        linearLayout2 = findViewById(R.id.line1);
        linearLayout2.setVisibility(View.GONE);

        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setHasFixedSize(true);
        rView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        user = Utilities.getUser(this);

        GetHasil();

    }

    public void GetHasil() {
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
        Call<GetValue<Hasil>> call = apiService.gethasil(random, user.getId_user());
        call.enqueue(new Callback<GetValue<Hasil>>() {
            @Override
            public void onResponse(@NonNull Call<GetValue<Hasil>> call, @NonNull Response<GetValue<Hasil>> response) {
                dialog.dismiss();
                if (response.body() != null) {
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        List<Hasil> data = response.body().getData();
                        if (data.size() == 0){
                            linearLayout2.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                        }else {
                            tvPelatihan.setText(data.get(0).getNama_kelas()+" ("+data.get(0).getNama_angkatan()+")");
                            linearLayout2.setVisibility(View.VISIBLE);
                            linearLayout.setVisibility(View.GONE);
                            HasilViewAdapter adapter = new HasilViewAdapter(HasilActivity.this, data);
                            rView.setAdapter(adapter);
                        }
                    }else {
                        linearLayout2.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                        Snackbar.make(findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    linearLayout2.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke server",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetValue<Hasil>> call, Throwable t) {
                dialog.dismiss();
                linearLayout2.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke server",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
