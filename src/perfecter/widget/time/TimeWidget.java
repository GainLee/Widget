
package com.example.timewidget;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.widget.RemoteViews;

public class TimeWidget extends AppWidgetProvider {

    private Time mCalendar=null;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Util.ACTION_TIMEZONE_CHANGED)) {
            String tz = intent.getStringExtra("time-zone");
            mCalendar = new Time(TimeZone.getTimeZone(tz).getID());
        }

        if(mCalendar==null){
            mCalendar=new Time();
        }
        mCalendar.setToNow();
        AppWidgetManager awm=AppWidgetManager.getInstance(context);
        int[] appWidgetIds=awm.getAppWidgetIds(new ComponentName(context, TimeWidget.class));
        refresh(context,awm,appWidgetIds);
        
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
            int[] appWidgetIds) {
        Log.d("perfecter", "onUpdate");
        Intent service=new Intent(context,TimeService.class);
        context.startService(service);
        refresh(context,appWidgetManager,appWidgetIds);
                super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void refresh(Context context, AppWidgetManager appWidgetManager,
            int[] appWidgetIds){
        Log.d("perfecter", "refresh");
        if(mCalendar==null){
            mCalendar=new Time();
        }
        mCalendar.setToNow();
        String yymmdd=mCalendar.year+"/"+(mCalendar.month+1)+"/"+mCalendar.monthDay;
        String hhmmss=mCalendar.hour+":"+mCalendar.minute;
        int week=mCalendar.weekDay;
        String weekStr=null;
        switch (week) {
        case Time.MONDAY:
            weekStr="Monday";
            break;
        case Time.TUESDAY:
            weekStr="Tuesday";
            break;
        case Time.WEDNESDAY:
            weekStr="Wednesday";
            break;
        case Time.THURSDAY:
            weekStr="Thursday";
            break;
        case Time.FRIDAY:
            weekStr="Friday";
            break;
        case Time.SATURDAY:
            weekStr="Saturday";
            break;
        case Time.SUNDAY:
            weekStr="Sunday";
            break;
        }
        
        RemoteViews rv=new RemoteViews(context.getPackageName(), R.layout.activity_main);
                rv.setTextViewText(R.id.yymmdd, yymmdd);
                rv.setTextViewText(R.id.hhmmss, hhmmss);
                rv.setTextViewText(R.id.weekday, weekStr);
                for(int i:appWidgetIds){
                    appWidgetManager.updateAppWidget(i, rv);
                }
    }
}
