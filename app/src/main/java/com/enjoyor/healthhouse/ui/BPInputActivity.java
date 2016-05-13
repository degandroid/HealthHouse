package com.enjoyor.healthhouse.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;

import butterknife.Bind;

/**
 * Created by YuanYuan on 2016/5/13.
 */
public class BPInputActivity extends BaseActivity {
    @Bind(R.id.navigation_back)
    ImageView navigation_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.bpinput_bp_tv)
    TextView bpinput_bp_tv;
    @Bind(R.id.bpinput_img_up_jian)
    ImageView bpinput_img_up_jian;
    @Bind(R.id.bpinput_img_up_jia)
    ImageView bpinput_img_up_jia;
    @Bind(R.id.bpinput_bp_tv_low)
    TextView bpinput_bp_tv_low;
    @Bind(R.id.bpinput_img_low_jian)
    ImageView bpinput_img_low_jian;
    @Bind(R.id.bpiinput_img_up_jia)
    ImageView bpiinput_img_up_jia;
    @Bind(R.id.bpinput_arrow)
    ImageView bpinput_arrow;
    @Bind(R.id.bpinput_arrows)
    ImageView bpinput_arrows;
    @Bind(R.id.bpinput_save)
    Button bpinput_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bpinput_ac_layout);
        setImmerseLayout(findViewById(R.id.navigation));
    }
}
