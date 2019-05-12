package com.example.chield.myapplication;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class ClickTimer extends AppWidgetProvider {

    private static final String TAG = "ClickTimer";
    public static final String WIDGET_WAS_CLICKED = "WIDGET_WAS_CLICKED";
    private static long lastTime = System.currentTimeMillis();

    @Override
    public void onReceive(Context context, Intent intent) {

        // Intent action is APPWIDGET_ENABLED when widget is placed on a screen
        // Intent action is APPWIDGET_UPDATE when android calls for a widget update
        // Intent action is APPWIDGET_DELETED when widget is removed from the screen
        // Intent action is WIDGET_WAS_CLICKED when widget is touched on the screen

        Log.d(TAG, "onReceive: ");
        super.onReceive(context, intent);

        // If the widget was touched by a user or android calls for a widget update...
        if (intent.getAction().equals(WIDGET_WAS_CLICKED) ||
                intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATE")) {

            long time = System.currentTimeMillis();
            long diff = time - lastTime;
            String formatted = String.format("%.3f sec", diff / 1000.0);

            lastTime = time;

            // Get the widget view
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.click_timer);

            // Set the appwidget_text TextView to the String we just created
            remoteViews.setTextViewText(R.id.appwidget_text, formatted);

            // We need the component name to have the AppWidgetManager perform an update (below)
            ComponentName componentName = new ComponentName(context, ClickTimer.class);

            // Set up pending intent for next click
            Intent newIntent = new Intent(context, ClickTimer.class);
            newIntent.setAction(WIDGET_WAS_CLICKED);
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, newIntent, 0);

            // Set the PendingIntent to execute next time someonetouches the widget
            remoteViews.setOnClickPendingIntent(R.id.layout, pi);

            // Tell the AppWidgetManager to do an update
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(componentName, remoteViews);
        }
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate: ");
        // Auto-called when app is touched

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Log.d(TAG, "updateAppWidget: ");
        CharSequence widgetText = context.getString(R.string.appwidget_text);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.click_timer);
        views.setTextViewText(R.id.appwidget_text, widgetText);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onEnabled(Context context) {
        // Auto-called when the widget is placed on a screen
        Log.d(TAG, "onEnabled: ");
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.d(TAG, "onDisabled: ");
    }
}

