package com.alot.elearning;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.BasePDFPagerAdapter;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import okhttp3.internal.Util;

public class MateriViewerViewAdapter extends RecyclerView.Adapter<MateriViewerViewAdapter.DataObjectHolder>{
    Context mContext;
    private List<MateriViewer> mListData;

    public MateriViewerViewAdapter(Context mContext, List<MateriViewer> mListData) {
        this.mContext = mContext;
        this.mListData = mListData;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_materi_viewer, parent, false);
        return new MateriViewerViewAdapter.DataObjectHolder(view);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onBindViewHolder(final DataObjectHolder holder, int position) {
        final MateriViewer data = mListData.get(position);
        final int pjString = data.getIsi_materi().length();
        if (!data.getNama_materi().isEmpty())
        holder.cover.setText("("+data.getNama_materi()+")\n"+"Tekan untuk melihat");

        if (data.getIsi_materi().substring(pjString-4, pjString).equals(".pdf") || data.getIsi_materi().substring(pjString-4, pjString).equals(".doc")){
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            File dir = new File(path, "/mlearning/"+data.getIsi_materi());

            holder.webView.fromFile(dir).load();
            holder.webView.setVisibility(View.VISIBLE);
            holder.videoView.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.GONE);
        }else if (data.getIsi_materi().substring(pjString-4, pjString).equals(".png") || data.getIsi_materi().substring(pjString-4, pjString).equals(".jpg")){
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            File dir = new File(path, "/mlearning/"+data.getIsi_materi());

            Glide.with(mContext).load(dir).into(holder.imageView);
            holder.webView.setVisibility(View.GONE);
            holder.videoView.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.VISIBLE);
        }else if (data.getIsi_materi().substring(pjString-4, pjString).equals(".mp4")){
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            File dir = new File(path, "/mlearning/"+data.getIsi_materi());
            Uri vidUri = Uri.parse(dir.getAbsolutePath());
            holder.videoView.setVideoURI(vidUri);
//            holder.videoView.start();

//            holder.videoView.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (holder.videoView.isPlaying()) {
//                        holder.videoView.pause();
//                    } else {
//                        holder.videoView.start();
//                    }
//                    return false;
//                }
//            });

            holder.webView.setVisibility(View.GONE);
            holder.videoView.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.GONE);
        }

        holder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getIsi_materi().substring(pjString-4, pjString).equals(".pdf")){
                    Intent intent = new Intent(mContext, PdfViewerActivity.class);
                    intent.putExtra("NAME", data.getIsi_materi());
                    mContext.startActivity(intent);

                }else if (data.getIsi_materi().substring(pjString-4, pjString).equals(".png") || data.getIsi_materi().substring(pjString-4, pjString).equals(".jpg")){
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                    File dir = new File(path, "/mlearning/"+data.getIsi_materi());

                    Intent intent = new Intent(mContext, ImageViewerActivity.class);
                    intent.putExtra("URI", dir.getAbsolutePath());
                    mContext.startActivity(intent);

                }else if (data.getIsi_materi().substring(pjString-4, pjString).equals(".mp4")){
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                    File dir = new File(path, "/mlearning/"+data.getIsi_materi());

                    Intent intent = new Intent(mContext, VideoViewerActivity.class);
                    intent.putExtra("URI", dir.getAbsolutePath());
                    mContext.startActivity(intent);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mListData != null) ? mListData.size() : 0;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        PDFView webView;
        ImageView imageView;
        VideoView videoView;
        TextView cover;
        public DataObjectHolder(View itemView) {
            super(itemView);
            webView = itemView.findViewById(R.id.webView);
            imageView = itemView.findViewById(R.id.imageView);
            videoView = itemView.findViewById(R.id.videoView);
            cover = itemView.findViewById(R.id.cover);

//            Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
//            final DisplayMetrics outMetrics = new DisplayMetrics();
//            display.getMetrics(outMetrics);
//            int viewPagerWidth = Math.round(outMetrics.widthPixels);
//
//            webView.setLayoutParams(new RelativeLayout.LayoutParams(viewPagerWidth, viewPagerWidth));
//            imageView.setLayoutParams(new RelativeLayout.LayoutParams(viewPagerWidth, viewPagerWidth));
//            videoView.setLayoutParams(new RelativeLayout.LayoutParams(viewPagerWidth, viewPagerWidth));
        }
    }
}
