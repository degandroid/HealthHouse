package com.enjoyor.healthhouse.ui;

import android.os.Bundle;

import com.enjoyor.healthhouse.R;

/**
 * Created by Administrator on 2016/5/13.
 */
public class RoundIconActivity extends BaseActivity {

    public static final int FROM_XUEYA = 11;
    public static final int FROM_BMI = 12;
    public static final int FROM_XUETANG = 13;
    public static final int FROM_XUEYANG = 14;
    public static final int FROM_YAOWEI = 21;
    public static final int FROM_DANGUCHUN = 22;
    public static final int FROM_NIAOSUAN = 23;
    public static final int FROM_XINDIAN = 24;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roundicon);

        if(getIntent().hasExtra("fromWhere")){
            int fromWhere = getIntent().getIntExtra("fromWhere",FROM_XUEYA);
            initIconXML(fromWhere);
        }
    }

    private void initIconXML(int fromWhere) {
        switch (fromWhere){
            case FROM_XUEYA:
                break;
        }
    }
}
