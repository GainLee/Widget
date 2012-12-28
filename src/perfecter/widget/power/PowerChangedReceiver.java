package com.example.timewidget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PowerChangedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
            intent.setFlags(0);
            intent.setAction(Util.ACTION_BATTERY_CHANGED);
            context.sendBroadcast(intent);
        }
    }
}