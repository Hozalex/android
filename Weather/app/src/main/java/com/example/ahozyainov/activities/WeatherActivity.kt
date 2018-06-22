package com.example.ahozyainov.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.ahozyainov.activities.R.id.*
import com.example.ahozyainov.common.IntentHelper
import com.example.ahozyainov.models.Cities
import com.example.ahozyainov.models.WeatherDataLoader
import kotlinx.android.synthetic.main.activity_weather.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class WeatherActivity : AppCompatActivity()
{

    private lateinit var lastShare: String
    private lateinit var getIntent: Intent
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        handler = Handler()
        getIntent = intent
        lastShare = resources.getString(R.string.last_share_weather)

        showWeather(getIntent.getStringExtra(IntentHelper.EXTRA_CITY_NAME))
        showForecast()

        share_button.setOnClickListener {
            shareWeather()

        }
    }

    private fun shareWeather()
    {
        var shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.putExtra(Intent.EXTRA_TEXT, text_view_city.text.toString() + ": " +
                text_view_weather.text.toString())
        shareIntent.type = "text/plain"

        if (shareIntent.resolveActivity(packageManager) != null)
        {
            startActivity(shareIntent)
        }

        var sendIntent: Intent = intent
        sendIntent.putExtra(IntentHelper.EXTRA_SHARED_WEATHER, lastShare + " " + text_view_city.text.toString())
        setResult(Activity.RESULT_OK, intent)
    }

    private fun showWeather(cityName: String)
    {
        updateWeatherData(cityName)

    }

    private fun showForecast()
    {
        if (intent.getBooleanExtra(IntentHelper.EXTRA_CHECKBOX_PRESSURE, false))
        {
            text_view_pressure.setBackgroundColor(resources.getColor(R.color.colorBackground))
            text_view_pressure.text = resources.getString(R.string.pressure_value)
        }
        if (intent.getBooleanExtra(IntentHelper.EXTRA_CHECKBOX_TOMORROW, false))
        {
            text_view_tomorrow_forecast.setBackgroundColor(resources.getColor(R.color.colorBackground))
            text_view_tomorrow_forecast.text = resources.getString(R.string.tomorrow_weather)
        }
        if (intent.getBooleanExtra(IntentHelper.EXTRA_CHECKBOX_WEEK, false))
        {
            text_view_week_forecast.setBackgroundColor(resources.getColor(R.color.colorBackground))
            text_view_week_forecast.text = resources.getString(R.string.week_weather)
        }
    }

    private fun updateWeatherData(city: String)
    {
        Thread(Runnable {
            var json: JSONObject? = WeatherDataLoader.getJSONData(city)
            if (json == null)
            {
                handler.post {
                    Toast.makeText(applicationContext, getString(R.string.city_not_found), Toast.LENGTH_LONG).show()
                }
            } else
            {
                handler.post {
                    renderWeather(json)
                }
            }
        }).start()

    }

    @SuppressLint("SetTextI18n")
    private fun renderWeather(json: JSONObject)
    {
        try
        {
            var weatherDescription = json.getJSONArray("weather").getJSONObject(0).getString("main")
            text_view_city.text = json.getString("name").toUpperCase(Locale.US) + ", " +
                    json.getJSONObject("sys").getString("country")
            text_view_weather.text = json.getJSONObject("main").getString("temp") + "\u2103" + " " +
                    json.getJSONArray("weather").getJSONObject(0).getString("description")
            when (weatherDescription)
            {
                "Clear" -> image_weather_activity.setImageResource(R.drawable.sunny)
                "Clouds" -> image_weather_activity.setImageResource(R.drawable.cloudly)
                "Rain" -> image_weather_activity.setImageResource(R.drawable.rainy)
                else -> image_weather_activity.setImageResource(R.drawable.start)
            }
        } catch (e: Exception)
        {
            e.printStackTrace()
        }

    }

}



