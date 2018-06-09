package com.example.ahozyainov.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.example.ahozyainov.activities.adapters.CityAdapter
import com.example.ahozyainov.activities.common.IntentHelper
import com.example.ahozyainov.activities.fragments.WeatherForecastFragment
import com.example.ahozyainov.activities.models.Cities

class MainActivity : AppCompatActivity() {

    private var sharedText = ""
    private val mySettings = "mySettings"
    private val sendRequestCode = 1
    private lateinit var settings: SharedPreferences
    private lateinit var textView: TextView
    private lateinit var checkBoxPressure: CheckBox
    private lateinit var checkBoxTomorrowForecast: CheckBox
    private lateinit var checkBoxWeekForecast: CheckBox
    private lateinit var rvCities: RecyclerView
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        twoPane = findViewById<View>(R.id.flRightContainer) != null

        settings = getSharedPreferences(mySettings, Context.MODE_PRIVATE)
        textView = findViewById(R.id.text_view_main)
        checkBoxPressure = findViewById(R.id.checkbox_pressure)
        checkBoxTomorrowForecast = findViewById(R.id.checkbox_tomorrow)
        checkBoxWeekForecast = findViewById(R.id.checkbox_week)
        intent = Intent(this, WeatherActivity::class.java)

        rvCities = findViewById(R.id.rvCities)
        rvCities.setHasFixedSize(true)
        rvCities.layoutManager = LinearLayoutManager(this)
        rvCities.adapter = CityAdapter(Cities.getAllCities(this), CityAdapter.OnCityClickListener { cityPosition ->
            run {
                if (!twoPane) {
                    intent.putExtra(IntentHelper.EXTRA_CITY_POSITION, cityPosition)
                    startActivityForResult(intent, sendRequestCode)
                } else showWeatherForecastFragment(cityPosition)
            }
        })

        if (twoPane && savedInstanceState == null)
            showWeatherForecastFragment(0)
        rvCities.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        if (savedInstanceState != null) {
            sharedText = savedInstanceState.getString(IntentHelper.EXTRA_SHARED_WEATHER)
            textView.text = sharedText
        }

        checkBoxPressure.setOnCheckedChangeListener { compoundButton, b ->
            intent.putExtra(IntentHelper.EXTRA_CHECKBOX_PRESSURE, b)
        }
        checkBoxTomorrowForecast.setOnCheckedChangeListener { compoundButton, b ->
            intent.putExtra(IntentHelper.EXTRA_CHECKBOX_TOMORROW, b)
        }
        checkBoxWeekForecast.setOnCheckedChangeListener { compoundButton, b ->
            intent.putExtra(IntentHelper.EXTRA_CHECKBOX_WEEK, b)
        }

        checkSettings()

    }

    private fun showWeatherForecastFragment(cityPosition: Int) {
        supportFragmentManager.beginTransaction().replace(R.id.flRightContainer,
                WeatherForecastFragment.newInstance(cityPosition))
                .commit()
    }

    override fun onStop() {
        super.onStop()
        settings.edit().putBoolean(checkBoxPressure.text.toString(), checkBoxPressure.isChecked).apply()
        settings.edit().putBoolean(checkBoxTomorrowForecast.text.toString(), checkBoxTomorrowForecast.isChecked).apply()
        settings.edit().putBoolean(checkBoxWeekForecast.text.toString(), checkBoxWeekForecast.isChecked).apply()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(IntentHelper.EXTRA_SHARED_WEATHER, sharedText)
        super.onSaveInstanceState(outState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == sendRequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                sharedText = data!!.getStringExtra(IntentHelper.EXTRA_SHARED_WEATHER)
                textView.text = sharedText
            }
        }
    }

    private fun checkSettings() {
        if (settings.getBoolean(checkBoxPressure.text.toString(), checkBoxPressure.isChecked)) {
            checkBoxPressure.isChecked = true
        }
        if (settings.getBoolean(checkBoxTomorrowForecast.text.toString(), checkBoxTomorrowForecast.isChecked)) {
            checkBoxTomorrowForecast.isChecked = true
        }
        if (settings.getBoolean(checkBoxWeekForecast.text.toString(), checkBoxWeekForecast.isChecked)) {
            checkBoxWeekForecast.isChecked = true
        }

    }


}


