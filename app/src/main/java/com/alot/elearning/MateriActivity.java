package com.alot.elearning;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MateriActivity extends AppCompatActivity {
    List<Materi> data = new ArrayList<>();
    List<MateriViewer> dataDetail = new ArrayList<>();
//    Button btnSelectAll;
    RecyclerView rView;
    String ID;
    ProgressBar pb;
    Dialog dialog;
    int totalSize = 0, counter = 0;
    TextView cur_val, text;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi);
        rView = findViewById(R.id.rView);
        linearLayout = findViewById(R.id.line2);
//        btnSelectAll = findViewById(R.id.button);

        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setHasFixedSize(true);
        rView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        ID = getIntent().getStringExtra("ID");

        GetMateriSQL(0);

//        for (int i=0;i<3;i++) {
//            Materi obj = new Materi(String.valueOf(i),"2", "Basis Data Keluarga Indonesia", "1");
//            data.add(obj);
//        }
//        MateriViewAdapter adapter = new MateriViewAdapter(this, data);
//        rView.setAdapter(adapter);
//        btnSelectAll.setText("Batal Pilih Semua");

//        btnSelectAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (btnSelectAll.getText().equals("Pilih Semua")){
//                    for (Materi materi : data) {
//                        materi.setCheck("1");
//                    }
//                    btnSelectAll.setText("Batal Pilih Semua");
//                }else {
//                    for (Materi materi : data) {
//                        materi.setCheck("0");
//                    }
//                    btnSelectAll.setText("Pilih Semua");
//                }
//                MateriViewAdapter adapter = new MateriViewAdapter(MateriActivity.this, data);
//                rView.setAdapter(adapter);
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        loadUserCart(rvCart);
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
            String idMateri = intent.getStringExtra("ID");
            String namaMateri = intent.getStringExtra("NAMA");
//            updateMateri(idMateri);
            GetDetailMateri(idMateri, namaMateri);
        }
    };

//    void updateMateri(String id) {
//        boolean isSelectAll = true;
//        if (data != null) {
//            for (Materi materi : data) {
//                if (materi.getId_materi().equals(id)){
//                    if (!materi.getCheck().equals(check)){
//                        materi.setCheck(check);
//                    }
//                }
////                Log.e("check", materi.id_materi + " "+materi.getCheck());
//                if (!materi.getCheck().equalsIgnoreCase("1")) {
//                    isSelectAll = false;
//                }
//            }
//        }
//        if (isSelectAll) btnSelectAll.setText("Batal Pilih Semua");
//        else btnSelectAll.setText("Pilih Semua");
//    }

    private void GetMateriSQL(int flag){
        DataHelper dbHelper = new DataHelper(MateriActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_materi ORDER BY id_materi ASC",null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0){
//            Log.e("data", "data null");
            if (Utilities.isNetworkAvailable(MateriActivity.this)) {
                GetMateri(0);
            }else {
                Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
            }
        }else {
            data = new ArrayList<>();
            String lastId="";
            for (int cc = 0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);
//                Log.e("data", cursor.getString(0) + " " + cursor.getString(1));
                if (!cursor.getString(0).equals(lastId)) {
//                    tempData.add(new Materi(
//                            cursor.getString(0), ID, cursor.getString(1), "1", "1"));
                    if (cursor.getString(2).isEmpty()){
                        data.add(new Materi(
                                cursor.getString(0), ID, cursor.getString(1), "0", cursor.getString(5)
                        ));
                    }else {
                        data.add(new Materi(
                                cursor.getString(0), ID, cursor.getString(1), "1", cursor.getString(5)
                        ));
                    }
                }
                lastId = cursor.getString(0);
            }

//            int jml=0;
//            for (int a=0; a<tempData.size(); a++){
//                if (!lastId.equals("") && !tempData.get(a).getId_materi().equals(lastId)) {
//                    data.add(new Materi(
//                            tempData.get(a - 1).getId_materi(), ID, tempData.get(a - 1).getNama_materi(), "1", String.valueOf(jml)));
//                    jml = 0;
//                }
//                if (a==tempData.size()-1){
//                    jml++;
//                    data.add(new Materi(
//                            tempData.get(a-1).getId_materi(), ID, tempData.get(a-1).getNama_materi(), "1", String.valueOf(jml)));
//                    jml=0;
//                }else {
//                    jml++;
//                }
//                lastId = tempData.get(a).getId_materi();
//            }

            MateriViewAdapter adapter = new MateriViewAdapter(MateriActivity.this, data);
            rView.setAdapter(adapter);
            if (flag == 0) {
                if (Utilities.isNetworkAvailable(MateriActivity.this)) {
                    GetMateri(1);
                }
            }
        }
    }

    private void GetMateri(final int flag) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        if (flag == 0){
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
        Call<GetValue<Materi>> call = apiService.getmateri(random, ID);
        call.enqueue(new Callback<GetValue<Materi>>() {
            @Override
            public void onResponse(@NonNull Call<GetValue<Materi>> call, @NonNull Response<GetValue<Materi>> response) {
                dialog.dismiss();
                if (response.body() != null) {
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        List<Materi> tempData = response.body().getData();
                        if (flag == 0) {
                            if (tempData.size() == 0){
                                linearLayout.setVisibility(View.VISIBLE);
                            }else {
                                linearLayout.setVisibility(View.GONE);
                                MateriViewAdapter adapter = new MateriViewAdapter(MateriActivity.this, tempData);
                                rView.setAdapter(adapter);
                            }
//                        btnSelectAll.setText("Batal Pilih Semua");
                        }

                        DataHelper dbHelper = new DataHelper(MateriActivity.this);
                        SQLiteDatabase db = dbHelper.getReadableDatabase();
                        for (int a = 0; a < tempData.size(); a++) {
                            Cursor cursor = db.rawQuery("SELECT * FROM tbl_materi WHERE id_materi = '"+ tempData.get(a).getId_materi() +"'",null);
                            cursor.moveToFirst();
                            if (cursor.getCount() == 0) {
//                                Log.e("booo", tempData.get(a).getId_materi());
                                dbHelper.onInsert(db, tempData.get(a).getId_materi(), tempData.get(a).getNama_materi(), "", "", "", tempData.get(a).getJumlah_file(), "");
                            }
                        }

                        if (tempData.size() != data.size()) {
                            GetMateriSQL(1);
                        }

                    }else  {
                        if (flag == 0) {
                            Snackbar.make(findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                    Snackbar.LENGTH_LONG).show();
                        }
                    }
                } else {
                    if (flag == 0) {
                        Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke server",
                                Snackbar.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetValue<Materi>> call, Throwable t) {
                dialog.dismiss();
                if (flag == 0) {
                    Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke server",
                            Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public void GetDetailMateri(final String id, final String nama) {
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
        Call<GetValue<MateriViewer>> call = apiService.getdetailmateri(random, id);
        call.enqueue(new Callback<GetValue<MateriViewer>>() {
            @Override
            public void onResponse(@NonNull Call<GetValue<MateriViewer>> call, @NonNull Response<GetValue<MateriViewer>> response) {
                dialog.dismiss();
                if (response.body() != null) {
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        dataDetail = response.body().getData();
                        counter = 0;
                        showProgress(dataDetail.get(counter).getIsi_materi());
                        new Thread(new Runnable() {
                            public void run() {
                                int pjString = dataDetail.get(counter).getIsi_materi().length();
                                if (dataDetail.get(counter).getIsi_materi().substring(pjString-4, pjString).equals(".pdf")){
                                    downloadFile(id, nama, Utilities.getURLFileMateri()+"berkas/"+dataDetail.get(counter).getIsi_materi(), dataDetail.get(counter).getIsi_materi());

                                }else if (dataDetail.get(counter).getIsi_materi().substring(pjString-4, pjString).equals(".png") || dataDetail.get(counter).getIsi_materi().substring(pjString-4, pjString).equals(".jpg")){
                                    downloadFile(id, nama, Utilities.getURLFileMateri()+"images/"+dataDetail.get(counter).getIsi_materi(), dataDetail.get(counter).getIsi_materi());

                                }else if (dataDetail.get(counter).getIsi_materi().substring(pjString-4, pjString).equals(".mp4")){
                                    downloadFile(id, nama, Utilities.getURLFileMateri()+"video/"+dataDetail.get(counter).getIsi_materi(), dataDetail.get(counter).getIsi_materi());
                                }
                            }
                        }).start();

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
            public void onFailure(@NonNull Call<GetValue<MateriViewer>> call, Throwable t) {
                dialog.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke server",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

    void downloadFile(final String idmateri, final String namamateri, String URL, final String namafile){
//        Log.e("uri", counter+" "+URL+" "+namafile);
        try {
            java.net.URL url = new URL(URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            //connect
            urlConnection.connect();

            //set the path where we want to save the file
//            File SDCardRoot = Environment.getExternalStorageDirectory();
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/mlearning/";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            //create a new file, to save the downloaded file
            File file = new File(dir, namafile);

            FileOutputStream fileOutput = new FileOutputStream(file);

            //Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            //this is the total size of the file which we are downloading
            totalSize = urlConnection.getContentLength();
            runOnUiThread(new Runnable() {
                public void run() {
                    pb.setMax(totalSize);
                }
            });

            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            int downloadedSize = 0;

            while ((bufferLength = inputStream.read(buffer)) > 0 ) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize = downloadedSize + bufferLength;
                // update the progressbar //
                final int finalDownloadedSize = downloadedSize;
                runOnUiThread(new Runnable() {
                    public void run() {
                        text.setText("Downloading " + namafile);
                        pb.setProgress(finalDownloadedSize);
//                        float per = ((float)downloadedSize/totalSize) * 100;
//                        cur_val.setText("Downloaded " + downloadedSize + "byte / " + totalSize + "KB (" + (int)per + "%)" );
                        if (finalDownloadedSize > 1000000){
                            cur_val.setText("Downloaded " + finalDownloadedSize/1000000 + " MB dari " + totalSize/1000000 + " MB");
                        }else if (finalDownloadedSize > 1000) {
                            cur_val.setText("Downloaded " + finalDownloadedSize/1000 + " KB dari " + totalSize/1000 + " KB");
                        }else{
                            if (totalSize > 1000000){
                                cur_val.setText("Downloaded " + finalDownloadedSize + " byte dari " + totalSize/1000000 + " MB");
                            }else if (totalSize > 1000){
                                cur_val.setText("Downloaded " + finalDownloadedSize + " byte dari " + totalSize/1000 + " KB");
                            }else {
                                cur_val.setText("Downloaded " + finalDownloadedSize + " byte dari " + totalSize + "byte");
                            }
                        }
                    }
                });
            }
            //close the output stream when complete //
            fileOutput.close();
            runOnUiThread(new Runnable() {
                public void run() {
//                     pb.dismiss(); // if you want close it..
//                    dialog.dismiss();
                    counter++;
                    if (counter != dataDetail.size()) {
                        new Thread(new Runnable() {
                            public void run() {
//                                downloadFile(idmateri, namamateri, Utilities.getURLFileMateri()+dataDetail.get(counter).getIsi_materi(), dataDetail.get(counter).getIsi_materi());

                                int pjString = dataDetail.get(counter).getIsi_materi().length();
                                if (dataDetail.get(counter).getIsi_materi().substring(pjString-4, pjString).equals(".pdf")){
                                    downloadFile(idmateri, namamateri, Utilities.getURLFileMateri()+"berkas/"+dataDetail.get(counter).getIsi_materi(), dataDetail.get(counter).getIsi_materi());

                                }else if (dataDetail.get(counter).getIsi_materi().substring(pjString-4, pjString).equals(".png") || dataDetail.get(counter).getIsi_materi().substring(pjString-4, pjString).equals(".jpg")){
                                    downloadFile(idmateri, namamateri, Utilities.getURLFileMateri()+"images/"+dataDetail.get(counter).getIsi_materi(), dataDetail.get(counter).getIsi_materi());

                                }else if (dataDetail.get(counter).getIsi_materi().substring(pjString-4, pjString).equals(".mp4")){
                                    downloadFile(idmateri, namamateri, Utilities.getURLFileMateri()+"video/"+dataDetail.get(counter).getIsi_materi(), dataDetail.get(counter).getIsi_materi());
                                }
                            }
                        }).start();
                    }else {
                        DataHelper dbHelper = new DataHelper(MateriActivity.this);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        dbHelper.onDelete(db, idmateri);

                        for (int a = 0; a < dataDetail.size(); a++) {
//                            DataHelper dbHelper = new DataHelper(MateriActivity.this);
//                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            dbHelper.onInsert(db, idmateri, namamateri, dataDetail.get(a).getId_detailmateri(), dataDetail.get(a).getIsi_materi(), dataDetail.get(a).getNama_materi(), String.valueOf(dataDetail.size()), dataDetail.get(a).getNo_urut());
                        }

                        for (Materi materi : data) {
                            if (materi.getId_materi().equals(idmateri)){
                                materi.setCheck("1");
                            }
                        }
                        MateriViewAdapter adapter = new MateriViewAdapter(MateriActivity.this, data);
                        rView.setAdapter(adapter);
                        dialog.dismiss();
                    }
                }
            });

        } catch (final MalformedURLException e) {
            dialog.dismiss();
            showError("Error : MalformedURLException " + e);
            e.printStackTrace();
        } catch (final IOException e) {
            dialog.dismiss();
            showError("Error : IOException " + e);
            e.printStackTrace();
        }
        catch (final Exception e) {
            dialog.dismiss();
            showError("Error : Please check your internet connection " + e);
        }
    }

    void showError(final String err){
        runOnUiThread(new Runnable() {
            public void run() {
                Log.e("error", err);
                Toast.makeText(MateriActivity.this, err, Toast.LENGTH_LONG).show();
            }
        });
    }

    void showProgress(String file_path){
        dialog = new Dialog(MateriActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.myprogressdialog);
        dialog.setTitle("Download Progress");

        text = dialog.findViewById(R.id.tv1);
        text.setText("Downloading " + file_path);
        cur_val = dialog.findViewById(R.id.cur_pg_tv);
        cur_val.setText("Starting download...");
        dialog.setCancelable(false);
        dialog.show();

        pb = dialog.findViewById(R.id.progress_bar);
        pb.setProgress(0);
        pb.setProgressDrawable(getResources().getDrawable(R.drawable.green_progress));
    }
}
