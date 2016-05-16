package com.enjoyor.healthhouse.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.xk.sanjay.rulberview.RulerWheel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by YuanYuan on 2016/5/13.
 */
public class BPInputActivity extends BaseActivity implements View.OnClickListener {
    private String TAG = this.getClass().getSimpleName();
    @Bind(R.id.navigation_back)
    ImageView navigation_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    TextView bpinput_bp_tv;
    ImageView bpinput_img_up_jian;
    ImageView bpinput_img_up_jia;
    TextView bpinput_bp_tv_low;
    ImageView bpinput_img_low_jian;
    ImageView bpiinput_img_up_jia;
    @Bind(R.id.bpinput_arrow)
    ImageView bpinput_arrow;
    @Bind(R.id.bpinput_arrows)
    ImageView bpinput_arrows;
    @Bind(R.id.bpinput_save)
    TextView bpinput_save;
    RulerWheel bpinput_up;
    RulerWheel bpinput_low;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bpinput_ac_layout);
        setImmerseLayout(findViewById(R.id.navigation));
        Log.d("tag", "create");
        initView();
        initEvent();
    }

    private void initEvent() {
        bpinput_img_up_jian.setOnClickListener(this);
        bpinput_img_up_jia.setOnClickListener(this);
        bpinput_img_low_jian.setOnClickListener(this);
        bpiinput_img_up_jia.setOnClickListener(this);
    }

    private void initView() {
        bpinput_up = (RulerWheel) findViewById(R.id.bpinput_up);
        bpinput_low = (RulerWheel) findViewById(R.id.bpinput_low);
        bpinput_bp_tv = (TextView) findViewById(R.id.bpinput_bp_tv);
        bpinput_bp_tv_low = (TextView) findViewById(R.id.bpinput_bp_tv_low);
        bpinput_img_up_jian = (ImageView) findViewById(R.id.bpinput_img_up_jian);
        bpinput_img_up_jia = (ImageView) findViewById(R.id.bpinput_img_up_jia);
        bpinput_img_low_jian = (ImageView) findViewById(R.id.bpinput_img_low_jian);
        bpiinput_img_up_jia = (ImageView) findViewById(R.id.bpiinput_img_up_jia);
        /**
         * 舒张压刻度尺
         */
        List<String> list = new ArrayList<>();
        for (int i = 0; i <= 250; i += 10) {
            list.add(i + "");
            for (int j = 1; j < 10; j++) {
                list.add((i + j) + "");
            }
        }
        bpinput_bp_tv.setText(80 + "");
        bpinput_up.setData(list);
        bpinput_up.setSelectedValue(80 + "");
        bpinput_up.setScrollingListener(new RulerWheel.OnWheelScrollListener<String>() {

            @Override
            public void onChanged(RulerWheel wheel, String oldValue, String newValue) {
                bpinput_bp_tv.setText(newValue + "");
            }

            @Override
            public void onScrollingStarted(RulerWheel wheel) {

            }

            @Override
            public void onScrollingFinished(RulerWheel wheel) {

            }
        });
        /**
         * 收缩压刻度尺
         */
        bpinput_bp_tv_low.setText(80 + "");
        bpinput_low.setData(list);
        bpinput_low.setSelectedValue(80 + "");
        bpinput_low.setScrollingListener(new RulerWheel.OnWheelScrollListener<String>() {

            @Override
            public void onChanged(RulerWheel wheel, String oldValue, String newValue) {
                bpinput_bp_tv_low.setText(newValue + "");
            }

            @Override
            public void onScrollingStarted(RulerWheel wheel) {

            }

            @Override
            public void onScrollingFinished(RulerWheel wheel) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.bpinput_img_up_jian:
                int s = Integer.parseInt(bpinput_bp_tv.getText().toString());
                updateData(1, s, bpinput_bp_tv, bpinput_up);
                break;
            case R.id.bpinput_img_up_jia:
                int a = Integer.parseInt(bpinput_bp_tv.getText().toString());
                updateData(2, a, bpinput_bp_tv, bpinput_up);
                break;
            case R.id.bpinput_img_low_jian:
                int b = Integer.parseInt(bpinput_bp_tv_low.getText().toString());
                updateData(1, b, bpinput_bp_tv_low, bpinput_low);
                break;
            case R.id.bpiinput_img_up_jia:
                int d = Integer.parseInt(bpinput_bp_tv_low.getText().toString());
                updateData(2, d, bpinput_bp_tv_low, bpinput_low);
                break;
        }
    }

    private void updateData(int i, int str, TextView id, RulerWheel view) {
        if (i == 1) {
            str--;
        } else {
            str++;
        }
        id.setText(str + "");
        view.setSelectedValue(str + "");
        view.invalidate();
    }
}
