package com.xinlan.newservice;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.test.ApplicationTestCase;

/**
 * Created by panyi on 2015/10/26.
 */
public class CoreService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println(this.getClass().getName() + "   on Create " + getThreadAndProcess());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println(this.getClass().getName() + "   on onStartCommand" + getThreadAndProcess());
//        try {
//            Thread.sleep(30*1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return START_NOT_STICKY;
    }

    private String getThreadAndProcess() {
        return "Process = " + android.os.Process.myPid() + "  thread " + Thread.currentThread();
    }


    private IWorker.Stub mBinder = new IWorker.Stub() {
        @Override
        public String doWork(int n) throws RemoteException {
            System.out.println("core service start work ...");
            System.out.println("thread-->" + getThreadAndProcess());
            int sum = 0;
            for (int i = 0; i <= n; i++) {
                sum += i;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(getThreadAndProcess()+"    "+i+"/"+n+"...    ");
            }//end for i
            return sum+"";
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println(this.getClass().getName() + "   on Destory " + getThreadAndProcess());
    }

}//end class
