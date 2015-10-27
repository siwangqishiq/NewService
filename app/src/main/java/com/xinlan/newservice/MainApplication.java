package com.xinlan.newservice;

import android.app.Application;
import android.content.Intent;

/**
 * Created by Administrator on 2015/10/26.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("main application createed!   "+android.os.Process.myPid()+"   "+Thread.currentThread());


        Intent it = new Intent(this,CoreService.class);
        startService(it);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}//end class
