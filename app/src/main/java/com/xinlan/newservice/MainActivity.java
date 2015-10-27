package com.xinlan.newservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText text;
    private Button btn;

    private IWorker mWork = null;

    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mWork = IWorker.Stub.asInterface(binder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("activity on Create  " + android.os.Process.myPid() + "   " + Thread.currentThread());

        this.bindService(new Intent(this, CoreService.class), conn, Context.BIND_AUTO_CREATE);//绑定Service服务

        text = (EditText) findViewById(R.id.text);
        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        moveTaskToBack(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("activity on Destory  " + android.os.Process.myPid() + "   " + Thread.currentThread());
    }

    @Override
    public void onClick(View v) {
        if (mWork != null) {
            System.out.println("on Click  thread-->" + getThreadAndProcess());
            String result = null;
            try {
                result = mWork.doWork(Integer.parseInt(text.getText().toString().trim()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            System.out.println("workfinished  thread-->" + getThreadAndProcess());

            btn.setText("result-->" + result);
        }
    }

    private String getThreadAndProcess() {
        return "Process = " + android.os.Process.myPid() + "  thread " + Thread.currentThread();
    }
}//end class
