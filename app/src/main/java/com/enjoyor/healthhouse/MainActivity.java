package com.enjoyor.healthhouse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.enjoyor.healthhouse.ui.BaseActivity;
import com.enjoyor.healthhouse.ui.LoginActivity;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setImmerseLayout(findViewById(R.id.common_back));
        Handler x = new Handler();
        x.postDelayed(new splashhandler(), 2000);

    }

    class splashhandler implements Runnable {

        public void run() {
            startActivity(new Intent(getApplication(), LoginActivity.class));
            MainActivity.this.finish();
        }
    }
}