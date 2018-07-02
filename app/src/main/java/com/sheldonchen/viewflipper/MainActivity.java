package com.sheldonchen.viewflipper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    private final int[] mResIds = {
            R.mipmap.pic0,
            R.mipmap.pic1,
            R.mipmap.pic2,
            R.mipmap.pic3,
            R.mipmap.pic4,
            R.mipmap.pic5,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SlideMoreViewPager viewPager = findViewById(R.id.vp);
        viewPager.setAdapter(new SlidePagerAdapter());
        viewPager.setOnFindMoreListener(new SlideMoreViewPager.OnFindMoreListener() {
            @Override
            public void onFindMore() {
                Toast.makeText(MainActivity.this, "find more!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class SlidePagerAdapter extends PagerAdapter {

        final Queue<ImageView> caches = new LinkedList<>();

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView convertView = caches.poll();
            if(convertView == null) {
                convertView = new ImageView(container.getContext());
                convertView.setScaleType(ImageView.ScaleType.FIT_XY);
                convertView.setLayoutParams(new ViewPager.LayoutParams());
            }

            convertView.setImageResource(mResIds[position]);
            container.addView(convertView);
            return convertView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            if(object instanceof ImageView) {
                ImageView imageView = (ImageView) object;
                container.removeView(imageView);
                caches.add(imageView);
            }
        }

        @Override
        public int getCount() {
            return mResIds.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}
