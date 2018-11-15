package com.alot.elearning;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class ProfilActivity extends AppCompatActivity {
    EditText etUsername, etPassword, etNip, etNama, etEmail, etHpTsel, etHpLain, etHpWa, etNoRek, etProv, etKab;
    Spinner spBank;
    String strNamaBank;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        etUsername = findViewById(R.id.editText14);
        etPassword = findViewById(R.id.editText15);
        etNip = findViewById(R.id.editText);
        etNama = findViewById(R.id.editText5);
        etEmail = findViewById(R.id.editText6);
        etHpTsel = findViewById(R.id.editText4);
        etHpLain = findViewById(R.id.editText8);
        etHpWa = findViewById(R.id.editText10);
        etNoRek = findViewById(R.id.editText11);
        spBank = findViewById(R.id.spinner);
        etProv = findViewById(R.id.editText12);
        etKab = findViewById(R.id.editText13);

        Button btnUpdate = findViewById(R.id.button);

        final User user = Utilities.getUser(this);
        etUsername.setText(user.getUsername());
        etPassword.setText("*****");
        etNip.setText(user.getNip());
        etNama.setText(user.getNama());
        etEmail.setText(user.getEmail());
        etHpTsel.setText(user.getNo_hp());
        etHpLain.setText(user.getNo_hp_other());
        etHpWa.setText(user.getNo_wa());
        etProv.setText(user.getProvinsi());
        etNoRek.setText(user.getNo_rekening());
        etKab.setText(user.getKabupaten());

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
        spBank.setAdapter(adapter);

        int spinnerPosition = adapter.getPosition(user.getNama_bank());
        spBank.setSelection(spinnerPosition);

        if (user.getNo_hp_other().isEmpty()){
            etHpLain.setEnabled(true);
        }else {
            etHpLain.setEnabled(false);
        }

        if (user.getNo_rekening().isEmpty()){
            etNoRek.setEnabled(true);
        }else {
            etNoRek.setEnabled(false);
        }

        if (user.getNama_bank().isEmpty()){
            spBank.setEnabled(true);
        }else {
            spBank.setEnabled(false);
        }

        spBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetUser(user.getId_user());
            }
        });

    }

    public void SetUser(String iduser) {
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
        Call<SetValue> call = apiService.updateuser(random, iduser, etHpLain.getText().toString().trim(), etNoRek.getText().toString().trim(), strNamaBank);
        call.enqueue(new Callback<SetValue>() {
            @Override
            public void onResponse(@NonNull Call<SetValue> call, @NonNull Response<SetValue> response) {
                dialog.dismiss();
                if (response.body() != null) {
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        User user = Utilities.getUser(ProfilActivity.this);
                        List<User> temp_user = new ArrayList<>();
                        temp_user.add(new User(
                                user.getId_user(), user.getUsername(), user.getNip(), user.getNama(), user.getEmail(), user.getNo_hp(),
                                etHpLain.getText().toString().trim() , user.getNo_wa(), etNoRek.getText().toString().trim(), strNamaBank, user.getProvinsi(),
                                user.getKabupaten(), user.getId_kelas(), user.getNama_kelas(), user.getId_angkatan(), user.getNama_angkatan(),
                                user.getTgl_mulai(), user.getTgl_selesai(), user.getTgl_ujian(), user.getPretest()
                        ));

                        for (User add : temp_user) {
                            Utilities.setUser(ProfilActivity.this, add);
                        }

                        Snackbar.make(findViewById(android.R.id.content), "Akun berhasil diperbarui", Snackbar.LENGTH_LONG).show();
                    }else {
                        Snackbar.make(findViewById(android.R.id.content), "Gagal memperbarui akun. Silahkan coba lagi",
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
