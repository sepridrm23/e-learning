package com.alot.elearning;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PdfViewerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        PDFView pdfView = findViewById(R.id.pdfView);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        String name = getIntent().getStringExtra("NAME");
        File dir = new File(path, "/mlearning/"+name);
        pdfView.fromFile(dir).spacing(5).load();

    }
}
