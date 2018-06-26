package com.example.ahozyainov.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Handler
import android.util.Log
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Serializable
import java.net.HttpURLConnection
import java.net.URL

class AvatarDataLoader : Serializable
{

    companion object
    {

        private const val URL_API = "http://graph.facebook.com/%s/picture?type=square"
        private const val USER_ID = "100000057955641"
        var icon: Drawable? = null
        var bIcon: Bitmap? = null

        @JvmStatic
        fun getAvatar(userId: String): Bitmap?
        {
            var handler = Handler()
            Thread(Runnable {
                try
                {
                    var url = URL(String.format(URL_API, userId))

                    handler.postDelayed({
                        bIcon = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                    }, 10000)


//                    icon = Drawable.createFromStream(inputStream, "avatar")

                } catch (e: Exception)
                {
                    e.printStackTrace()
                }
            }).start()

            Log.d("icon", bIcon.toString())
            return bIcon
        }

    }


}