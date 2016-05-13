package com.enjoyor.healthhouse.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean.Article;
import com.enjoyor.healthhouse.bean.Banner;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.ui.RoundIconActivity;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.ScrollListUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

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
public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private static HomeFragment mineFragment;

    public static HomeFragment getInstance(int info) {
        mineFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("info", info + "MineFragment");
        mineFragment.setArguments(bundle);
        return mineFragment;
    }

    @Bind(R.id.tv_right)
    TextView tv_right;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.navigation_back)
    ImageView navigation_back;
    @Bind(R.id.vp_banner)
    ViewPager vp_banner;
    @Bind(R.id.ll_dots)
    LinearLayout ll_dots;
    @Bind(R.id.ll_roundicon1)
    LinearLayout ll_roundicon1;
    @Bind(R.id.ll_roundicon2)
    LinearLayout ll_roundicon2;
    @Bind(R.id.ll_xueya)
    LinearLayout ll_xueya;
    @Bind(R.id.ll_BMI)
    LinearLayout ll_BMI;
    @Bind(R.id.ll_xuetang)
    LinearLayout ll_xuetang;
    @Bind(R.id.ll_xueyang)
    LinearLayout ll_xueyang;
    @Bind(R.id.ll_yaowei)
    LinearLayout ll_yaowei;
    @Bind(R.id.ll_danguchun)
    LinearLayout ll_danguchun;
    @Bind(R.id.ll_niaosuan)
    LinearLayout ll_niaosuan;
    @Bind(R.id.ll_xindian)
    LinearLayout ll_xindian;
    @Bind(R.id.lv_information)
    ListView lv_information;
    private List<ImageView> banner_list;
    private List<ImageView> dot_list;
    private List<Article> article;

    private int currentItem = 5000;//记录上一次点的位置
    private int oldPosition = 0;//存放图片的id
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;

    private String BANNER_URL = "display/index/banner.do";
    private String ARTICLES_URL = "articles/app/index.do";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("随手记");
        tv_right.setOnClickListener(this);
        navigation_name.setText("首页");
        navigation_back.setVisibility(View.INVISIBLE);
        initNetBanner();
        initRoundIcon();
        initArticle();
        return view;
    }

    private void initRoundIcon() {
        for(int i=0;i<ll_roundicon1.getChildCount();i++){
            ll_roundicon1.getChildAt(i).setOnClickListener(this);
        }
        for(int i=0;i<ll_roundicon2.getChildCount();i++){
            ll_roundicon2.getChildAt(i).setOnClickListener(this);
        }
    }

    private void initListView() {
        lv_information.setAdapter(new InformationAdapter());
        ScrollListUtil.setListViewHeightBasedOnChildren(lv_information);
        lv_information.setFocusable(false);
        lv_information.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                initArticle();
            }
        });
    }

    private void initViewPager() {

        adapter = new ViewPagerAdapter();
        vp_banner.setAdapter(adapter);

        vp_banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                dot_list.get(position).setImageResource(R.mipmap.image_indicator_dot_focus);
                dot_list.get(oldPosition).setImageResource(R.mipmap.image_indicator_dot);
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

    private void getBannerDate() {
        dot_list = new ArrayList<>();
        for (int i = 0; i < banner_list.size(); i++) {
            ImageView view = new ImageView(getActivity());
            view.setImageResource(R.mipmap.image_indicator_dot);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(2, 0, 2, 0);
            view.setLayoutParams(lp);
            ll_dots.addView(view);
            dot_list.add(view);
        }
        if (dot_list.size() > 0) {
            dot_list.get(0).setImageResource(R.mipmap.image_indicator_dot_focus);
        }
        initViewPager();
    }

    private void initArticle() {
        RequestParams params = new RequestParams();
        AsyncHttpUtil.get(UrlInterface.TEXT_URL + ARTICLES_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    article = JsonHelper.getArrayJson(apiMessage.Data, Article.class);
                    for (int n = 0; n < article.size(); n++) {
                        Log.i("zxw", "article----------------------" + article.get(n).getTitle());
                    }
                    if (article.size() > 0) {
                        initListView();
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
    }

    private void initNetBanner() {
        RequestParams params = new RequestParams();
        AsyncHttpUtil.get(UrlInterface.TEXT_URL + BANNER_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    List<Banner> banners = JsonHelper.getArrayJson(apiMessage.Data, Banner.class);
                    banner_list = new ArrayList<ImageView>();
                    for (int j = 0; j < banners.size(); j++) {
                        String picUrl = UrlInterface.FILE_URL + banners.get(j).getFilePath();
                        ImageView imageView = new ImageView(getActivity());
                        Glide.with(getActivity()).load(picUrl).into(imageView);
                        banner_list.add(imageView);
                    }
                    getBannerDate();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

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
            currentItem = (currentItem + 1) % banner_list.size();
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
            return article.size();
        }

        @Override
        public Object getItem(int position) {
            return article.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_homefragment, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_tittle.setText(article.get(position).getTitle());
            holder.tv_date.setText(article.get(position).getModifyTime());
            holder.tv_readnumber.setText("" + article.get(position).getPageViews());
//            holder.tv_comment.setText("comment");
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

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.tv_right:
                break;
            case R.id.ll_xueya:
                Intent intent_xueya = new Intent(getActivity(), RoundIconActivity.class);
                intent_xueya.putExtra("fromWhere",RoundIconActivity.FROM_XUEYA);
                startActivity(intent_xueya);
                break;
            case R.id.ll_BMI:
                Intent intent_BMI = new Intent(getActivity(), RoundIconActivity.class);
                intent_BMI.putExtra("fromWhere",RoundIconActivity.FROM_BMI);
                startActivity(intent_BMI);
                break;
            case R.id.ll_xuetang:
                Intent intent_xuetang = new Intent(getActivity(), RoundIconActivity.class);
                intent_xuetang.putExtra("fromWhere",RoundIconActivity.FROM_XUETANG);
                startActivity(intent_xuetang);
                break;
            case R.id.ll_xueyang:
                Intent intent_xueyang = new Intent(getActivity(), RoundIconActivity.class);
                intent_xueyang.putExtra("fromWhere",RoundIconActivity.FROM_XUEYANG);
                startActivity(intent_xueyang);
                break;
            case R.id.ll_yaowei:
                Intent intent_yaowei = new Intent(getActivity(), RoundIconActivity.class);
                intent_yaowei.putExtra("fromWhere",RoundIconActivity.FROM_YAOWEI);
                startActivity(intent_yaowei);
                break;
            case R.id.ll_danguchun:
                Intent intent_danguchun = new Intent(getActivity(), RoundIconActivity.class);
                intent_danguchun.putExtra("fromWhere",RoundIconActivity.FROM_DANGUCHUN);
                startActivity(intent_danguchun);
                break;
            case R.id.ll_niaosuan:
                Intent intent_niaosuan = new Intent(getActivity(), RoundIconActivity.class);
                intent_niaosuan.putExtra("fromWhere",RoundIconActivity.FROM_NIAOSUAN);
                startActivity(intent_niaosuan);
                break;
            case R.id.ll_xindian:
                Intent intent_xindian = new Intent(getActivity(), RoundIconActivity.class);
                intent_xindian.putExtra("fromWhere",RoundIconActivity.FROM_XINDIAN);
                startActivity(intent_xindian);
                break;
        }
    }
}

