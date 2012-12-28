package com.example.timewidget;

import java.util.TimeZone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;

public class TimeChangedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_TIME_CHANGED)
                || intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
            context.sendBroadcast(new Intent(Util.ACTION_TIME_CHANGED));
        }
        
        if(intent.getAction().equals(Intent.ACTION_TIMEZONE_CHANGED)){
            intent.setAction(Util.ACTION_TIMEZONE_CHANGED);
            context.sendBroadcast(intent);
        }
    }
}
