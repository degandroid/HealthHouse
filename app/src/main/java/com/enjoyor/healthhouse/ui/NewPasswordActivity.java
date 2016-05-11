package com.enjoyor.healthhouse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.utils.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/9.
 */
public class NewPasswordActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.et_newpassword)EditText et_newpassword;
    @Bind(R.id.et_again_newpassword)EditText et_again_newpassword;
    @Bind(R.id.bt_commit)Button bt_commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpassword);
        ButterKnife.bind(this);
        initOnClick();
    }
    private void initOnClick() {
        et_newpassword.setOnClickListener(this);
        et_again_newpassword.setOnClickListener(this);
        bt_commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key){
            case R.id.bt_commit:
                if(isCorrect()){
                    Intent intent = new Intent(NewPasswordActivity.this,MainTabActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }
    private boolean isCorrect() {
        String newPwd = et_newpassword.getText().toString().trim();
        String newAgainPwd = et_again_newpassword.getText().toString().trim();
        if (StringUtils.isBlank(newPwd)){
            Toast.makeText(NewPasswordActivity.this, "新密码不能为空", Toast.LENGTH_LONG).show();
            et_newpassword.requestFocus();
            return false;
        }else if(StringUtils.isBlank(newAgainPwd)){
            Toast.makeText(NewPasswordActivity.this,"两次密码输入不匹配",Toast.LENGTH_LONG).show();
            et_again_newpassword.requestFocus();
            return false;
        }
        return true;
    }


}
