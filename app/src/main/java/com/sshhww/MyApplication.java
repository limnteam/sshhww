package com.sshhww;

import android.app.Application;

import com.yanzhenjie.nohttp.NoHttp;

/**
 * author： xiongdejin
 * date: 2018/4/19
 * describe:
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.initialize(this);
    }
}
