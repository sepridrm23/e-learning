package com.alot.elearning;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity {
    EditText etUsername, etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        etUsername = findViewById(R.id.editText);
        etPassword = findViewById(R.id.editText2);
        TextView tvDaftar = findViewById(R.id.textView2);
        TextView tvLupa = findViewById(R.id.tv_lupa);

        tvDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        tvLupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, LupaActivity.class));
            }
        });

        Button btnSignIn = findViewById(R.id.button);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUsername.getText().toString().isEmpty()){
                    etUsername.setError("Username tidak boleh kosong");
//                    Snackbar.make(findViewById(android.R.id.content), "Username tidak boleh kosong",
//                            Snackbar.LENGTH_LONG).show();
                }else if (etPassword.getText().toString().isEmpty()){
                    etPassword.setError("Password tidak boleh kosong");
//                    Snackbar.make(findViewById(android.R.id.content), "Password tidak boleh kosong",
//                            Snackbar.LENGTH_LONG).show();
                }else {
                    SignIn();
                }
            }
        });
    }

    public void SignIn() {
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
        Call<GetValue<User>> call = apiService.signin(random, etUsername.getText().toString().trim(), etPassword.getText().toString().trim());
        call.enqueue(new Callback<GetValue<User>>() {
            @Override
            public void onResponse(@NonNull Call<GetValue<User>> call, @NonNull Response<GetValue<User>> response) {
                dialog.dismiss();
                if (response.body() != null) {
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        Utilities.setLogin(SignInActivity.this);
                        for (User user : response.body().getData()) {
                            Utilities.setUser(SignInActivity.this, user);
                        }
//                        Snackbar.make(findViewById(android.R.id.content), "Login success",
//                                Snackbar.LENGTH_LONG).show();
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        finish();
                    } else if (success == 2) {
                        Snackbar.make(findViewById(android.R.id.content), "Password tidak sesuai",
                                Snackbar.LENGTH_LONG).show();
                    }else if (success == 3) {
                        Snackbar.make(findViewById(android.R.id.content), "Username tidak terdaftar",
                                Snackbar.LENGTH_LONG).show();
                    }else if (success == 4) {
                        Snackbar.make(findViewById(android.R.id.content), "Anda telah Login di device lain. Silahkan Logout terlebih dahulu",
                                Snackbar.LENGTH_LONG).show();
                    }else {
                        Snackbar.make(findViewById(android.R.id.content), "Login gagal. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke server",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetValue<User>> call, Throwable t) {
                dialog.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke server",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

}
