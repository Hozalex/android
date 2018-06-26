package com.example.ahozyainov.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.ahozyainov.activities.R.drawable.avatar
import com.example.ahozyainov.activities.fragments.WeatherForecastFragment
import com.example.ahozyainov.adapters.CityAdapter
import com.example.ahozyainov.common.IntentHelper
import com.example.ahozyainov.models.AvatarDataLoader.Companion.getAvatar
import com.example.ahozyainov.models.Cities
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_layout.*
import java.io.*
import java.net.URL

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{

    private var sharedText = ""
    private val mySettings = "mySettings"
    private val sendRequestCode = 1
    private lateinit var settings: SharedPreferences
    private var twoPane: Boolean = false
    private lateinit var citiesArrayList: ArrayList<Cities>
    private var cityListFileName = "CityList"
    private var avatarFileName = "avatar"
    private lateinit var path: String
    private lateinit var popupMenu: PopupMenu
    private var isPressureChecked: Boolean = false
    private var isHumidityChecked: Boolean = false
    private var isWindChecked: Boolean = false
    private lateinit var ava: ImageView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        twoPane = findViewById<View>(R.id.flRightContainer) != null
        settings = getSharedPreferences(mySettings, Context.MODE_PRIVATE)
        citiesArrayList = ArrayList(10)
        ava = nav_view.getHeaderView(0).findViewById(R.id.ivHeader)
        GetAvatar(ava).execute("http://graph.facebook.com/100000057955641/picture?type=square")
        path = filesDir.toString()
        rvCities.setHasFixedSize(true)
        rvCities.layoutManager = LinearLayoutManager(this)
        registerForContextMenu(rvCities)
        readCityList(path + cityListFileName)

        if (savedInstanceState != null)
        {
            setSavedInstanceCity(savedInstanceState)
        }
        if (settings != null)
        {
            getSettings()
        }

        setSupportActionBar(toolbar)

        initActionBar()

        nav_view.setNavigationItemSelectedListener(this)
        addAdapter(savedInstanceState)
        initPopUpMenu(popButton)


    }

    private fun getSettings()
    {
        isPressureChecked = settings.getBoolean(IntentHelper.EXTRA_CHECKBOX_PRESSURE, true)
        isHumidityChecked = settings.getBoolean(IntentHelper.EXTRA_CHECKBOX_HUMIDITY, true)
        isWindChecked = settings.getBoolean(IntentHelper.EXTRA_CHECKBOX_WIND, true)
    }

    private fun setSavedInstanceCity(savedInstanceState: Bundle)
    {
        var cityList = savedInstanceState.getStringArrayList(IntentHelper.EXTRA_ARRAY_CITIES)
        for (cityName in cityList)
        {
            citiesArrayList.add(Cities(cityName))
        }
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

    private fun initPopUpMenu(it: View)
    {
        popupMenu = PopupMenu(this, it)
        menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

        popButton.setOnClickListener {

            popupMenu.setOnMenuItemClickListener {

                when (it.itemId)
                {
                    R.id.pressure_menu_checkbox ->
                    {
                        it.isChecked = !it.isChecked
                        isPressureChecked = it.isChecked
                        false
                    }
                    R.id.humidity_menu_checkbox ->
                    {
                        it.isChecked = !it.isChecked
                        isHumidityChecked = it.isChecked
                        false
                    }
                    R.id.wind_menu_checkbox ->
                    {
                        it.isChecked = !it.isChecked
                        isWindChecked = it.isChecked
                        false
                    }
                    else ->
                    {
                        super.onOptionsItemSelected(it)
                    }
                }
            }
            popupMenu.show()

        }
    }

    private fun addAdapter(savedInstanceState: Bundle?)
    {
        rvCities.adapter = CityAdapter(citiesArrayList, CityAdapter.OnCityClickListener { cityPosition ->
            run {
                if (!twoPane)
                {
                    intent = Intent(this, WeatherActivity::class.java)
                    intent.putExtra(IntentHelper.EXTRA_CITY_NAME, citiesArrayList[cityPosition].name)
                    intent.putExtra(IntentHelper.EXTRA_CHECKBOX_PRESSURE, isPressureChecked)
                    intent.putExtra(IntentHelper.EXTRA_CHECKBOX_HUMIDITY, isHumidityChecked)
                    intent.putExtra(IntentHelper.EXTRA_CHECKBOX_WIND, isWindChecked)
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

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onContextItemSelected(item: MenuItem?): Boolean
    {

        return super.onContextItemSelected(item)
    }


    private fun deleteCity()
    {
        val toast = Toast.makeText(applicationContext, "меню в разработке", Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun clearCities()
    {
        citiesArrayList.clear()
        addAdapter(savedInstanceState = Bundle())
    }

    private fun addCity()
    {
        val alert = AlertDialog.Builder(this)
        alert.setTitle(R.string.input_city)
        val inputText = EditText(this)
        inputText.setPaddingRelative(16, 0, 0, 0)
        alert.setView(inputText)
        alert.setPositiveButton("Ok") { dialogInterface, i ->
            if (inputText.text.isNotEmpty())
            {
                citiesArrayList.add(Cities(inputText.text.toString()))
                addAdapter(savedInstanceState = Bundle())
            }
        }
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
        outState?.putStringArrayList(IntentHelper.EXTRA_ARRAY_CITIES, getArrayListCities())
        super.onSaveInstanceState(outState)
    }

    private fun getArrayListCities(): ArrayList<String>
    {
        var cityListName: ArrayList<String> = ArrayList()
        for (city in citiesArrayList)
        {
            cityListName.add(city.name)
        }

        return cityListName
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

    override fun onStop()
    {
        saveCityList(path + cityListFileName)
//        saveAvatarInternal(path + avatarFileName)
        settings.edit().putBoolean(IntentHelper.EXTRA_CHECKBOX_PRESSURE, isPressureChecked).apply()
        settings.edit().putBoolean(IntentHelper.EXTRA_CHECKBOX_HUMIDITY, isHumidityChecked).apply()
        settings.edit().putBoolean(IntentHelper.EXTRA_CHECKBOX_WIND, isWindChecked).apply()

        super.onStop()
    }

    private fun saveCityList(path: String)
    {
        var cityList: File
        if (citiesArrayList.isNotEmpty())
        {
            try
            {
                cityList = File(path)
                val fileOutputStream: FileOutputStream
                val objectOutputStream: ObjectOutputStream

                if (!cityList.exists())
                {
                    cityList.createNewFile()
                }

                fileOutputStream = FileOutputStream(cityList, false)
                objectOutputStream = ObjectOutputStream(fileOutputStream)
                objectOutputStream.writeObject(citiesArrayList)

                fileOutputStream.close()
                objectOutputStream.close()
            } catch (e: Exception)
            {
                e.printStackTrace()
            }

        }

    }

    private fun readCityList(path: String)
    {
        var fileInputStream: FileInputStream
        var objectInputStream: ObjectInputStream

        try
        {
            fileInputStream = FileInputStream(path)
            objectInputStream = ObjectInputStream(fileInputStream)
            citiesArrayList = objectInputStream.readObject() as ArrayList<Cities>
            addAdapter(savedInstanceState = Bundle())

            fileInputStream.close()
            objectInputStream.close()
        } catch (e: Exception)
        {
            e.printStackTrace()
        }

    }

//    private fun saveAvatarInternal(path: String)
//    {
//        val avatar: File
//        try
//        {
//            avatar = File(path)
//            val fileOutputStream: FileOutputStream
//            val objectOutputStream: ObjectOutputStream
//
//            if (!avatar.exists())
//            {
//                avatar.createNewFile()
//            }
//
//            fileOutputStream = FileOutputStream(avatar, false)
//            objectOutputStream = ObjectOutputStream(fileOutputStream)
//            objectOutputStream.writeObject(ivHeader)
//
//            fileOutputStream.close()
//            objectOutputStream.close()
//
//        } catch (e: Exception)
//        {
//            e.printStackTrace()
//        }
//
//    }
//
//    private fun readAvatarInternal(path: String)
//    {
//        val fileInputStream: FileInputStream
//        val objectInputStream: ObjectInputStream
//
//        try
//        {
//            fileInputStream = FileInputStream(path)
//            objectInputStream = ObjectInputStream(fileInputStream)
//            val image = objectInputStream.readObject() as ImageView
//
//            fileInputStream.close()
//            objectInputStream.close()
//        } catch (e: Exception)
//        {
//            e.printStackTrace()
//        }
//
//    }

    private class GetAvatar(var imageView: ImageView) : AsyncTask<String, Void, Bitmap>()
    {

        override fun doInBackground(vararg p0: String?): Bitmap?
        {
            val url = URL(p0[0])
            var icon: Bitmap? = null
            try
            {
                icon = BitmapFactory.decodeStream(url.openConnection().getInputStream())

            } catch (e: Exception)
            {
                Log.d("Error", e.message)
                e.printStackTrace()
            }
            return icon
        }

        override fun onPostExecute(result: Bitmap?)
        {
            imageView.setImageResource(R.drawable.abc_ic_star_black_16dp)
        }

    }

}


