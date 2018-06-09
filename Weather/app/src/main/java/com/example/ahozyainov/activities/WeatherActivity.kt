package com.example.ahozyainov.activities

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.ahozyainov.activities.R.color.colorBackground
import com.example.ahozyainov.activities.common.IntentHelper
import com.example.ahozyainov.activities.models.Cities

class WeatherActivity : AppCompatActivity() {

    private lateinit var lastShare: String
    private lateinit var textViewWeather: TextView
    private lateinit var textViewCity: TextView
    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private lateinit var textViewPressure: TextView
    private lateinit var textViewTomorrowForecast: TextView
    private lateinit var textViewWeekForecast: TextView
    private lateinit var getIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        textViewWeather = findViewById(R.id.text_view_weather)
        textViewCity = findViewById(R.id.text_view_city)
        imageView = findViewById(R.id.image_weather_activity)
        button = findViewById(R.id.share_button)
        textViewPressure = findViewById(R.id.text_view_pressure)
        textViewTomorrowForecast = findViewById(R.id.text_view_tomorrow_forecast)
        textViewWeekForecast = findViewById(R.id.text_view_week_forecast)
        getIntent = intent
        lastShare = resources.getString(R.string.last_share_weather)

        showWeather(getIntent.getIntExtra(IntentHelper.EXTRA_CITY_POSITION, 0))
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
        sendIntent.putExtra(IntentHelper.EXTRA_SHARED_WEATHER, lastShare + " " + textViewCity.text.toString())
        setResult(Activity.RESULT_OK, intent)
    }

    private fun showWeather(position: Int) {
        var cities: Cities = Cities.getAllCities(this)[position]
        textViewCity.text = cities.name
        imageView.setImageResource(cities.imageId)
        textViewWeather.text = resources.getString(cities.descriptionId)

    }

    private fun showForecast() {
        if (intent.getBooleanExtra(IntentHelper.EXTRA_CHECKBOX_PRESSURE, false)) {
            textViewPressure.setBackgroundColor(resources.getColor(R.color.colorBackground))
            textViewPressure.text = resources.getString(R.string.pressure_value)
        }
        if (intent.getBooleanExtra(IntentHelper.EXTRA_CHECKBOX_TOMORROW, false)) {
            textViewTomorrowForecast.setBackgroundColor(resources.getColor(R.color.colorBackground))
            textViewTomorrowForecast.text = resources.getString(R.string.tomorrow_weather)
        }
        if (intent.getBooleanExtra(IntentHelper.EXTRA_CHECKBOX_WEEK, false)) {
            textViewWeekForecast.setBackgroundColor(resources.getColor(R.color.colorBackground))
            textViewWeekForecast.text = resources.getString(R.string.week_weather)
        }
    }

}



