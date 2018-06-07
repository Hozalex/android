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
import android.widget.*
import com.example.ahozyainov.activities.adapters.CityAdapter
import com.example.ahozyainov.activities.common.IntentHelper
import com.example.ahozyainov.activities.models.Cities

class MainActivity : AppCompatActivity() {

    private val sharedTextKey = "sharedText"
    private var sharedText = ""
    private val mySettings = "mySettings"
    private val sendRequestCode = 1
    private lateinit var settings: SharedPreferences
    private lateinit var textView: TextView
    private lateinit var checkBoxPressure: CheckBox
    private lateinit var checkBoxTomorrowForecast: CheckBox
    private lateinit var checkBoxWeekForecast: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settings = getSharedPreferences(mySettings, Context.MODE_PRIVATE)
        textView = findViewById(R.id.text_view_main)
        checkBoxPressure = findViewById(R.id.checkbox_pressure)
        checkBoxTomorrowForecast = findViewById(R.id.checkbox_tomorrow)
        checkBoxWeekForecast = findViewById(R.id.checkbox_week)
        intent = Intent(this, WeatherActivity::class.java)

        var rvCities: RecyclerView = findViewById(R.id.rvCities)
        rvCities.setHasFixedSize(true)
        rvCities.layoutManager = LinearLayoutManager(this)
        rvCities.adapter = CityAdapter(Cities.getAllCities(this), CityAdapter.OnCityClickListener { cityPosition ->
            run {
                intent.putExtra(IntentHelper.EXTRA_CITY_POSITION, cityPosition)
                startActivityForResult(intent, sendRequestCode)
            }
        })
        rvCities.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        if (savedInstanceState != null) {
            sharedText = savedInstanceState.getString(sharedTextKey)
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

    override fun onStop() {
        super.onStop()
        settings.edit().putBoolean(checkBoxPressure.text.toString(), checkBoxPressure.isChecked).apply()
        settings.edit().putBoolean(checkBoxTomorrowForecast.text.toString(), checkBoxTomorrowForecast.isChecked).apply()
        settings.edit().putBoolean(checkBoxWeekForecast.text.toString(), checkBoxWeekForecast.isChecked).apply()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(sharedTextKey, sharedText)
        super.onSaveInstanceState(outState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == sendRequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                sharedText = data!!.getStringExtra(sharedTextKey)
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


