package com.example.timewidget;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class TimeService extends Service {

    private TimeChangedReceiver mTimeChangedReceiver = new TimeChangedReceiver();
    private PowerChangedReceiver mPowerChangedReceiver=new PowerChangedReceiver();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerTime();
        registerPower();
        return super.onStartCommand(intent, flags, startId);
    }

    private void registerTime() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        registerReceiver(mTimeChangedReceiver, filter);
    }
    
    private void registerPower(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mPowerChangedReceiver, filter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mTimeChangedReceiver);
        unregisterReceiver(mPowerChangedReceiver);
        super.onDestroy();
    }

}
