package com.example.ahozyainov.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import com.example.ahozyainov.common.IntentHelper
import com.example.ahozyainov.services.WeatherDataLoadService

class MyLocation
{

    companion object
    {
        private var locationManager: LocationManager? = null
        private lateinit var location: Location
        private const val LOCATION_PROVIDER = LocationManager.PASSIVE_PROVIDER
        private lateinit var geocoder: Geocoder
        private lateinit var cityName: String

        @SuppressLint("MissingPermission")
        fun getMyLocation(context: Context)
        {
            geocoder = Geocoder(context)
            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            location = locationManager!!.getLastKnownLocation(LOCATION_PROVIDER)

            val serviceIntent = Intent(context, WeatherDataLoadService::class.java)
            serviceIntent.putExtra(IntentHelper.EXTRA_COORDINATES, arrayOf(location.latitude.toString(), location.longitude.toString()))
            serviceIntent.putExtra(IntentHelper.EXTRA_CITY_NAME, "")
            context.startService(serviceIntent)
        }


    }


}