package com.example.ahozyainov.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import com.example.ahozyainov.activities.R
import com.example.ahozyainov.models.WeatherDatabaseHelper
import com.example.ahozyainov.services.WeatherDataLoadService


class WidgetWeather : AppWidgetProvider()
{
    private val TAG = "widget"
    private lateinit var dbHelper: WeatherDatabaseHelper

    companion object
    {
        const val UPDATE_WIDGET_ACTION = "android.appwidget.action.APPWIDGET_UPDATE"
    }


    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray)
    {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        for (id in appWidgetIds)
        {
            updateWidget(context, appWidgetManager, id)
        }

    }

    override fun onReceive(context: Context, intent: Intent)
    {
        val mgr = AppWidgetManager.getInstance(context)
        if (intent.action.equals(UPDATE_WIDGET_ACTION, ignoreCase = true)) run {
            val appWidgetIds = mgr.getAppWidgetIds(ComponentName(context, WidgetWeather::class.java))
//            mgr.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view)
        }
    }

    private fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int)
    {
        val remoteViews = RemoteViews(context.packageName, R.layout.widget_layout)
        setData(remoteViews, context, appWidgetId)
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
    }

    private fun setData(remoteViews: RemoteViews, context: Context, appWidgetId: Int)
    {
        dbHelper = WeatherDatabaseHelper(context)
        val intent = Intent(context, WeatherDataLoadService::class.java)
        intent.putExtra("city", dbHelper.getCityName())

    }


}