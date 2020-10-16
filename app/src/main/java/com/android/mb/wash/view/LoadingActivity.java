package com.android.mb.wash.view;


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.android.mb.wash.R;
import com.android.mb.wash.entity.CurrentUser;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.utils.ProjectHelper;


/**
 * 引导页
 * @author cgy
 */

public class LoadingActivity extends AppCompatActivity {
    private static final int LOADING_TIME_OUT = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 去除信号栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        findViewById(R.id.ll_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        new Handler().postDelayed(new Runnable() {

            public void run() {
                if (CurrentUser.getInstance().isLogin()){
                    NavigationHelper.startActivity(LoadingActivity.this, MainActivity.class, null, true);
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isReLogin", true);
                    NavigationHelper.startActivity(LoadingActivity.this, LoginActivity.class,bundle,false);
                }
            }

        }, LOADING_TIME_OUT);
    }


}
