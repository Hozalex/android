package com.example.ahozyainov.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.ahozyainov.models.WeatherDatabaseHelper
import com.example.ahozyainov.services.WeatherDataLoadService


class WidgetWeather : AppWidgetProvider()
{
    private val TAG = "widget"
    private lateinit var dbHelper: WeatherDatabaseHelper


    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray)
    {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        for (id in appWidgetIds)
        {
            updateWidget(context)
            Log.d(TAG, "Update $id")
        }

    }

    private fun updateWidget(context: Context)
    {
        dbHelper = WeatherDatabaseHelper(context)
        val serviceIntent = Intent(context, WeatherDataLoadService::class.java)
        serviceIntent.putExtra("city", dbHelper.getCityName())
        context.startService(serviceIntent)
    }


}