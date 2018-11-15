package com.alot.elearning;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LupaActivity extends AppCompatActivity{
    EditText etNip, etNama, etEmail, etNomor;
    Button btnKirim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa);
        etNip = findViewById(R.id.et_nip);
        etNama = findViewById(R.id.et_nama);
        etEmail = findViewById(R.id.et_email);
        etNomor = findViewById(R.id.et_no);
        btnKirim = findViewById(R.id.btn_save);

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNip.getText().toString().isEmpty() || etNama.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty() || etNomor.getText().toString().isEmpty()){
                    Snackbar.make(findViewById(android.R.id.content), "Mohon lengkapi data yang dibutuhkan", 5000).show();
                }else {
                    SetUser();
                }
            }
        });

    }

    public void SetUser() {
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
        Call<SetValue> call = apiService.lupapass(random, etNip.getText().toString().trim(), etNama.getText().toString().trim(), etEmail.getText().toString().trim(), etNomor.getText().toString().trim());
        call.enqueue(new Callback<SetValue>() {
            @Override
            public void onResponse(@NonNull Call<SetValue> call, @NonNull Response<SetValue> response) {
                dialog.dismiss();
                if (response.body() != null) {
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        Snackbar.make(findViewById(android.R.id.content), "Permintaan lupa password berhasil dikirim. Silahkan cek Email atau WhatsApp Anda", Snackbar.LENGTH_LONG).show();
                    }else {
                        Snackbar.make(findViewById(android.R.id.content), "Gagal mengirim permintaan lupa password",
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
