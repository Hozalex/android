package com.example.ahozyainov.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

class WeatherActivity : AppCompatActivity() {

    private val sharedTextKey = "sharedText"
    private lateinit var lastShare: String
    private lateinit var textViewWeather: TextView
    private lateinit var textViewCity: TextView
    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private val idSpinner = "idSpinner"
    private val cityKey = "city"
    private val checkedBoxText = "checkedBoxText"
    private val isCheckedBox = "checkedBox"
    private lateinit var weatherString: Array<String>
    private lateinit var textViewPressure: TextView
    private lateinit var textViewTomorrowForecast: TextView
    private lateinit var textViewWeekForecast: TextView
    private lateinit var checkPressure: String
    private lateinit var checkTomorrow: String
    private lateinit var checkWeek: String
    private lateinit var getIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        textViewWeather = findViewById(R.id.text_view_weather)
        textViewCity = findViewById(R.id.text_view_city)
        imageView = findViewById(R.id.image_weather_activity)
        button = findViewById(R.id.share_button)
        weatherString = resources.getStringArray(R.array.weather)
        textViewPressure = findViewById(R.id.text_view_pressure)
        textViewTomorrowForecast = findViewById(R.id.text_view_tomorrow_forecast)
        textViewWeekForecast = findViewById(R.id.text_view_week_forecast)
        checkPressure = resources.getString(R.string.pressure)
        checkTomorrow = resources.getString(R.string.pressure)
        checkWeek = resources.getString(R.string.pressure)
        getIntent = intent
        lastShare = resources.getString(R.string.last_share_weather)


        showWeather(getIntent.getIntExtra(idSpinner, 5), getIntent.getStringExtra(cityKey))
        showForecast()

        button.setOnClickListener({
            shareWeather()

        })
    }

    private fun shareWeather() {
        var shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.putExtra(Intent.EXTRA_TEXT, textViewCity.text.toString() + ": " +
                textViewWeather.text.toString())
        shareIntent.type = "text/plain"

        if (shareIntent.resolveActivity(packageManager) != null) {
            startActivity(shareIntent)
        }

        var sendIntent: Intent = intent

        sendIntent.putExtra(sharedTextKey, lastShare + " " + textViewCity.text.toString())
        setResult(Activity.RESULT_OK, intent)
    }

    private fun showWeather(position: Int, idCity: String) {

        if (position == 0 || position == 3) {

            imageView.setImageResource(R.drawable.sunny)
            textViewCity.text = idCity
            textViewWeather.text = weatherString[1]
            textViewWeather.setBackgroundResource(R.color.colorBackground)

        } else if (position == 1 || position == 2) {
            imageView.setImageResource(R.drawable.rainy)
            textViewCity.text = idCity
            textViewWeather.text = weatherString[0]
            textViewWeather.setBackgroundResource(R.color.colorBackground)
        }

    }

    private fun showForecast() {

        if (getIntent.getIntExtra(checkPressure, 3) == 1) {
            println("checkPressure - got ")
            textViewPressure.text = resources.getString(R.string.pressure_value)
            textViewPressure.setBackgroundResource(R.color.colorBackground)
        }
        if (getIntent.getIntExtra(checkTomorrow, 3) == 1) {
            println("checkTomorrow - got ")
            textViewTomorrowForecast.text = resources.getString(R.string.tomorrow_weather)
            textViewTomorrowForecast.setBackgroundResource(R.color.colorBackground)
        }
        if (getIntent.getIntExtra(checkWeek, 3) == 1) {
            println("checkWeek - got ")
            textViewWeekForecast.text = resources.getString(R.string.week_weather)
            textViewWeekForecast.setBackgroundResource(R.color.colorBackground)
        }
    }

}



