package com.example.ahozyainov.activities.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.ahozyainov.activities.R
import com.example.ahozyainov.activities.common.IntentHelper
import com.example.ahozyainov.activities.models.Cities
import kotlinx.android.synthetic.*


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
        var imWeather: ImageView = view.findViewById(R.id.image_weather_activity)
        var btShareWeather: Button = view.findViewById(R.id.share_button)

        if(arguments == null) return
//        var cities : Cities = Cities.getAllCities(getArguments().getInt(IntentHelper.EXTRA_CITY_ID))

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
