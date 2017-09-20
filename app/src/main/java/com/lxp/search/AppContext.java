package com.lxp.search;

import android.app.Application;
import android.content.Context;

/**
 * 作者：lixiaopeng on 2017/9/20 0020.
 * 邮箱：lixiaopeng186@163.com
 * 描述：
 */

public class AppContext extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
    public static Context getInstance(){
        return context;
    }
}
