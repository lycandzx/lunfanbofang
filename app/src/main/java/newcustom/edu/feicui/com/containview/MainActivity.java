package newcustom.edu.feicui.com.containview;

import android.icu.util.TimeUnit;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MainActivity extends AppCompatActivity {
    List<ImageView> images;
    List<View> dots;
    TextView title;
    ViewPageradapter mAdapter;
    ViewPager mViewpager;
    String titles[] = new String[]{"第1张", "第2张", "第3张", "第4张", "第5张"};
    int[] imageIds = new int[]{R.mipmap.i, R.mipmap.k, R.mipmap.m, R.mipmap.n, R.mipmap.q};
    ScheduledExecutorService mScheduled;
    public int oldPosition = 0;
    public int currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        oninit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mScheduled = Executors.newSingleThreadScheduledExecutor();
        mScheduled.scheduleWithFixedDelay(new ViewPagertask(), 2, 2, java.util.concurrent.TimeUnit.SECONDS);
    }

    private void oninit() {
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        images = new ArrayList<>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        dots = new ArrayList<>();
        dots.add(findViewById(R.id.img1));
        dots.add(findViewById(R.id.img2));
        dots.add(findViewById(R.id.img3));
        dots.add(findViewById(R.id.img4));
        dots.add(findViewById(R.id.img5));
        title = (TextView) findViewById(R.id.txt);
        title.setText(titles[0]);
        mAdapter = new ViewPageradapter();
        mViewpager.setAdapter(mAdapter);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                title.setText(titles[position]);
                dots.get(position).setBackgroundResource(R.mipmap.normal);
                dots.get(oldPosition).setBackgroundResource(R.mipmap.def);
                oldPosition = position;
                currentItem = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public class ViewPageradapter extends PagerAdapter {
        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(images.get(position));
            return images.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(images.get(position));

        }
    }

    public class ViewPagertask implements Runnable {

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            handle.sendEmptyMessage(1);
        }

        Handler handle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mViewpager.setCurrentItem(currentItem);
            }
        };
    }

}
