package com.example.ahozyainov.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.example.ahozyainov.activities.R.id.text_view_city
import com.example.ahozyainov.activities.R.id.text_view_humidity
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
    private lateinit var handler: Handler
    private var isPressureChecked: Boolean = false
    private var isHumidityChecked: Boolean = false
    private var isWindChecked: Boolean = false
    lateinit var weatherDescription: String
    private lateinit var broadcastReceiver: BroadcastReceiver
    public lateinit var cityName: String

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        handler = Handler()
        getIntent = intent
        lastShare = resources.getString(R.string.last_share_weather)

        broadcastReceiver = WeatherBroadcastReceiver()
        val intentFilter = IntentFilter("com.example.ahozyainov.services.RESPONSE")
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT)
        registerReceiver(broadcastReceiver, intentFilter)

        checkWeatherDetails()
        updateWeatherData(getIntent.getStringExtra(IntentHelper.EXTRA_CITY_NAME))


        share_button.setOnClickListener {
            shareWeather()

        }
    }

    override fun onDestroy()
    {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
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

    fun updateWeatherData(city: String)
    {

        val serviceIntent = Intent(this, WeatherDataLoadService::class.java)
        serviceIntent.putExtra("city", city)
        startService(serviceIntent)
//        Thread(Runnable {
//            val json: JSONObject? = WeatherDataLoader.getJSONData(city)
//            if (json == null)
//            {
//                handler.post {
//                    Toast.makeText(applicationContext, getString(R.string.city_not_found), Toast.LENGTH_LONG).show()
//                }
//            } else
//            {
//                handler.post {
//                    renderWeather(json)
//                }
//            }
//        }).start()

    }

//    @SuppressLint("SetTextI18n")
//    fun renderWeather(json: JSONObject)
//    {
//        lateinit var cityName: String
//        lateinit var humidity: String
//        lateinit var pressure: String
//        lateinit var wind: String

//        try
//        {

//            var weatherDescription = json.getJSONArray("weather").getJSONObject(0).getString("main")
//            cityName = json.getString("name").toUpperCase(Locale.US) + ", " +
//                    json.getJSONObject("sys").getString("country")
//            text_view_city.text = cityName
//            text_view_weather.text = json.getJSONObject("main").getString("temp") + "\u2103" + " " +
//                    json.getJSONArray("weather").getJSONObject(0).getString("description")
//            humidity = "Humidity: " + json.getJSONObject("main").getString("humidity") + " " + "\u0025"
//            pressure = "Pressure: " + json.getJSONObject("main").getString("pressure") + " " + "hpa"
//            wind = "Wind: " + json.getJSONObject("wind").getString("speed") + " " + "m/s"
//            writeDataToDatabase(weatherDescription, humidity, pressure, wind, cityName)
//            when (weatherDescription)
//            {
//                "Clear" -> image_weather_activity.setImageResource(R.drawable.sunny)
//                "Clouds" -> image_weather_activity.setImageResource(R.drawable.cloudly)
//                "Rain" -> image_weather_activity.setImageResource(R.drawable.rainy)
//                else -> image_weather_activity.setImageResource(R.drawable.start)
//            }
//            if (isPressureChecked)
//            {
//                text_view_pressure.text = pressure
//                text_view_pressure.setBackgroundColor(this.resources.getColor(R.color.colorBackground))
//            }
//            if (isHumidityChecked)
//            {
//                text_view_humidity.text = humidity
//                text_view_humidity.setBackgroundColor(this.resources.getColor(R.color.colorBackground))
//            }
//            if (isWindChecked)
//            {
//                text_view_wind.text = wind
//                text_view_wind.setBackgroundColor(this.resources.getColor(R.color.colorBackground))
//            }
//
//        } catch (e: Exception)
//        {
//            e.printStackTrace()
//        }

//    }

    //    private fun writeDataToDatabase(weatherDescription: String?, humidity: String, pressure: String, wind: String, cityName: String)
//    {
//        var databaseHelper = WeatherDatabaseHelper(context = this)
//        var cursor = databaseHelper.getCityWeather()
//        cursor.moveToFirst()
//        var weatherData = "$weatherDescription, $humidity, $pressure, $wind"
//
//        databaseHelper.cityWeather(cityName, weatherData)
//
//        cursor.close()
//        databaseHelper.close()
//
//    }
    companion object
    {
        fun setWeatherDataToView(cityName: String, humidity: String, pressure: String, wind: String, weatherDescription: String)
        {

        }
    }

    class WeatherBroadcastReceiver : BroadcastReceiver()
    {
        lateinit var cityName: String
        lateinit var humidity: String
        lateinit var pressure: String
        lateinit var wind: String
        lateinit var weatherDescription: String


        override fun onReceive(p0: Context, p1: Intent)
        {
            if (p1.getStringExtra("cityName") == "")
            {
                Toast.makeText(p0, "City not found", Toast.LENGTH_LONG).show()
            }
            cityName = p1.getStringExtra("cityName")
            humidity = p1.getStringExtra("humidity")
            pressure = p1.getStringExtra("pressure")
            wind = p1.getStringExtra("wind")
            weatherDescription = p1.getStringExtra("weatherDescription")
            Toast.makeText(p0, cityName, Toast.LENGTH_LONG).show()

            setWeatherDataToView(cityName, humidity, pressure, wind, weatherDescription)

        }


    }

}



