package com.enjoyor.healthhouse.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.adapter.FragmentTabAdapter;
import com.enjoyor.healthhouse.fragments.MineFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/3.
 */
public class MainTabActivity extends BaseActivity{

    @Bind(R.id.tab_content)
    FrameLayout tab_content;
    @Bind(R.id.main_content_radio)
    RadioGroup main_content_radio;
    @Bind(R.id.main_tab1)
    RadioButton main_tab1;
    @Bind(R.id.main_tab2)
    RadioButton main_tab2;
    @Bind(R.id.main_tab3)
    RadioButton main_tab3;
    @Bind(R.id.main_tab4)
    RadioButton main_tab4;
    @Bind(R.id.main_tab5)
    RadioButton main_tab5;

    private long preTime;
    private List<Fragment> fragment_list = new ArrayList<>();
    public static int CurrentFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        ButterKnife.bind(this);
//        setImmerseLayout(findViewById(R.id.common_back));
//        User u = new User();
//        u.setId(1);
//        u.setName("张鸿洋");
//
//        new UserDao(this).add(u);
//
//        Log.i("zxw", new UserDao(this).get().getName());
        initAdapter();
        initDrawable();
    }

    private void initAdapter() {
        initFragment();
        FragmentTabAdapter tabAdapter = new FragmentTabAdapter(this, fragment_list, R.id.tab_content, main_content_radio);
        tabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener() {
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                CurrentFragment = index;
            }
        });
    }

    private void initFragment() {
        fragment_list.clear();
        fragment_list.add(MineFragment.getInstance(1));
        fragment_list.add(MineFragment.getInstance(2));
        fragment_list.add(MineFragment.getInstance(3));
        fragment_list.add(MineFragment.getInstance(4));
        fragment_list.add(MineFragment.getInstance(5));
    }


    private void initDrawable() {
        int tabIconHeight = getResources().getDimensionPixelOffset(R.dimen.tab_icon_height);
        Drawable topDrawable1 = getResources().getDrawable(R.mipmap.ic_launcher);
        topDrawable1.setBounds(0, 0, tabIconHeight, tabIconHeight);
        main_tab1.setCompoundDrawables(null, topDrawable1, null, null);

        Drawable topDrawable2 = getResources().getDrawable(R.mipmap.ic_launcher);
        topDrawable2.setBounds(0, 0, tabIconHeight, tabIconHeight);
        main_tab2.setCompoundDrawables(null, topDrawable2, null, null);

        Drawable topDrawable3 = getResources().getDrawable(R.drawable.white_null);
        topDrawable3.setBounds(0, 0, tabIconHeight, tabIconHeight);
        main_tab3.setCompoundDrawables(null, topDrawable3, null, null);
//        main_tab3.setVisibility(View.INVISIBLE);

        Drawable topDrawable4 = getResources().getDrawable(R.mipmap.ic_launcher);
        topDrawable4.setBounds(0, 0, tabIconHeight, tabIconHeight);
        main_tab4.setCompoundDrawables(null, topDrawable4, null, null);

        Drawable topDrawable5 = getResources().getDrawable(R.mipmap.ic_launcher);
        topDrawable5.setBounds(0, 0, tabIconHeight, tabIconHeight);
        main_tab5.setCompoundDrawables(null, topDrawable5, null, null);
    }


    /*----------------------------------------双击退出程序----------------------------------------*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - preTime > 2000) {
                Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
            } else {
                this.finish();
                System.exit(0);
            }
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            preTime = System.currentTimeMillis();
        }
        return true;
    }
}
