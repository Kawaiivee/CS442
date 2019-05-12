package com.christopherhield.a_widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.RemoteViews;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Widget extends AppWidgetProvider {

    private static final String TAG = "Widget";
    public static final String WIDGET_CLICKED = "WIDGET_CLICKED";
    public static final String WIDGET_TIMER_UPDATE = "WIDGET_TIMER_UPDATE";
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        // Intent action is APPWIDGET_ENABLED when widget is placed on a screen
        // Intent action is APPWIDGET_UPDATE when android calls for a widget update
        // Intent action is APPWIDGET_DELETED when widget is removed from the screen
        // Intent action is WIDGET_CLICKED when widget is touched on the screen
        // Intent action is WIDGET_TIMER_UPDATE when widget updated due to the timer

        Log.d(TAG, "onReceive: Type of message received: " + intent.getAction());

        this.context = context;

        super.onReceive(context, intent);
        if (intent.getAction() == null)
            return;
        if (intent.getAction().equals(WIDGET_CLICKED) ||
                intent.getAction().equals(WIDGET_TIMER_UPDATE) ||
                intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATE")) {

            // Start async task to get weather data
            AsyncLoaderTask atl = new AsyncLoaderTask(this);
            atl.execute("Chicago");
        }
    }


    public void updateData(int t, Bitmap bm) {
        Log.d(TAG, "updateData (called from AsyncTask): Temp: " + t + ", Bitmap: " + bm.getWidth() + "x" + bm.getHeight());

        // Get Widget XML layout
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        ComponentName componentName = new ComponentName(context, Widget.class);

        // Update the widget layout elements
        remoteViews.setTextViewText(R.id.appwidget_text, String.format(Locale.getDefault(), "%d°", t));
        Log.d(TAG, "updateData: Updated temp TextView to " + String.format("%d°", t));
        remoteViews.setImageViewBitmap(R.id.imageView2, bm);
        Log.d(TAG, "updateData: Updated icon ImageView to " + bm.toString());
        String timeStamp = timeFormat.format(new Date());
        remoteViews.setTextViewText(R.id.time_text, timeStamp);
        Log.d(TAG, "updateData: Updated time ImageView to " + timeStamp);

        // Set PendingIntent for the next click
        Intent intent = new Intent(context, Widget.class);
        intent.setAction(WIDGET_CLICKED);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.layout, pi);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetManager.updateAppWidget(componentName, remoteViews);

    }

    @Override
    public void onEnabled(Context context) {
        Log.d(TAG, "onEnabled: ");
        // Setup timer update
        Util.scheduleUpdate(context);
    }

    @Override
    public void onDisabled(Context context) {
        Log.d(TAG, "onDisabled: ");
        // Cancel timer updates
        Util.clearTimeUpdate(context);
        super.onDisabled(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.d(TAG, "onDeleted: ");
        // Cancel tmier updates
        Util.clearTimeUpdate(context);
        super.onDeleted(context, appWidgetIds);
    }

}

