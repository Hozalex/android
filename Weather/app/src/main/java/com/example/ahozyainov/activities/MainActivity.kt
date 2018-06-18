package com.example.ahozyainov.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.ahozyainov.activities.fragments.WeatherForecastFragment
import com.example.ahozyainov.adapters.CityAdapter
import com.example.ahozyainov.common.IntentHelper
import com.example.ahozyainov.models.Cities

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
    private lateinit var imageMain: ImageView
    private lateinit var floatingActionButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        twoPane = findViewById<View>(R.id.flRightContainer) != null
        settings = getSharedPreferences(mySettings, Context.MODE_PRIVATE)
        textView = findViewById(R.id.text_view_main)


        imageMain = findViewById(com.example.ahozyainov.activities.R.id.image_Main)
        rvCities = findViewById(R.id.rvCities)
        rvCities.setHasFixedSize(true)
        rvCities.layoutManager = LinearLayoutManager(this)
        if (savedInstanceState != null) {
            sharedText = savedInstanceState.getString(IntentHelper.EXTRA_SHARED_WEATHER)
            textView.text = sharedText
        }


        addAdapter(savedInstanceState)
//        checkBoxChecked()
        initPopUpMenu()

//        checkSettings()

    }

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.menu_info -> {
//                val toast = Toast.makeText(applicationContext, R.string.attention_info, Toast.LENGTH_LONG)
//                toast.duration = Toast.LENGTH_LONG
//                toast.show()
//                return true
//            }
//            R.id.menu_about -> {
//                val toast = Toast.makeText(applicationContext, R.string.about_text, Toast.LENGTH_LONG)
//                toast.duration = Toast.LENGTH_LONG
//                toast.show()
//                return true
//            }
//
//        }
//        dlMainLayout.closeDrawer(GravityCompat.START)
//        return true
//
//    }


    private fun initPopUpMenu() {
        floatingActionButton = findViewById(R.id.popButton)
        floatingActionButton.setOnClickListener {
            val popupMenu = PopupMenu(this, it)
            menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.pressure_menu_checkbox -> {
                        it.isChecked = !it.isChecked
                        true
                    }
                    R.id.tomorrow_menu_checkbox -> {

                        true
                    }
                    R.id.week_menu_checkbox -> {

                        true
                    }
                    else -> {
                        false
                    }
                }
            }
            popupMenu.show()

        }
    }

    private fun checkBoxChecked() {
        checkBoxPressure.setOnCheckedChangeListener { compoundButton, b ->
            intent.putExtra(IntentHelper.EXTRA_CHECKBOX_PRESSURE, b)
        }
        checkBoxTomorrowForecast.setOnCheckedChangeListener { compoundButton, b ->
            intent.putExtra(IntentHelper.EXTRA_CHECKBOX_TOMORROW, b)
        }
        checkBoxWeekForecast.setOnCheckedChangeListener { compoundButton, b ->
            intent.putExtra(IntentHelper.EXTRA_CHECKBOX_WEEK, b)
        }
    }

    private fun addAdapter(savedInstanceState: Bundle?) {
        rvCities.adapter = CityAdapter(Cities.getAllCities(this), CityAdapter.OnCityClickListener { cityPosition ->
            run {
                if (!twoPane) {
                    intent = Intent(this, WeatherActivity::class.java)
                    intent.putExtra(IntentHelper.EXTRA_CITY_POSITION, cityPosition)
                    startActivityForResult(intent, sendRequestCode)
                } else showWeatherForecastFragment(cityPosition)
            }
        })

        if (twoPane && savedInstanceState == null)
            showWeatherForecastFragment(0)
        rvCities.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        when (item!!.itemId) {
//            R.id.menu_add -> {
//                addCity()
//                return true
//            }
//            R.id.menu_clear -> {
//                clearCities()
//                return true
//            }
//            R.id.menu_delete -> {
//                settings.edit().clear()
//                this.onResume()
//                return true
//            }
//
//        }
//        return super.onOptionsItemSelected(item)
//    }


    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val toast = Toast.makeText(applicationContext, "меню в разработке", Toast.LENGTH_SHORT)
        toast.show()
        return super.onContextItemSelected(item)
    }


    private fun deleteCity() {
        val toast = Toast.makeText(applicationContext, "меню в разработке", Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun clearCities() {
        val toast = Toast.makeText(applicationContext, "меню в разработке", Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun addCity() {
        intent = Intent(this, AddCityActivity::class.java)
        startActivity(intent)
    }

    private fun showWeatherForecastFragment(cityPosition: Int) {
        supportFragmentManager.beginTransaction().replace(R.id.flRightContainer,
                WeatherForecastFragment.newInstance(cityPosition))
                .commit()
    }


    override fun onStop() {
        super.onStop()
//        settings.edit().putBoolean(checkBoxPressure.text.toString(), checkBoxPressure.isChecked).apply()
//        settings.edit().putBoolean(checkBoxTomorrowForecast.text.toString(), checkBoxTomorrowForecast.isChecked).apply()
//        settings.edit().putBoolean(checkBoxWeekForecast.text.toString(), checkBoxWeekForecast.isChecked).apply()
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


