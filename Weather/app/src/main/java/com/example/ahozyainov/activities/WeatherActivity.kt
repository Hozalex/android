package com.example.ahozyainov.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.os.*
import android.support.annotation.UiThread
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.example.ahozyainov.activities.R.id.*
import com.example.ahozyainov.activities.R.string.last_share_weather
import com.example.ahozyainov.activities.R.string.pressure
import com.example.ahozyainov.common.IntentHelper
import com.example.ahozyainov.models.WeatherDataLoader
import com.example.ahozyainov.models.WeatherDatabaseHelper
import com.example.ahozyainov.services.WeatherDataLoadService
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.activity_weather.view.*
import org.json.JSONObject
import java.util.*

class WeatherActivity : AppCompatActivity()
{

    private lateinit var lastShare: String
    private lateinit var getIntent: Intent
    private var isPressureChecked: Boolean = false
    private var isHumidityChecked: Boolean = false
    private var isWindChecked: Boolean = false
    private lateinit var broadcastReceiver: BroadcastReceiver


    companion object
    {
        const val BROADCAST_ACTION = "com.example.ahozyainov.services.RESPONSE"

    }


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        getIntent = intent
        lastShare = resources.getString(R.string.last_share_weather)

        broadcastReceiver = WeatherBroadcastReceiver()
        registerReceiver(broadcastReceiver, IntentFilter(BROADCAST_ACTION))



        checkWeatherDetails()
        updateWeatherData(getIntent.getStringExtra(IntentHelper.EXTRA_CITY_NAME))

        share_button.setOnClickListener {
            shareWeather()

        }
    }

    override fun onStop()
    {
        unregisterReceiver(broadcastReceiver)
        super.onStop()
    }

    private fun shareWeather()
    {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.putExtra(Intent.EXTRA_TEXT, text_view_city.text.toString() + ": " +
                text_view_weather.text.toString())
        shareIntent.type = "text/plain"

        if (shareIntent.resolveActivity(packageManager) != null)
        {
            startActivity(shareIntent)
        }

        val sendIntent: Intent = intent
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

        val serviceIntent = Intent(this, WeatherDataLoadService::class.java)
        serviceIntent.putExtra("city", city)
        startService(serviceIntent)

    }

    @SuppressLint("SetTextI18n")
    fun renderWeather(cityName: String, weather: String, humidity: String, pressure: String, wind: String, weatherDescription: String)
    {

        try
        {

            text_view_city.text = cityName
            text_view_weather.text = weather

            when (weatherDescription)
            {
                "Clear" -> image_weather_activity.setImageResource(R.drawable.sunny)
                "Clouds" -> image_weather_activity.setImageResource(R.drawable.cloudly)
                "Rain" -> image_weather_activity.setImageResource(R.drawable.rainy)
                else -> image_weather_activity.setImageResource(R.drawable.start)
            }
            if (isPressureChecked)
            {
                text_view_pressure.text = pressure
                text_view_pressure.setBackgroundColor(this.resources.getColor(R.color.colorBackground))
            }
            if (isHumidityChecked)
            {
                text_view_humidity.text = humidity
                text_view_humidity.setBackgroundColor(this.resources.getColor(R.color.colorBackground))
            }
            if (isWindChecked)
            {
                text_view_wind.text = wind
                text_view_wind.setBackgroundColor(this.resources.getColor(R.color.colorBackground))
            }

        } catch (e: Exception)
        {
            e.printStackTrace()
        }

    }

    inner class WeatherBroadcastReceiver : BroadcastReceiver()
    {
        lateinit var cityName: String
        lateinit var humidity: String
        lateinit var pressure: String
        lateinit var wind: String
        lateinit var weather: String
        lateinit var weatherDescription: String


        override fun onReceive(p0: Context, p1: Intent)
        {

            if (p1.getStringExtra("cityName") == "")
            {
                Toast.makeText(p0, "City not found", Toast.LENGTH_SHORT).show()
            }
            cityName = p1.getStringExtra("cityName")
            weather = p1.getStringExtra("weather")
            humidity = p1.getStringExtra("humidity")
            pressure = p1.getStringExtra("pressure")
            wind = p1.getStringExtra("wind")
            weatherDescription = p1.getStringExtra("weatherDescription")

            renderWeather(cityName, weather, humidity, pressure, wind, weatherDescription)

        }


    }


}



