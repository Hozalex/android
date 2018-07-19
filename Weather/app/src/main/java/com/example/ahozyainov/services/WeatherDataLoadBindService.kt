package com.example.ahozyainov.services

import android.app.Service
import android.content.Intent
import android.os.IBinder

class WeatherDataLoadBindService : Service()

{

    override fun onBind(p0: Intent?): IBinder
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int
    {

        return super.onStartCommand(intent, flags, startId)
    }

}