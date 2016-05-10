package com.enjoyor.healthhouse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean.UserLogin;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.StringUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * Created by Administrator on 2016/5/9.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.et_phonenumber)
    EditText et_phonenumber;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.bt_login)
    Button bt_login;
    @Bind(R.id.tv_forget_password)
    TextView tv_forget_password;
    @Bind(R.id.tv_login_quick)
    TextView tv_login_quick;

    private String LOGIN_URL = "account/login.action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initOnClick();
    }

    private void initOnClick() {
        et_phonenumber.setOnClickListener(this);
        et_password.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        tv_forget_password.setOnClickListener(this);
        tv_login_quick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.et_phonenumber:
                break;

            case R.id.et_password:
                break;

            case R.id.bt_login:
                if(isCorrect()){
                    initData();
                }
                break;

            case R.id.tv_forget_password:
                if(isCorrect()){
                    Intent intent = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.tv_login_quick:

                    Intent intent = new Intent(LoginActivity.this,RegistActivity.class);
                    startActivity(intent);

//                dialog(LoginActivity.this, "该用户已注册","取消","登陆", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        disappear();
//                    }
//                }, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        disappear();
//                    }
//                });
                break;
        }
    }

    private boolean isCorrect() {
        String phoneNumber = et_phonenumber.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        if (StringUtils.isBlank(phoneNumber)){
            Toast.makeText(LoginActivity.this,"手机号码不能为空",Toast.LENGTH_LONG).show();
            et_phonenumber.requestFocus();
            return false;
        }else if(StringUtils.isBlank(password)){
            Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_LONG).show();
            et_password.requestFocus();
            return false;
        }
        return true;
    }


    private void initData() {
        RequestParams params = new RequestParams();
        params.add("origin", String.valueOf("AndroidApp"));
        params.add("userLoginName", String.valueOf("13399320686"));
        params.add("userLoginPwd", String.valueOf("1324"));
        params.add("userLoginType", String.valueOf("2"));
        params.add("userLoginFrom", String.valueOf("1"));
        AsyncHttpUtil.post(UrlInterface.TEXT_URL+LOGIN_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    UserLogin _UserLogin = JsonHelper.getJson(apiMessage.Data, UserLogin.class);
//                    Log.i("zxw",_UserLogin.getAccountId()+"--------------"+_UserLogin.getLoginName());
                    Intent intent = new Intent(LoginActivity.this,MainTabActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }
}
