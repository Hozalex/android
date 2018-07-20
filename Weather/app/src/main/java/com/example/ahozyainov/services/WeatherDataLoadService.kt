package com.example.ahozyainov.services

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.ahozyainov.activities.R.string.pressure
import com.example.ahozyainov.models.WeatherDatabaseHelper
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
    private val ACTION_WEATHERDATALOAD: String = "com.example.ahozyainov.services.RESPONSE"

    var cityName: String = ""
    var humidity: String = ""
    var pressure: String = ""
    var wind: String = ""
    var weatherDescription: String = ""

    override fun onCreate()
    {
        super.onCreate()
        Log.d(TAG, "Service Start")
    }

    override fun onHandleIntent(p0: Intent?)
    {
        val city: String = p0!!.getStringExtra("city")
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


        val responseIntent = Intent()
        responseIntent.action = ACTION_WEATHERDATALOAD
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT)
        responseIntent.putExtra("cityName", cityName)
        responseIntent.putExtra("humidity", humidity)
        responseIntent.putExtra("pressure", pressure)
        responseIntent.putExtra("wind", wind)
        responseIntent.putExtra("weatherDescription", weatherDescription)
        sendBroadcast(responseIntent)


    }

    private fun getDataFromJSON(json: JSONObject?)
    {
        try
        {
            weatherDescription = json!!.getJSONArray("weather").getJSONObject(0).getString("main")
            cityName = json!!.getString("name").toUpperCase(Locale.US) + ", " +
                    json.getJSONObject("sys").getString("country")
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