package com.example.codestack.step_counter;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/*
//Creating the services
public class MyServices extends IntentService {
    //@androidx.annotation.Nullable
    int mStartMode;
    IBinder iBinder;
    boolean mAllowRebind;


    public MyServices(String name) {
        super(name);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent intent)
    {
        return null;
    }

    protected void onHandleIntent() {

    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
*/



public class MyServices extends IntentService{


    public MyServices() {
        super("my_intent_thread");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {


    }
}


