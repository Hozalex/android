package com.example.ahozyainov.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.ahozyainov.common.IntentHelper
import com.example.ahozyainov.models.Cities
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : AppCompatActivity() {

    private lateinit var lastShare: String
    private lateinit var getIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        getIntent = intent
        lastShare = resources.getString(R.string.last_share_weather)

        showWeather(getIntent.getIntExtra(IntentHelper.EXTRA_CITY_POSITION, 0))
        showForecast()

        share_button.setOnClickListener {
            shareWeather()

        }
    }

    private fun shareWeather() {
        var shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.putExtra(Intent.EXTRA_TEXT, text_view_city.text.toString() + ": " +
                text_view_weather.text.toString())
        shareIntent.type = "text/plain"

        if (shareIntent.resolveActivity(packageManager) != null) {
            startActivity(shareIntent)
        }

        var sendIntent: Intent = intent
        sendIntent.putExtra(IntentHelper.EXTRA_SHARED_WEATHER, lastShare + " " + text_view_city.text.toString())
        setResult(Activity.RESULT_OK, intent)
    }

    private fun showWeather(position: Int) {
        var cities: Cities = Cities.getAllCities(this)[position]
        text_view_city.text = cities.name
        image_weather_activity.setImageResource(cities.imageId)
        text_view_weather.text = resources.getString(cities.descriptionId)

    }

    private fun showForecast() {
        if (intent.getBooleanExtra(IntentHelper.EXTRA_CHECKBOX_PRESSURE, false)) {
            text_view_pressure.setBackgroundColor(resources.getColor(R.color.colorBackground))
            text_view_pressure.text = resources.getString(R.string.pressure_value)
        }
        if (intent.getBooleanExtra(IntentHelper.EXTRA_CHECKBOX_TOMORROW, false)) {
            text_view_tomorrow_forecast.setBackgroundColor(resources.getColor(R.color.colorBackground))
            text_view_tomorrow_forecast.text = resources.getString(R.string.tomorrow_weather)
        }
        if (intent.getBooleanExtra(IntentHelper.EXTRA_CHECKBOX_WEEK, false)) {
            text_view_week_forecast.setBackgroundColor(resources.getColor(R.color.colorBackground))
            text_view_week_forecast.text = resources.getString(R.string.week_weather)
        }
    }

}



