package com.example.ahozyainov.models

import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherDataLoader {

    companion object {

        private const val POST_URL_API = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric"
        private const val KEY = "x-api-key"
        private const val API_KEY = "b24c3e1ddeea0709848ec2c367c01d24"
        private const val RESPONSE_CODE = "cod"
        private const val RESPONSE_CODE_OK = 200

        @JvmStatic
        fun getJSONData(city: String): JSONObject? {
            try {
                var url = URL(String.format(POST_URL_API, city))
                Log.d("post", url.toString())
                var connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.addRequestProperty(KEY, API_KEY)

                var reader = BufferedReader(InputStreamReader(connection.inputStream))
                var rawData = StringBuilder(1024)
                var temp: String

                while (true) {
                    temp = reader.readLine() ?: break
                    rawData.append(temp).append("\n")
                }

                reader.close()

                var jsonObject = JSONObject(rawData.toString())

                if (jsonObject.getInt(RESPONSE_CODE) != RESPONSE_CODE_OK) {
                    return null
                }

                return jsonObject

            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }

        }

    }

}