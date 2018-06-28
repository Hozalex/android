package com.example.ahozyainov.models

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class WeatherDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION)
{
    val TAG = javaClass.simpleName
    val TABLE = DB_NAME
    val DATABASE_CREATE = "CREATE TABLE " + TABLE + " (" +
            "$ID integer PRIMARY KEY autoincrement," +
            "$CITY text," +
            "$WEATHER_DATA text," +
            "$TIMESTAMP integer" +
            ")"

    companion object
    {
        private const val DB_NAME = "weatherDB"
        private const val DB_VERSION = 1
        private const val ID = "_id"
        private const val CITY = "CITY"
        private const val WEATHER_DATA = "WEATHER_DATA"
        private const val TIMESTAMP = "TIMESTAMP"

    }

    fun cityWeather(city: String, weatherData: String)
    {
        val values = ContentValues()
        values.put(CITY, city)
        values.put(WEATHER_DATA, weatherData)
        values.put(TIMESTAMP, System.currentTimeMillis())
        writableDatabase.insert(TABLE, null, values)
    }

    fun getCityWeather(): Cursor
    {
        return readableDatabase.query(TABLE, arrayOf(ID, TIMESTAMP, CITY, WEATHER_DATA),
                null, null, null, null, null)
    }


    override fun onCreate(db: SQLiteDatabase)
    {
        Log.d(TAG, "Create: $DATABASE_CREATE")
        db.execSQL(DATABASE_CREATE)

    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}