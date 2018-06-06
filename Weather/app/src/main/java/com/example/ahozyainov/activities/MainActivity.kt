package com.example.ahozyainov.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {

    private val sharedTextKey = "sharedText"
    private var sharedText = ""
    private val MYSETTINGS = "mySettings"
    private var city: String = ""
    private val cityKey = "city"
    private val isChecked = 1
    private val unChecked = 0
    private val sendRequestCode = 1
    private val idSpinner = "idSpinner"
    private lateinit var settings: SharedPreferences
    private lateinit var spinner: Spinner
    private lateinit var button: Button
    private lateinit var textView: TextView
    private lateinit var checkPressure: CheckBox
    private lateinit var checkTomorrowForecast: CheckBox
    private lateinit var checkWeekForecast: CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settings = getSharedPreferences(MYSETTINGS, Context.MODE_PRIVATE)
        spinner = findViewById(R.id.cities_spinner)
        button = findViewById(R.id.check_button)
        textView = findViewById(R.id.text_view_main)
        checkPressure = findViewById(R.id.checkbox_pressure)
        checkTomorrowForecast = findViewById(R.id.checkbox_tomorrow)
        checkWeekForecast = findViewById(R.id.checkbox_week)
        intent = Intent(this, WeatherActivity::class.java)


        if (savedInstanceState != null) {
            sharedText = savedInstanceState.getString(sharedTextKey)
            textView.text = sharedText
        }


        checkSettings()

        button.setOnClickListener({
            checkWeather()
        })
    }

    override fun onStop() {
        super.onStop()

//        settings.edit().putString(checkPressure.text.toString(), checkPressure.isChecked.toString())
//        settings.edit().putString(checkTomorrowForecast.text.toString(), checkTomorrowForecast.isChecked.toString())
//        settings.edit().putString(checkWeekForecast.text.toString(), checkWeekForecast.isChecked.toString())

        city = spinner.selectedItem.toString()
        settings.edit().putString(cityKey, city).apply()

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

    private fun checkWeather() {
        var position: Int = spinner.selectedItemPosition
        city = spinner.selectedItem.toString()

        if (checkPressure.isChecked) {
            println("checkPressure - checked main")
            intent.putExtra(checkPressure.text.toString(), isChecked)
        } else intent.putExtra(checkPressure.text.toString(), unChecked)

        if (checkTomorrowForecast.isChecked) {
            println("checkTomorrow - checked main")
            intent.putExtra(checkTomorrowForecast.text.toString(), isChecked)
        } else intent.putExtra(checkTomorrowForecast.text.toString(), unChecked)

        if (checkWeekForecast.isChecked) {
            println("checkWeek - checked main")
            intent.putExtra(checkPressure.text.toString(), isChecked)
        } else intent.putExtra(checkPressure.text.toString(), unChecked)

        intent.putExtra(idSpinner, position)
        intent.putExtra(cityKey, city)
        startActivityForResult(intent, sendRequestCode)
    }

    private fun checkSettings() {
        var checkedBox: String
        if (settings.contains(cityKey)) {
            var checkCity: String = settings.getString(cityKey, "")
            var cityArray: Array<String> = resources.getStringArray(R.array.cities)
            var index = 0
            for (item in cityArray) {
                if (checkCity.equals(item)) {
                    index = cityArray.indexOf(item)
                }
            }
            spinner.setSelection(index)
        }


    }

}
