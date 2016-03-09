package com.sprungsolutions.sitorstart;

import android.app.Application;

/**
 * Created by arisprung on 3/9/16.
 */
public class SitOrStartApplication extends Application {
    private SSSharedPreferencesManager ssPrefrenceManager;
    @Override
    public void onCreate() {
        super.onCreate();
        ssPrefrenceManager = SSSharedPreferencesManager.getInstance(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ssPrefrenceManager.removeKey(SSSharedPreferencesManager.START_OR_SIT_SET_ID);
    }
}
