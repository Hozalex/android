package com.example.ahozyainov.activities.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.ahozyainov.activities.R

open class ForecastDetailFragment : android.support.v4.app.Fragment(), View.OnClickListener {

    private lateinit var checkBoxPressure: CheckBox
    private lateinit var checkBoxTomorrowForecast: CheckBox
    private lateinit var checkBoxWeekForecast: CheckBox
    private lateinit var tvPressure: TextView
    private lateinit var tvTomorrowForecast: TextView
    private lateinit var tvWeekForecast: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ForecastDetailFragment", "savedInstanceState$savedInstanceState")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var layout: View = inflater.inflate(R.layout.fragment_forecast_detail, container, false)
        checkBoxPressure = layout.findViewById(R.id.checkbox_pressure)
        checkBoxPressure.setOnClickListener(this)
        checkBoxTomorrowForecast = layout.findViewById(R.id.checkbox_tomorrow)
        checkBoxTomorrowForecast.setOnClickListener(this)
        checkBoxWeekForecast = layout.findViewById(R.id.checkbox_week)
        checkBoxWeekForecast.setOnClickListener(this)
        tvPressure = layout.findViewById(R.id.text_view_pressure)
        tvTomorrowForecast = layout.findViewById(R.id.text_view_tomorrow_forecast)
        tvWeekForecast = layout.findViewById(R.id.text_view_week_forecast)


        if (savedInstanceState != null) {
            checkBoxPressure.isChecked = savedInstanceState.getBoolean("isCheckedPressure")
            checkBoxTomorrowForecast.isChecked = savedInstanceState.getBoolean("isCheckedTomorrowForecast")
            checkBoxWeekForecast.isChecked = savedInstanceState.getBoolean("isCheckedWeekForecast")
            tvPressure.text = savedInstanceState.getString("tvPressure")
            tvTomorrowForecast.text = savedInstanceState.getString("tvTomorrowForecast")
            tvWeekForecast.text = savedInstanceState.getString("tvWeekForecast")

        }

        return layout
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("isCheckedPressure", checkBoxPressure.isChecked)
        outState.putBoolean("isCheckedTomorrowForecast", checkBoxTomorrowForecast.isChecked)
        outState.putBoolean("isCheckedWeekForecast", checkBoxWeekForecast.isChecked)
        outState.putString("tvPressure", tvPressure.text.toString())
        outState.putString("tvTomorrowForecast", tvTomorrowForecast.text.toString())
        outState.putString("tvWeekForecast", tvWeekForecast.text.toString())
        super.onSaveInstanceState(outState)
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            checkBoxPressure.id -> onClickPressureCheckBox()
            checkBoxTomorrowForecast.id -> onClickTomorrowForecastCheckBox()
            checkBoxWeekForecast.id -> onClickWeekForecastCheckBox()

        }
    }

    private fun onClickPressureCheckBox() {
        if (checkBoxPressure.isChecked) {
            tvPressure.text = resources.getString(R.string.pressure_value)
            tvPressure.setBackgroundResource(R.color.colorBackground)
        } else {
            tvPressure.text = ""
            tvPressure.setBackgroundColor(0)
        }
    }

    private fun onClickTomorrowForecastCheckBox() {
        if (checkBoxTomorrowForecast.isChecked) {
            tvTomorrowForecast.text = resources.getString(R.string.tomorrow_weather)
            tvTomorrowForecast.setBackgroundResource(R.color.colorBackground)
        } else {
            tvTomorrowForecast.text = ""
            tvTomorrowForecast.setBackgroundColor(0)
        }
    }

    private fun onClickWeekForecastCheckBox() {
        if (checkBoxWeekForecast.isChecked) {
            tvWeekForecast.text = resources.getString(R.string.week_weather)
            tvWeekForecast.setBackgroundResource(R.color.colorBackground)
        } else {
            tvWeekForecast.text = ""
            tvWeekForecast.setBackgroundColor(0)
        }
    }

}