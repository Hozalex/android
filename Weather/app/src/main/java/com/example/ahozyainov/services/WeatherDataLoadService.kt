package com.example.ahozyainov.services

import android.app.IntentService
import android.content.Intent
import com.example.ahozyainov.models.WeatherDataLoader
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherDataLoadService : IntentService("WeatherDataLoadService")
{
    private val POST_URL_API = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric"
    private val KEY = "x-api-key"
    private val API_KEY = "b24c3e1ddeea0709848ec2c367c01d24"
    private val RESPONSE_CODE = "cod"
    private val RESPONSE_CODE_OK = 200
    private var jsonData: JSONObject? = null
    private lateinit var city: String

    override fun onHandleIntent(p0: Intent?)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int
    {
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

            var jsonObject = JSONObject(rawData.toString())

            if (jsonObject.getInt(RESPONSE_CODE) != RESPONSE_CODE_OK)
            {
                jsonData = null
            }

            jsonData = jsonObject

        } catch (e: Exception)
        {
            e.printStackTrace()
            jsonData = null
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy()
    {
        super.onDestroy()
    }
}