package com.enjoyor.healthhouse.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.custom.Watcher;
import com.enjoyor.healthhouse.utils.StringUtils;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

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
    private Handler handler;
    private String tellphone;
    private String pwd;
    private int count = 60;//30秒倒计时
    //填写从短信SDK应用后台注册得到的APPKEY
    private static String APPKEY = "12977e0645924";
    //填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "5bc6ab98e28655d3e2b66fe95e257319";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_layout);
        ButterKnife.bind(this);
        initView();
        initData();
        initCode();
        initSDK();
        initEvent();
    }

    private void initCode() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                if (msg.what > 0) {
                    regist_yanzheng.setText(msg.what + "秒");
                }
            }
        };
    }

    private void initEvent() {
        navigation_back.setOnClickListener(this);
        regist_yanzheng.setOnClickListener(this);
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
                if (StringUtils.isBlank(registphonenumber.getText().toString())) {
                    Toast.makeText(RegistActivity.this, "手机号码不能为空", Toast.LENGTH_LONG).show();
                    registphonenumber.requestFocus();
                } else {
                    sendMsg();
                    tellphone = registphonenumber.getText().toString();
                    SMSSDK.getVerificationCode("86", tellphone);
                }
                break;
            case R.id.regist:
                break;
        }
    }

    private void sendMsg() {
        regist_yanzheng.setEnabled(false);
        regist_yanzheng.setText("获取验证码");
        //倒记时
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() { // UI thread
                    @Override
                    public void run() {
                        count--;
                        handler.sendEmptyMessage(count);
                        if (count == 0) {
                            timer.cancel();
                            reSet();
                        }
                    }
                });
            }
        };

        timer.schedule(task, 1000, 1000);
    }

    /**
     * 获取验证码的方法
     */
    private void initSDK() {
        SMSSDK.initSDK(this, APPKEY, APPSECRET, true);
        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                mHandler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh);
    }

    private void reSet() {
        if (tellphone != null && tellphone.equals(registphonenumber.getText().toString().trim())) {
            regist_yanzheng.setText("重新获取");
        } else {
            regist_yanzheng.setText("获取验证码");
        }
        regist_yanzheng.setEnabled(true);
        count = 30;
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event=" + event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                System.out.println("--------result" + event);
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    Toast.makeText(getApplicationContext(), "提交验证码成功", Toast.LENGTH_SHORT).show();

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //已经验证
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                }
            } else {
                int status = 0;
                try {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    JSONObject object = new JSONObject(throwable.getMessage());
                    String des = object.optString("detail");
                    status = object.optInt("status");
                    if (!TextUtils.isEmpty(des)) {
                        Toast.makeText(RegistActivity.this, des, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    SMSLog.getInstance().w(e);
                }
            }
        }
    };
}
