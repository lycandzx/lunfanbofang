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
// 空的图片数组
    List<ImageView> images;
//   点的集合
    List<View> dots;
//
    TextView title;
//   适配器
    ViewPageradapter mAdapter;
    ViewPager mViewpager;
//
    String titles[] = new String[]{"第1张", "第2张", "第3张", "第4张", "第5张"};
//   要显示的图片数组
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
//        遍历资源图片数组
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
//            给每个imageview设置资源图片
            imageView.setBackgroundResource(imageIds[i]);
//           给空的图片数组中添加imageview
            images.add(imageView);
        }
        dots = new ArrayList<>();
        dots.add(findViewById(R.id.img1));
        dots.add(findViewById(R.id.img2));
        dots.add(findViewById(R.id.img3));
        dots.add(findViewById(R.id.img4));
        dots.add(findViewById(R.id.img5));
//
        title = (TextView) findViewById(R.id.txt);
//    设置title为第一张
        title.setText(titles[0]);
        mAdapter = new ViewPageradapter();

        mViewpager.setAdapter(mAdapter);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
//当页被选择时
            @Override
            public void onPageSelected(int position) {
//                给textview设置内容
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

//       判断view是否属于object
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

//       初始化，给container中添加view
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(images.get(position));
            return images.get(position);
        }
//销毁从container中移除
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(images.get(position));

        }
    }
//线程池
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
