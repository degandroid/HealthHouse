package com.enjoyor.healthhouse.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/3.
 */
public class HomeFragment extends Fragment {

    @Bind(R.id.lv_information)
    ListView lv_information;
    @Bind(R.id.vp_banner)
    ViewPager vp_banner;
    @Bind(R.id.dot_0)
    View dot_0;
    @Bind(R.id.dot_1)
    View dot_1;
    @Bind(R.id.dot_2)
    View dot_2;

    private List<ImageView> banner_list;
    private List<View> dot_list;
    private int currentItem = 5000;
    //记录上一次点的位置
    private int oldPosition = 0;
    //存放图片的id
    private int[] imageIds = new int[]{
            R.mipmap.bl_banner1,
            R.mipmap.bl_banner2,
            R.mipmap.bl_banner3,
    };
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;

    private static HomeFragment mineFragment;

    public static HomeFragment getInstance(int info) {
        mineFragment = new HomeFragment();

        Bundle bundle = new Bundle();
        bundle.putString("info", info + "MineFragment");
        mineFragment.setArguments(bundle);

        return mineFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        initViewPager(view);
        initListView(view);

        return view;
    }

    private void initListView(View view) {


        lv_information.setAdapter(new InformationAdapter());
        lv_information.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void initViewPager(View view) {
        banner_list = new ArrayList<ImageView>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageIds[i]);
            banner_list.add(imageView);
        }
        //显示的小点
        dot_list = new ArrayList<View>();
        dot_list.add(dot_0);
        dot_list.add(dot_1);
        dot_list.add(dot_2);
        adapter = new ViewPagerAdapter();
        vp_banner.setAdapter(adapter);

        vp_banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageSelected(int position) {
                dot_list.get(position).setBackgroundResource(R.mipmap.image_indicator_dot_focus);
                dot_list.get(oldPosition).setBackgroundResource(R.mipmap.image_indicator_dot);

                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return banner_list.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
//			super.destroyItem(container, position, object);
//			view.removeView(view.getChildAt(position));
//			view.removeViewAt(position);
            view.removeView(banner_list.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            view.addView(banner_list.get(position));
            return banner_list.get(position);
        }

    }

    private class ViewPageTask implements Runnable {

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            vp_banner.setCurrentItem(currentItem);
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPageTask(), 1, 3, TimeUnit.SECONDS);
    }

    public class InformationAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_homefragment,null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_tittle.setText("tittle");
            holder.tv_date.setText("date");
            holder.tv_readnumber.setText("number");
            holder.tv_comment.setText("comment");
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.iv_infopic)
            ImageView iv_infopic;
            @Bind(R.id.tv_tittle)
            TextView tv_tittle;
            @Bind(R.id.tv_date)
            TextView tv_date;
            @Bind(R.id.tv_readnumber)
            TextView tv_readnumber;
            @Bind(R.id.tv_comment)
            TextView tv_comment;
            ViewHolder(View view){
                ButterKnife.bind(this,view);
            }
        }

    }
}

