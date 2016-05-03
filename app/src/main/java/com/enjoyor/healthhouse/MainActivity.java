package com.enjoyor.healthhouse;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.enjoyor.healthhouse.ui.BaseActivity;

public class MainActivity extends BaseActivity {

    /**
     * 声明一个在按下Back键时的时间
     */
    private long preTime;
    private static final String TAG = "MainActivity";

    //    private String url = "http://115.28.37.145:9008/healthstationserver/health/getrecordnewinfo.action?origin=AndroidApp&userId=35";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setImmerseLayout(findViewById(R.id.common_back));
        //我做了修改



//        User u = new User();
//        u.setId(1);
//        u.setName("张鸿洋");
//
//        new UserDao(this).add(u);
//
//        Log.i("zxw", new UserDao(this).get().getName());
    }

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
