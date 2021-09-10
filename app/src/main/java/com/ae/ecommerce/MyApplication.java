package com.ae.ecommerce;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.ae.ecommerce.utils.SingletonClass;


public class MyApplication extends android.app.Application {
    public static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SingletonClass.getInstance();
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    public static MyApplication getInstance() {
        return instance;
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
