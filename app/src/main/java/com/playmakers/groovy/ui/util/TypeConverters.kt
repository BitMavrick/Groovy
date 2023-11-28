package com.playmakers.groovy.ui.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.core.net.toUri
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class TypeConverters {
    @TypeConverter
    fun bitmapToString(bitmap: Bitmap?): String {

        if (bitmap == null) return "0"

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    @TypeConverter
    fun stringToBitmap(string: String?): Bitmap? {

        if (string == "0" || string == null) return null

        val imageBytes = Base64.decode(string,0)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    @TypeConverter
    fun stringToUri(string: String): Uri{
        return string.toUri()
    }

    @TypeConverter
    fun uriToString(uri: Uri) : String{
        return uri.toString()
    }
}