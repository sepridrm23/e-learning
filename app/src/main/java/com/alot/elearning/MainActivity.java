package com.alot.elearning;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
//    ProgressBar pb;
    Dialog dialog;
//    int totalSize = 0;
//    TextView cur_val;
//    String dwnload_file_path = "http://freshyummy.co.id/freshyummy/tes.mp4";
    Button btnKelas;
    TextView tv_diklat, tv_mulai, tv_pretest, tv_protest, tv_selesai;
    LinearLayout ll;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnOut = findViewById(R.id.btnOut);
        Button btnUjian = findViewById(R.id.btnUjian);
        btnKelas = findViewById(R.id.btnKelas);
        Button btnProfil = findViewById(R.id.btnProfil);
        Button btnMateri = findViewById(R.id.btnMateri);
        tv_diklat = findViewById(R.id.tv_diklat);
        tv_mulai = findViewById(R.id.tv_mulai);
        tv_pretest = findViewById(R.id.tv_pretest);
        tv_protest = findViewById(R.id.tv_protest);
        tv_selesai = findViewById(R.id.tv_selesai);
        ll = findViewById(R.id.ll);

//        Button b = findViewById(R.id.b1);

//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showProgress(dwnload_file_path);
//
//                new Thread(new Runnable() {
//                    public void run() {
//                        downloadFile("http://freshyummy.co.id/bkkbn/file/haha.mp4", "haha.mp4", 1);
//                    }
//                }).start();
//            }
//        });

        if (Utilities.isNetworkAvailable(this)) {
            user = Utilities.getUser(this);
            CheckID();
        }

        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true)
                        .setTitle("Konfirmasi")
                        .setMessage("Keluar dari akun ?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (Utilities.isNetworkAvailable(MainActivity.this)) {
                                    SignOut();
                                }else {
                                    Snackbar.make(findViewById(android.R.id.content), "Tidak ada koneksi internet",
                                            Snackbar.LENGTH_LONG).show();
                                }
                            }
                        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        });

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfilActivity.class));
            }
        });

        btnMateri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }else {
                    if (!user.getId_angkatan().isEmpty()) {
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
                            Intent intent = new Intent(MainActivity.this, MateriActivity.class);
                            intent.putExtra("ID", user.getId_kelas());
                            startActivity(intent);
                        }
                    }else {
                        Snackbar.make(findViewById(android.R.id.content), "Anda belum mengikuti Diklat Teknis manapun. Silahkan ikuti salah satu Diklat Teknis",
                                Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnKelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!user.getId_angkatan().isEmpty()) {
//                    Snackbar.make(findViewById(android.R.id.content), "Anda sudah terdaftar pada pelatihan "+user.getNama_kelas()+" ("+user.getNama_angkatan()+")",
//                            Snackbar.LENGTH_LONG).show();
//                }else {
                if (Utilities.isNetworkAvailable(MainActivity.this)) {
                    startActivity(new Intent(MainActivity.this, ClassActivity.class));
                }else {
                    Snackbar.make(findViewById(android.R.id.content), "Tidak ada koneksi internet",
                            Snackbar.LENGTH_LONG).show();
                }
//                }
            }
        });

        btnUjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!user.getId_angkatan().isEmpty()) {
                    startActivity(new Intent(MainActivity.this, UjianActivity.class));
//                }else {
//                    Snackbar.make(findViewById(android.R.id.content), "Anda belum mengikuti kelas manapun. Silahkan ikuti salah satu kelas",
//                            Snackbar.LENGTH_LONG).show();
//                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Intent intent = new Intent(MainActivity.this, MateriActivity.class);
                intent.putExtra("ID", user.getId_kelas());
                startActivity(intent);
            }else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = Utilities.getUser(this);
//        Log.e("boom", user.getPretest());

        if (!user.getTgl_selesai().isEmpty()) {
            Date date_now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date date_selesai = new Date();
            try {
                date_selesai = format.parse(user.getTgl_selesai());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (date_now.after(date_selesai)) {
                String namapelatihan = user.getNama_kelas();
                DataHelper dbHelper = new DataHelper(MainActivity.this);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM tbl_materi", null);
                cursor.moveToFirst();
                if (cursor.getCount() != 0) {
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                    for (int cc = 0; cc < cursor.getCount(); cc++) {
                        cursor.moveToPosition(cc);
                        File dir = new File(path, "/mlearning/" + cursor.getString(3));
                        dir.delete();
                    }
                }

                dbHelper.onDeleteAll(db);

                List<User> temp_user = new ArrayList<>();
                temp_user.add(new User(
                        user.getId_user(), user.getUsername(), user.getNip(), user.getNama(), user.getEmail(), user.getNo_hp(),
                        user.getNo_hp_other(), user.getNo_wa(), user.getNo_rekening(), user.getNama_bank(), user.getProvinsi(),
                        user.getKabupaten(), "", "", "", "", "", "", "", ""
                ));

                for (User add : temp_user) {
                    Utilities.setUser(MainActivity.this, add);
                }

                user = Utilities.getUser(MainActivity.this);

//            if (!user.getId_angkatan().isEmpty()){
//                btnKelas.setText("Pelatihan\n"+user.getNama_kelas()+" ("+user.getNama_angkatan()+")");
//            }else {
//                btnKelas.setText("Pelatihan");
//            }

                Snackbar.make(findViewById(android.R.id.content), "Diklat Teknis " + namapelatihan + " telah selesai",
                        5000).show();
            }
        }

        if (!user.getId_angkatan().isEmpty()){
//            btnKelas.setText("Diklat Teknis\n"+user.getNama_kelas()+" ("+user.getNama_angkatan()+")");
            tv_diklat.setText(user.getNama_kelas()+"-"+user.getNama_angkatan());
            tv_mulai.setText(user.getTgl_mulai());
            tv_pretest.setText(user.getTgl_mulai());
            tv_protest.setText(user.getTgl_ujian());
            tv_selesai.setText(user.getTgl_selesai());
            ll.setVisibility(View.VISIBLE);
        }else {
//            btnKelas.setText("Diklat Teknis");
            ll.setVisibility(View.GONE);
        }
    }

    public void SignOut() {
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
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
        Call<SetValue> call = apiService.signout(random, user.getId_user());
        call.enqueue(new Callback<SetValue>() {
            @Override
            public void onResponse(@NonNull Call<SetValue> call, @NonNull Response<SetValue> response) {
                dialog.dismiss();
                if (response.body() != null) {
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        Utilities.signOutUser(MainActivity.this);
                        Intent mIntent = new Intent(MainActivity.this, SignInActivity.class);
                        startActivity(mIntent);
                        finish();
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

    public void CheckID() {
//        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
//        dialog.setMessage("Loading...");
//        dialog.setCancelable(false);
//        dialog.show();

        String random = Utilities.getRandom(5);

        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.getBaseURLPhp())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        final APIServices apiService = retrofit.create(APIServices.class);
        Call<SetValue> call = apiService.checkid(random, user.getId_user());
        call.enqueue(new Callback<SetValue>() {
            @Override
            public void onResponse(@NonNull Call<SetValue> call, @NonNull Response<SetValue> response) {
//                dialog.dismiss();
                if (response.body() != null) {
                    int success = response.body().getSuccess();
                    if (success == 1) {
//                        user = Utilities.getUser(MainActivity.this);
                        if (!user.getId_angkatan().isEmpty()) {
                            Snackbar.make(findViewById(android.R.id.content), "Anda telah dikeluarkan dari Diklat Teknis " + user.getNama_kelas() + ", Angkatan " + user.getNama_angkatan(),
                                    5000).show();
                            List<User> temp_user = new ArrayList<>();
                            temp_user.add(new User(
                                    user.getId_user(), user.getUsername(), user.getNip(), user.getNama(), user.getEmail(), user.getNo_hp(),
                                    user.getNo_hp_other(), user.getNo_wa(), user.getNo_rekening(), user.getNama_bank(), user.getProvinsi(),
                                    user.getKabupaten(), "", "", "", "",
                                    "", "", "", ""
                            ));

                            for (User add : temp_user) {
                                Utilities.setUser(MainActivity.this, add);
                            }

                            user = Utilities.getUser(MainActivity.this);

                            ll.setVisibility(View.GONE);
                        }
                    }
//                    else{
//                        Snackbar.make(findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
//                                Snackbar.LENGTH_LONG).show();
//                    }
//                } else {
//                    Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke server",
//                            Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SetValue> call, Throwable t) {
//                dialog.dismiss();
//                Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke server",
//                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

//    void downloadFile(String URL, String name, final int flag){
//        try {
//            URL url = new URL(URL);
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//            urlConnection.setRequestMethod("GET");
//            urlConnection.setDoOutput(true);
//
//            //connect
//            urlConnection.connect();
//
//            //set the path where we want to save the file
////            File SDCardRoot = Environment.getExternalStorageDirectory();
//            String path = Environment.getExternalStorageDirectory().getAbsolutePath() +
//                    "/tes/";
//            File dir = new File(path);
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//
//            //create a new file, to save the downloaded file
//            File file = new File(dir, name);
//
//            FileOutputStream fileOutput = new FileOutputStream(file);
//
//            //Stream used for reading the data from the internet
//            InputStream inputStream = urlConnection.getInputStream();
//
//            //this is the total size of the file which we are downloading
//            totalSize = urlConnection.getContentLength();
//
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    pb.setMax(totalSize);
//                }
//            });
//
//            //create a buffer...
//            byte[] buffer = new byte[1024];
//            int bufferLength = 0;
//            int downloadedSize = 0;
//
//            while ((bufferLength = inputStream.read(buffer)) > 0 ) {
//                fileOutput.write(buffer, 0, bufferLength);
//                downloadedSize = downloadedSize + bufferLength;
//                // update the progressbar //
//                final int finalDownloadedSize = downloadedSize;
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        pb.setProgress(finalDownloadedSize);
////                        float per = ((float)downloadedSize/totalSize) * 100;
////                        cur_val.setText("Downloaded " + downloadedSize + "byte / " + totalSize + "KB (" + (int)per + "%)" );
//                        if (finalDownloadedSize > 1000){
//                            cur_val.setText("Downloaded " + finalDownloadedSize/1000 + " MB");
//                        }else {
//                            cur_val.setText("Downloaded " + finalDownloadedSize + " byte");
//                        }
//                    }
//                });
//            }
//            //close the output stream when complete //
//            fileOutput.close();
//            runOnUiThread(new Runnable() {
//                public void run() {
////                     pb.dismiss(); // if you want close it..
////                    dialog.dismiss();
//
//                    if (flag == 1) {
//                        new Thread(new Runnable() {
//                            public void run() {
//                                downloadFile("http://freshyummy.co.id/bkkbn/file/sd.pdf", "sd.pdf", 0);
//                            }
//                        }).start();
//                    }
//                }
//            });
//
//        } catch (final MalformedURLException e) {
//            showError("Error : MalformedURLException " + e);
//            e.printStackTrace();
//        } catch (final IOException e) {
//            showError("Error : IOException " + e);
//            e.printStackTrace();
//        } catch (final Exception e) {
//            showError("Error : Please check your internet connection " + e);
//        }
//    }
//
//    void showError(final String err){
//        runOnUiThread(new Runnable() {
//            public void run() {
//                Toast.makeText(MainActivity.this, err, Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    void showProgress(String file_path){
//        dialog = new Dialog(MainActivity.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.myprogressdialog);
//        dialog.setTitle("Download Progress");
//
//        TextView text = dialog.findViewById(R.id.tv1);
//        text.setText("Downloading file from ... " + file_path);
//        cur_val = dialog.findViewById(R.id.cur_pg_tv);
//        cur_val.setText("Starting download...");
//        dialog.setCancelable(true);
//        dialog.show();
//
//        pb = dialog.findViewById(R.id.progress_bar);
//        pb.setProgress(0);
//        pb.setProgressDrawable(getResources().getDrawable(R.drawable.green_progress));
//    }

}
