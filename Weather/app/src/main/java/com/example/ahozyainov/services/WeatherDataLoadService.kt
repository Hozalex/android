package com.example.ahozyainov.services

import android.app.IntentService
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.example.ahozyainov.activities.R
import com.example.ahozyainov.activities.R.id.text_view_weather
import com.example.ahozyainov.activities.R.string.pressure
import com.example.ahozyainov.activities.WeatherActivity
import com.example.ahozyainov.models.WeatherDatabaseHelper
import com.example.ahozyainov.widget.WidgetWeather
import kotlinx.android.synthetic.main.activity_weather.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class WeatherDataLoadService : IntentService("WeatherDataLoadService")
{
    private val POST_URL_API = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric"
    private val KEY = "x-api-key"
    private val API_KEY = "b24c3e1ddeea0709848ec2c367c01d24"
    private val RESPONSE_CODE = "cod"
    private val RESPONSE_CODE_OK = 200
    private val TAG: String = "DataLoadServiceLog"

    private var cityName: String = ""
    private var humidity: String = ""
    private var pressure: String = ""
    private var wind: String = ""
    private var weather: String = ""
    private var weatherDescription: String = ""

    override fun onCreate()
    {
        super.onCreate()
        Log.d(TAG, "Service Start")
    }

    override fun onHandleIntent(p0: Intent?)
    {
        val city: String = p0!!.getStringExtra("city")
        Log.d(TAG, "OnHandlerIntent$city")
        var jsonObject: JSONObject? = null
        try
        {

            val url = URL(String.format(POST_URL_API, city))
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.addRequestProperty(KEY, API_KEY)

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val rawData = StringBuilder(1024)
            var temp: String

            while (true)
            {
                temp = reader.readLine() ?: break
                rawData.append(temp).append("\n")
            }

            reader.close()

            jsonObject = JSONObject(rawData.toString())

            if (jsonObject.getInt(RESPONSE_CODE) != RESPONSE_CODE_OK)
            {
                Log.d(TAG, "Response code not OK")
                jsonObject = null
            }

            getDataFromJSON(jsonObject)

        } catch (e: Exception)
        {
            e.printStackTrace()
            Log.d(TAG, e.message)

        }

        sendDataToWeatherActivity()
        sendDataToWidget()

    }


    private fun sendDataToWeatherActivity()
    {
        val responseIntent = Intent(WeatherActivity.BROADCAST_ACTION)
        if (cityName.isEmpty())
        {
            cityName = "City Not Found"
        }
        responseIntent.putExtra("cityName", cityName)
        responseIntent.putExtra("weather", weather)
        responseIntent.putExtra("humidity", humidity)
        responseIntent.putExtra("pressure", pressure)
        responseIntent.putExtra("wind", wind)
        responseIntent.putExtra("weatherDescription", weatherDescription)
        Log.d(TAG, "sendDataToWeatherActivity $cityName, $weather, $weatherDescription")
        sendBroadcast(responseIntent)
        stopSelf()
    }

    private fun sendDataToWidget()
    {
        val remoteView = RemoteViews(packageName, R.layout.widget_layout)
        if (cityName.isEmpty())
        {
            cityName = "City Not Found"
        }
        remoteView.setTextViewText(R.id.tvCityWidget, cityName + "\n" + weather)
        when (weatherDescription)
        {
            "Clear" -> remoteView.setImageViewResource(R.id.ivCityWidget, R.drawable.sunnywidget)
            "Clouds" -> remoteView.setImageViewResource(R.id.ivCityWidget, R.drawable.cloudlywidget)
            "Rain" -> remoteView.setImageViewResource(R.id.ivCityWidget, R.drawable.rainywidget)
            else -> remoteView.setImageViewResource(R.id.ivCityWidget, R.drawable.start)
        }
        val widget = ComponentName(this, WidgetWeather::class.java)
        val manager = AppWidgetManager.getInstance(this)
        manager.updateAppWidget(widget, remoteView)
        stopSelf()
    }

    private fun getDataFromJSON(json: JSONObject?)
    {
        try
        {
            weatherDescription = json!!.getJSONArray("weather").getJSONObject(0).getString("main")
            cityName = json.getString("name").toUpperCase(Locale.US) + ", " +
                    json.getJSONObject("sys").getString("country")
            weather = json.getJSONObject("main").getString("temp") + "\u2103" + " " +
                    json.getJSONArray("weather").getJSONObject(0).getString("description")
            humidity = "Humidity: " + json.getJSONObject("main").getString("humidity") + " " + "\u0025"
            pressure = "Pressure: " + json.getJSONObject("main").getString("pressure") + " " + "hpa"
            wind = "Wind: " + json.getJSONObject("wind").getString("speed") + " " + "m/s"
            writeDataToDatabase(weatherDescription, humidity, pressure, wind, cityName)

        } catch (e: Exception)
        {
            e.printStackTrace()
            Log.d(TAG, e.message)
        }
    }

    private fun writeDataToDatabase(weatherDescription: String?, humidity: String, pressure: String, wind: String, cityName: String)
    {
        val databaseHelper = WeatherDatabaseHelper(context = this)
        val cursor = databaseHelper.getCityWeather()
        cursor.moveToFirst()
        val weatherData = "$weatherDescription, $humidity, $pressure, $wind"

        databaseHelper.cityWeather(cityName, weatherData)

        cursor.close()
        databaseHelper.close()

    }


}