package com.enjoyor.healthhouse.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.custom.Watcher;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/9.
 */
public class RegistActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.navigation_back)
    ImageView navigation_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.registphonenumber)
    EditText registphonenumber;
    @Bind(R.id.regist_password)
    EditText regist_password;
    @Bind(R.id.regist_tv_yanzheng)
    EditText regist_tv_yanzheng;
    @Bind(R.id.regist_yanzheng)
    TextView regist_yanzheng;
    @Bind(R.id.regist)
    Button regist;
    @Bind(R.id.regist_phone_delete)
    ImageView regist_phone_delete;
    @Bind(R.id.regist_pwd_delete)
    ImageView regist_pwd_delete;
    @Bind(R.id.regist_choice)
    CheckBox regist_choice;
    @Bind(R.id.regist_agreement)
    TextView regist_agreement;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_layout);
        ButterKnife.bind(this);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        navigation_back.setOnClickListener(this);
    }

    private void initData() {
        registphonenumber.addTextChangedListener(new Watcher(registphonenumber, regist_phone_delete));
        regist_password.addTextChangedListener(new Watcher(regist_password, regist_pwd_delete));
    }

    private void initView() {
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.navigation_back:
                finish();
                break;
            case R.id.regist_yanzheng:
                break;
            case R.id.regist:
                break;
        }
    }
}
