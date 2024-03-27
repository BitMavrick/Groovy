package com.bitmavrick.groovy.ui.component

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.view.animation.PathInterpolator
import androidx.annotation.VisibleForTesting
import com.bitmavrick.groovy.core.util.CalculationUtils.lerp
import com.bitmavrick.groovy.core.util.CalculationUtils.lerpInv
import com.bitmavrick.groovy.core.util.CalculationUtils.lerpInvSat
import com.bitmavrick.groovy.core.util.CalculationUtils.setAlphaComponent
import kotlin.math.abs
import kotlin.math.cos


class SquigglyProgress : Drawable() {

    companion object {
        private const val TWO_PI = (Math.PI * 2f).toFloat()

        @VisibleForTesting
        internal const val DISABLED_ALPHA = 77
    }

    private val wavePaint = Paint()
    private val linePaint = Paint()
    private val path = Path()
    private var heightFraction = 0f
    private var heightAnimator: ValueAnimator? = null
    private var phaseOffset = 0f
    private var lastFrameTime = -1L

    private val transitionPeriods = 1.5f

    private val minWaveEndpoint = 0f

    private val matchedWaveEndpoint = 1f

    var waveLength = 0f

    var lineAmplitude = 0f

    var phaseSpeed = 0f

    var strokeWidth = 0f
        set(value) {
            if (field == value) {
                return
            }
            field = value
            wavePaint.strokeWidth = value
            linePaint.strokeWidth = value
        }

    var transitionEnabled = true
        set(value) {
            field = value
            invalidateSelf()
        }

    init {
        wavePaint.strokeCap = Paint.Cap.ROUND
        linePaint.strokeCap = Paint.Cap.ROUND
        linePaint.style = Paint.Style.STROKE
        wavePaint.style = Paint.Style.STROKE
        linePaint.alpha = DISABLED_ALPHA
    }

    var animate: Boolean = false
        set(value) {
            if (field == value) {
                return
            }
            field = value
            if (field) {
                lastFrameTime = SystemClock.uptimeMillis()
            }
            heightAnimator?.cancel()
            heightAnimator =
                ValueAnimator.ofFloat(heightFraction, if (animate) 1f else 0f).apply {
                    if (animate) {
                        startDelay = 60
                        duration = 800
                        interpolator = PathInterpolator(
                            0.05f, 0.7f, 0.1f, 1f
                        )
                    } else {
                        duration = 550
                        interpolator = PathInterpolator(
                            0f, 0f, 0f, 1f
                        )
                    }
                    addUpdateListener {
                        heightFraction = it.animatedValue as Float
                        invalidateSelf()
                    }
                    addListener(
                        object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                heightAnimator = null
                            }
                        }
                    )
                    start()
                }
        }

    override fun draw(canvas: Canvas) {
        if (animate) {
            invalidateSelf()
            val now = SystemClock.uptimeMillis()
            phaseOffset += (now - lastFrameTime) / 1000f * phaseSpeed
            phaseOffset %= waveLength
            lastFrameTime = now
        }

        val progress = level / 10_000f
        val totalWidth = bounds.width().toFloat()
        val totalProgressPx = totalWidth * progress
        val waveProgressPx =
            totalWidth *
                    (if (!transitionEnabled || progress > matchedWaveEndpoint) progress
                    else
                        lerp(
                            minWaveEndpoint,
                            matchedWaveEndpoint,
                            lerpInv(0f, matchedWaveEndpoint, progress)
                        ))

        val waveStart = -phaseOffset - waveLength / 2f
        val waveEnd = if (transitionEnabled) totalWidth else waveProgressPx

        val computeAmplitude: (Float, Float) -> Float = { x, sign ->
            if (transitionEnabled) {
                val length = transitionPeriods * waveLength
                val coeff =
                    lerpInvSat(waveProgressPx + length / 2f, waveProgressPx - length / 2f, x)
                sign * heightFraction * lineAmplitude * coeff
            } else {
                sign * heightFraction * lineAmplitude
            }
        }

        path.rewind()
        path.moveTo(waveStart, 0f)

        var currentX = waveStart
        var waveSign = 1f
        var currentAmp = computeAmplitude(currentX, waveSign)
        val dist = waveLength / 2f
        while (currentX < waveEnd) {
            waveSign = -waveSign
            val nextX = currentX + dist
            val midX = currentX + dist / 2
            val nextAmp = computeAmplitude(nextX, waveSign)
            path.cubicTo(midX, currentAmp, midX, nextAmp, nextX, nextAmp)
            currentAmp = nextAmp
            currentX = nextX
        }

        val clipTop = lineAmplitude + strokeWidth
        canvas.save()
        canvas.translate(bounds.left.toFloat(), bounds.centerY().toFloat())

        canvas.save()
        canvas.clipRect(0f, -1f * clipTop, totalProgressPx, clipTop)
        canvas.drawPath(path, wavePaint)
        canvas.restore()

        if (transitionEnabled) {

            canvas.save()
            canvas.clipRect(totalProgressPx, -1f * clipTop, totalWidth, clipTop)
            canvas.drawPath(path, linePaint)
            canvas.restore()
        } else {
            canvas.drawLine(totalProgressPx, 0f, totalWidth, 0f, linePaint)
        }

        val startAmp = cos(abs(waveStart) / waveLength * TWO_PI)
        canvas.drawPoint(0f, startAmp * lineAmplitude * heightFraction, wavePaint)

        canvas.restore()
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("PixelFormat.TRANSLUCENT", "android.graphics.PixelFormat")
    )
    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        wavePaint.colorFilter = colorFilter
        linePaint.colorFilter = colorFilter
    }

    override fun setAlpha(alpha: Int) {
        updateColors(wavePaint.color, alpha)
    }

    override fun getAlpha(): Int {
        return wavePaint.alpha
    }

    override fun setTint(tintColor: Int) {
        updateColors(tintColor, alpha)
    }

    override fun onLevelChange(level: Int): Boolean {
        return animate
    }

    override fun setTintList(tint: ColorStateList?) {
        if (tint == null) {
            return
        }
        updateColors(tint.defaultColor, alpha)
    }

    private fun updateColors(tintColor: Int, alpha: Int) {
        wavePaint.color = setAlphaComponent(tintColor, alpha)
        linePaint.color =
            setAlphaComponent(tintColor, (DISABLED_ALPHA * (alpha / 255f)).toInt())
    }
}