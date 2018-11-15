package com.alot.elearning;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

@SuppressLint("Registered")
public class ImageViewerActivity extends AppCompatActivity {
    String uri = "";
    private List<String> mPaths = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        uri = getIntent().getStringExtra("URI");
        mPaths.add(uri);

        HackyViewPager viewPager = findViewById(R.id.imageView);
        viewPager.setAdapter(new SamplePagerAdapter());
    }

    private class SamplePagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return mPaths.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            Glide.with(container.getContext())
                    .load(mPaths.get(position))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(photoView);

            container.addView(photoView, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
