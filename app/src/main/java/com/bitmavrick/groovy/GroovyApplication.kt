package com.bitmavrick.groovy

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.bitmavrick.groovy.core.util.ColorUtils
import com.google.android.material.color.DynamicColors
import kotlin.system.exitProcess

class GroovyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        when (prefs.getString("theme_mode", "0")) {
            "0" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }

            "1" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

            "2" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        ColorUtils.overrideGlobalAmoledColor = prefs.getBoolean("oled", false)


        DynamicColors.applyToActivitiesIfAvailable(this)

        Thread.setDefaultUncaughtExceptionHandler { _, paramThrowable ->
            val exceptionMessage = android.util.Log.getStackTraceString(paramThrowable)

            val intent = Intent(this, BugHandler::class.java)
            intent.putExtra("exception_message", exceptionMessage)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

            android.os.Process.killProcess(android.os.Process.myPid())
            exitProcess(10)
        }
    }
}