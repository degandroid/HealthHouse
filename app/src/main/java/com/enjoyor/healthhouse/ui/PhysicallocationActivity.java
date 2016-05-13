package com.enjoyor.healthhouse.ui;

import android.os.Bundle;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.custom.PullToRefeshListView;

import butterknife.Bind;

/**
 * Created by YuanYuan on 2016/5/12.
 * 体检点
 */

public class PhysicallocationActivity extends BaseActivity implements PullToRefeshListView.OnRefreshListener {
    @Bind(R.id.physicall_lv)
    PullToRefeshListView physicall_lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.physicallocation_ac_layout);
        initData();
    }

    private void initData() {

    }

    @Override
    public void onRefresh() {

    }
}
