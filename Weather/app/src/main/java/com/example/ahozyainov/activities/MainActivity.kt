package com.example.ahozyainov.activities

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import com.example.ahozyainov.activities.fragments.WeatherForecastFragment
import com.example.ahozyainov.adapters.CityAdapter
import com.example.ahozyainov.common.IntentHelper
import com.example.ahozyainov.models.Cities
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{

    private var sharedText = ""
    private val mySettings = "mySettings"
    private val sendRequestCode = 1
    private lateinit var settings: SharedPreferences
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        twoPane = findViewById<View>(R.id.flRightContainer) != null
        settings = getSharedPreferences(mySettings, Context.MODE_PRIVATE)
        rvCities.setHasFixedSize(true)
        rvCities.layoutManager = LinearLayoutManager(this)
        if (savedInstanceState != null)
        {
            sharedText = savedInstanceState.getString(IntentHelper.EXTRA_SHARED_WEATHER)
            text_view_main.text = sharedText
        }

        setSupportActionBar(toolbar)
        initActionBar()
        nav_view.setNavigationItemSelectedListener(this)

        addAdapter(savedInstanceState)
        initPopUpMenu()

    }

    private fun initActionBar()
    {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.open, R.string.close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean
    {
        when (item.itemId)
        {
            R.id.menu_info ->
            {
                val toast = Toast.makeText(applicationContext, R.string.attention_info, Toast.LENGTH_LONG)
                toast.duration = Toast.LENGTH_LONG
                toast.show()
                return true
            }
            R.id.menu_about ->
            {
                val toast = Toast.makeText(applicationContext, R.string.about_text, Toast.LENGTH_LONG)
                toast.duration = Toast.LENGTH_LONG
                toast.show()
                return true
            }
            R.id.menu_feedback ->
            {
                intent = Intent(Intent.ACTION_SEND)
                intent.type = "message/email"
                intent.putExtra(Intent.EXTRA_EMAIL, getText(R.string.feedback_mail_to))
                intent.putExtra(Intent.EXTRA_SUBJECT, getText(R.string.feedback_mail_subject))

                if (intent.resolveActivity(packageManager) != null)
                {
                    startActivity(intent)
                }

            }

        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true

    }

    private fun initPopUpMenu()
    {
        popButton.setOnClickListener {
            val popupMenu = PopupMenu(this, it)
            menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId)
                {
                    R.id.pressure_menu_checkbox ->
                    {
                        it.isChecked = !it.isChecked
                        true
                    }
                    R.id.tomorrow_menu_checkbox ->
                    {

                        true
                    }
                    R.id.week_menu_checkbox ->
                    {

                        true
                    }
                    else ->
                    {
                        false
                    }
                }
            }
            popupMenu.show()

        }
    }

    private fun addAdapter(savedInstanceState: Bundle?)
    {
        rvCities.adapter = CityAdapter(Cities.getAllCities(this), CityAdapter.OnCityClickListener { cityPosition ->
            run {
                if (!twoPane)
                {
                    intent = Intent(this, WeatherActivity::class.java)
                    intent.putExtra(IntentHelper.EXTRA_CITY_POSITION, cityPosition)
                    startActivityForResult(intent, sendRequestCode)
                } else
                {
                    showWeatherForecastFragment(cityPosition)
                    popButton.hide()
                }
            }
        })

        if (twoPane && savedInstanceState == null)
        {
            showWeatherForecastFragment(0)
            popButton.hide()
        }

        rvCities.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean
    {
        when (item!!.itemId)
        {
            R.id.menu_add ->
            {
                addCity()
                return true
            }
            R.id.menu_clear ->
            {
                clearCities()
                return true
            }
            R.id.menu_delete ->
            {
                settings.edit().clear()
                this.onResume()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onContextItemSelected(item: MenuItem?): Boolean
    {
        val toast = Toast.makeText(applicationContext, "меню в разработке", Toast.LENGTH_SHORT)
        toast.show()
        return super.onContextItemSelected(item)
    }


    private fun deleteCity()
    {
        val toast = Toast.makeText(applicationContext, "меню в разработке", Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun clearCities()
    {
        val toast = Toast.makeText(applicationContext, "меню в разработке", Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun addCity()
    {
        var alert = AlertDialog.Builder(this)
        alert.setTitle(R.string.input_city)
        var inputText = EditText(this)
        alert.setView(inputText)
        alert.setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i ->
            if (inputText.text != null)
            {
                //TODO
            }
        })
        alert.show()
    }

    private fun showWeatherForecastFragment(cityPosition: Int)
    {
        supportFragmentManager.beginTransaction().replace(R.id.flRightContainer,
                WeatherForecastFragment.newInstance(cityPosition))
                .commit()
    }

    override fun onSaveInstanceState(outState: Bundle?)
    {
        outState?.putString(IntentHelper.EXTRA_SHARED_WEATHER, sharedText)
        super.onSaveInstanceState(outState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        if (requestCode == sendRequestCode)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                sharedText = data!!.getStringExtra(IntentHelper.EXTRA_SHARED_WEATHER)
                text_view_main.text = sharedText
            }
        }
    }


}


