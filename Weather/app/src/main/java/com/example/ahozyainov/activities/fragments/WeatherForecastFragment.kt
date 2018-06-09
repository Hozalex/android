package com.example.ahozyainov.activities.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.example.ahozyainov.activities.R
import com.example.ahozyainov.activities.common.IntentHelper
import com.example.ahozyainov.activities.models.Cities


class WeatherForecastFragment : Fragment() {

    private var cityId: Int = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var tvCityName: TextView = view.findViewById(R.id.text_view_city)
        var tvWeather: TextView = view.findViewById(R.id.text_view_weather)
        var tvPressure: TextView = view.findViewById(R.id.text_view_pressure)
        var tvTomorrowForecast: TextView = view.findViewById(R.id.text_view_tomorrow_forecast)
        var tvWeekForecast: TextView = view.findViewById(R.id.text_view_week_forecast)
        var checkBoxPressure = view.findViewById<CheckBox>(R.id.checkbox_pressure)
        var checkBoxTomorrowForecast = view.findViewById<CheckBox>(R.id.checkbox_tomorrow)
        var checkBoxWeekForecast = view.findViewById<CheckBox>(R.id.checkbox_week)
        var imWeather: ImageView = view.findViewById(R.id.image_weather_activity)
        var btShareWeather: Button = view.findViewById(R.id.share_button)

        checkBoxPressure.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                tvPressure.text = view.resources.getString(R.string.pressure_value)
            } else tvPressure.text = ""
        }
        checkBoxTomorrowForecast.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                tvTomorrowForecast.text = view.resources.getString(R.string.tomorrow_weather)
            } else tvTomorrowForecast.text = ""
        }
        checkBoxWeekForecast.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                tvWeekForecast.text = view.resources.getString(R.string.week_weather)
            } else tvWeekForecast.text = ""
        }


        if (arguments == null) return
        var cities = Cities.getAllCities(context)
        var city: Cities = cities[arguments!!.getInt(IntentHelper.EXTRA_CITY_ID)]
        tvCityName.text = city.name
        tvWeather.setText(city.descriptionId)
        imWeather.setImageResource(city.imageId)

        btShareWeather.setOnClickListener {
            var shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_TEXT, tvCityName.text.toString() + ": " +
                    tvWeather.text.toString())
            shareIntent.type = "text/plain"
            startActivity(shareIntent)

        }
    }


    companion object {
        @JvmStatic
        fun newInstance(cityId: Int) =
                WeatherForecastFragment().apply {
                    arguments = Bundle().apply {
                        putInt(IntentHelper.EXTRA_CITY_ID, cityId)
                    }
                }
    }


}
