package com.example.timewidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class PowerWidget extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Util.ACTION_BATTERY_CHANGED)) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
            AppWidgetManager awm = AppWidgetManager.getInstance(context);
            refresh(context, awm, awm.getAppWidgetIds(new ComponentName(
                    context, PowerWidget.class)), level, scale);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
            int[] appWidgetIds) {
        Intent service = new Intent(context, TimeService.class);
        context.startService(service);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void refresh(Context context, AppWidgetManager appWidgetManager,
            int[] appWidgetIds, int level, int scale) {
        Paint mPaint = new Paint();
        int color = Color.rgb(255 / 5 * 1, 255 / 5 * 5, 0);
        switch (level / 20) {
        case 0:
            color = Color.rgb(255 / 5 * 5, 255 / 5 * 1, 0);
            break;
        case 1:
            color = Color.rgb(255 / 5 * 4, 255 / 5 * 2, 0);
            break;
        case 2:
            color = Color.rgb(255 / 5 * 3, 255 / 5 * 3, 0);
            break;
        case 3:
            color = Color.rgb(255 / 5 * 2, 255 / 5 * 4, 0);
            break;
        default:
            color = Color.rgb(255 / 5 * 1, 255 / 5 * 5, 0);
            break;
        }
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(2);

        int radius = context.getResources().getDimensionPixelSize(
                R.dimen.power_paint_radius);

        Bitmap bitmap = Bitmap.createBitmap(
                (int) (radius * 2 + mPaint.getStrokeWidth() * 2),
                (int) (radius * 2 + mPaint.getStrokeWidth() * 2),
                Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        RectF oval = new RectF(mPaint.getStrokeWidth(),
                mPaint.getStrokeWidth(), radius * 2, radius * 2);

        float sweepAngle = level / (float) scale * 360;
        canvas.drawArc(oval, 270+360-sweepAngle, sweepAngle, false, mPaint);

        RemoteViews rv = new RemoteViews(context.getPackageName(),
                R.layout.power);
        rv.setTextColor(R.id.power_tv, color);
        rv.setTextViewText(R.id.power_tv,
                String.valueOf((int) (level / (float) scale * 100)));
        rv.setImageViewBitmap(R.id.power_iv, bitmap);
        for (int i : appWidgetIds) {
            appWidgetManager.updateAppWidget(i, rv);
        }
    }
}
