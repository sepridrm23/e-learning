package com.alot.elearning;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.VideoView;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MateriViewerActivity extends AppCompatActivity {
    RecyclerView rView;
    String ID;

//    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi_viewer);
        rView = findViewById(R.id.rView);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setHasFixedSize(true);

        ID = getIntent().getStringExtra("ID");

//        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
//        File dir = new File(path, "/tes/Boom.pdf");
//
//        PDFView webView = findViewById(R.id.webView);
//        webView.fromFile(dir).defaultPage(50).load();

        GetMateriDetailSQLite();

//        final VideoView vidView = findViewById(R.id.videoView);
//
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
//        File dir = new File(path);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//
//        String vidAddress = dir+"/tes/Boo.mp4";
//        Log.e("Address", vidAddress);
//        Uri vidUri = Uri.parse(vidAddress);
//        vidView.setVideoURI(vidUri);
//        vidView.start();
//
//        vidView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (vidView.isPlaying()) {
//                    vidView.pause();
//                } else {
//                    vidView.start();
//                }
//                return false;
//            }
//        });

    }

    private void GetMateriDetailSQLite(){
        DataHelper dbHelper = new DataHelper(MateriViewerActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_materi WHERE id_materi = '"+ ID +"' ORDER BY nomor_urut ASC",null);
        cursor.moveToFirst();
        List<MateriViewer> data = new ArrayList<>();
//        if (cursor.getCount() != 0) {
            for (int cc = 0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);
//                Log.e("boo", cursor.getString(3));
                data.add(new MateriViewer(
                        cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(6)
                ));
            }
//        }else {
//
//        }

        MateriViewerViewAdapter adapter = new MateriViewerViewAdapter(MateriViewerActivity.this, data);
        rView.setAdapter(adapter);
    }

//    public void GetDetailMateri() {
//        final ProgressDialog dialog = new ProgressDialog(this);
//        dialog.setMessage("Loading...");
//        dialog.setCancelable(false);
//        dialog.show();
//
//        String random = Utilities.getRandom(5);
//
//        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Utilities.getBaseURLPhp())
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient)
//                .build();
//
//        final APIServices apiService = retrofit.create(APIServices.class);
//        Call<GetValue<MateriViewer>> call = apiService.getdetailmateri(random, ID);
//        call.enqueue(new Callback<GetValue<MateriViewer>>() {
//            @Override
//            public void onResponse(@NonNull Call<GetValue<MateriViewer>> call, @NonNull Response<GetValue<MateriViewer>> response) {
//                dialog.dismiss();
//                if (response.body() != null) {
//                    int success = response.body().getSuccess();
//                    if (success == 1) {
//                        List<MateriViewer> data = response.body().getData();
////                        Log.e("materi", data.get(0).isi_materi);
//                        MateriViewerViewAdapter adapter = new MateriViewerViewAdapter(MateriViewerActivity.this, data);
//                        rView.setAdapter(adapter);
//
////                        for (int a = 0; a < data.size(); a++) {
////                            DataHelper dbHelper = new DataHelper(MateriViewerActivity.this);
////                            SQLiteDatabase db = dbHelper.getWritableDatabase();
////                            dbHelper.onInsert(db, data.get(a).getId_detailmateri(), ID, data.get(a).getIsi_materi(), data.get(a).getNama_materi());
////                        }
//
//                    }else  {
//                        Snackbar.make(findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
//                                Snackbar.LENGTH_LONG).show();
//                    }
//                } else {
//                    Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke server",
//                            Snackbar.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<GetValue<MateriViewer>> call, Throwable t) {
//                dialog.dismiss();
//                Snackbar.make(findViewById(android.R.id.content), "Tidak terhubung ke server",
//                        Snackbar.LENGTH_LONG).show();
//            }
//        });
//    }
}
