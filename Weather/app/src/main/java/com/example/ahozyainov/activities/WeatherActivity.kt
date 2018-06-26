package com.example.ahozyainov.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
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
    private var isPressureChecked: Boolean = false
    private var isHumidityChecked: Boolean = false
    private var isWindChecked: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        handler = Handler()
        getIntent = intent
        lastShare = resources.getString(R.string.last_share_weather)

        checkWeatherDetails()
        updateWeatherData(getIntent.getStringExtra(IntentHelper.EXTRA_CITY_NAME))


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

    private fun checkWeatherDetails()
    {
        if (intent.getBooleanExtra(IntentHelper.EXTRA_CHECKBOX_PRESSURE, false))
        {
            isPressureChecked = true
        }
        if (intent.getBooleanExtra(IntentHelper.EXTRA_CHECKBOX_HUMIDITY, false))
        {
            isHumidityChecked = true
        }
        if (intent.getBooleanExtra(IntentHelper.EXTRA_CHECKBOX_WIND, false))
        {
            isWindChecked = true
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
            if (isPressureChecked)
            {
                text_view_pressure.text = "Pressure: " + json.getJSONObject("main").getString("pressure") + " " + "hpa"
                text_view_pressure.setBackgroundColor(this.resources.getColor(R.color.colorBackground))
            }
            if (isHumidityChecked)
            {
                text_view_humidity.text = "Humidity: " + json.getJSONObject("main").getString("humidity") + " " + "\u0025"
                text_view_humidity.setBackgroundColor(this.resources.getColor(R.color.colorBackground))
            }
            if (isWindChecked)
            {
                text_view_wind.text = "Wind: " + json.getJSONObject("wind").getString("speed") + " " + "m/s"
                text_view_wind.setBackgroundColor(this.resources.getColor(R.color.colorBackground))
            }

        } catch (e: Exception)
        {
            e.printStackTrace()
        }

    }

}



