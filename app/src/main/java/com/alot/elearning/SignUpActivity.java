package com.alot.elearning;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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

public class SignUpActivity extends AppCompatActivity {
    EditText etNip, etNama, etEmail, etHpTsel, etHpLain, etHpWa, etNoRek, etProv, etKab;
    Spinner spNamaBank;
    Button  btnSignUp;
    String strNamaBank;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etNip = findViewById(R.id.editText);
        etNama = findViewById(R.id.editText5);
        etEmail = findViewById(R.id.editText6);
        etHpTsel = findViewById(R.id.editText4);
        etHpLain = findViewById(R.id.editText8);
        etHpWa = findViewById(R.id.editText10);
        etNoRek = findViewById(R.id.editText11);
        etProv = findViewById(R.id.editText12);
        etKab = findViewById(R.id.editText13);
        btnSignUp = findViewById(R.id.button);
        spNamaBank = findViewById(R.id.spinner);

        final List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Silahkan Pilih Bank");
        spinnerArray.add("Bank Mandiri");
        spinnerArray.add("BRI");
        spinnerArray.add("BCA");
        spinnerArray.add("BNI");
        spinnerArray.add("Bank CIMB Niaga");
        spinnerArray.add("Bank Danamon");
        spinnerArray.add("Bank Permata");
        spinnerArray.add("Bank Panin");
        spinnerArray.add("BTN");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNamaBank.setAdapter(adapter);

        spNamaBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    strNamaBank = "";
                }else {
                    strNamaBank = spinnerArray.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNip.getText().toString().isEmpty()){
                    etNip.setError("NIP tidak boleh kosong");
                }else if (etNama.getText().toString().isEmpty()){
                    etNama.setError("Nama tidak boleh kosong");
                }else if (etEmail.getText().toString().isEmpty()){
                    etEmail.setError("Email tidak boleh kosong");
                }else if (etHpTsel.getText().toString().isEmpty()){
                    etHpTsel.setError("No. HP Telkomsel tidak boleh kosong");
                }else if (etHpWa.getText().toString().isEmpty()){
                    etHpWa.setError("No. HP WhatsApp tidak boleh kosong");
                }else if (etProv.getText().toString().isEmpty()){
                    etProv.setError("Nama Provinsi tidak boleh kosong");
                }else if (etKab.getText().toString().isEmpty()){
                    etKab.setError("Nama Kabupaten/Kota tidak boleh kosong");
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
        Call<SetValue> call = apiService.signup(random, etNip.getText().toString().trim(), etNama.getText().toString().trim().toUpperCase(), etEmail.getText().toString().trim(),
        etHpTsel.getText().toString().trim(), etHpLain.getText().toString().trim(), etHpWa.getText().toString().trim(), etNoRek.getText().toString().trim(), strNamaBank,
        etProv.getText().toString().trim().toUpperCase(), etKab.getText().toString().trim().toUpperCase());
        call.enqueue(new Callback<SetValue>() {
            @Override
            public void onResponse(@NonNull Call<SetValue> call, @NonNull Response<SetValue> response) {
                dialog.dismiss();
                if (response.body() != null) {
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        Snackbar.make(findViewById(android.R.id.content), "Akun berhasil dibuat. Username dan Password akan dikirim melalui Email atau WhatsApp terdaftar", 5000).show();
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 5500);
                    } else if (success == 2) {
                        Snackbar.make(findViewById(android.R.id.content), "NIP sudah terdaftar. Silahkan login aplikasi",
                                Snackbar.LENGTH_LONG).show();
                    }else {
                        Snackbar.make(findViewById(android.R.id.content), "Gagal membuat akun. Silahkan coba lagi",
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
