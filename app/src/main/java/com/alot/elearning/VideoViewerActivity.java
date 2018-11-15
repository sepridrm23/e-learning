package com.alot.elearning;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;

@SuppressLint("Registered")
public class VideoViewerActivity extends AppCompatActivity implements EasyVideoCallback {
    EasyVideoPlayer videoView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_viewer);
        videoView = findViewById(R.id.videoView);

        String uri = getIntent().getStringExtra("URI");
        Uri vidUri = Uri.parse(uri);

        videoView.setSource(vidUri);
        videoView.setCallback(this);
//        videoView.start();

//        videoView.setVideoURI(vidUri);
//        videoView.start();

//        videoView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (videoView.isPlaying()) {
//                    videoView.pause();
//                } else {
//                    videoView.start();
//                }
//                return false;
//            }
//        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    public void onStarted(EasyVideoPlayer player) {
//        player.start();
    }

    @Override
    public void onPaused(EasyVideoPlayer player) {
//        player.pause();
    }

    @Override
    public void onPreparing(EasyVideoPlayer player) {
        Log.d("EVP-Sample", "onPreparing()");
    }

    @Override
    public void onPrepared(EasyVideoPlayer player) {
        Log.d("EVP-Sample", "onPrepared()");
    }

    @Override
    public void onBuffering(int percent) {

    }

    @Override
    public void onError(EasyVideoPlayer player, Exception e) {
        Log.d("EVP-Sample", "onError(): " + e.getMessage());
    }

    @Override
    public void onCompletion(EasyVideoPlayer player) {
        Log.d("EVP-Sample", "onCompletion()");
    }

    @Override
    public void onRetry(EasyVideoPlayer player, Uri source) {
        Toast.makeText(this, "Retry", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSubmit(EasyVideoPlayer player, Uri source) {

    }
}
