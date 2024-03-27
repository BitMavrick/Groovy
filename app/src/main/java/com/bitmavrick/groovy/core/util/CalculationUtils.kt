package com.bitmavrick.groovy.core.util

import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CalculationUtils {
    fun convertDurationToTimeStamp(duration: Long): String {
        val minutes = duration / 1000 / 60
        val seconds = duration / 1000 - minutes * 60
        if (seconds < 10) {
            return "$minutes:0$seconds"
        }
        return "$minutes:$seconds"
    }

    fun convertUnixTimestampToMonthDay(unixTimestamp: Long): String =
        SimpleDateFormat(
            "MM-dd",
            Locale.getDefault()
        ).format(
            Date(unixTimestamp * 1000)
        )

    @ColorInt
    fun setAlphaComponent(
        @ColorInt color: Int,
        @IntRange(from = 0x0, to = 0xFF) alpha: Int
    ): Int {
        require(!(alpha < 0 || alpha > 255)) { "alpha must be between 0 and 255." }
        return color and 0x00ffffff or (alpha shl 24)
    }

    fun constrain(amount: Float, low: Float, high: Float): Float {
        return if (amount < low) low else amount.coerceAtMost(high)
    }


    fun lerp(start: Float, stop: Float, amount: Float): Float {
        return start + (stop - start) * amount
    }

    fun lerp(start: Int, stop: Int, amount: Float): Float {
        return lerp(start.toFloat(), stop.toFloat(), amount)
    }

    fun lerpInv(a: Float, b: Float, value: Float): Float {
        return if (a != b) (value - a) / (b - a) else 0.0f
    }

    fun saturate(value: Float): Float {
        return constrain(value, 0.0f, 1.0f)
    }

    fun lerpInvSat(a: Float, b: Float, value: Float): Float {
        return saturate(lerpInv(a, b, value))
    }

}