package com.zup.appkoin.view.util

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.graphics.Canvas
import android.hardware.Sensor
import android.hardware.Sensor.TYPE_ACCELEROMETER
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView


/**
 * author Helio Hachimine
 */


class ParallaxView : AppCompatImageView, SensorEventListener {

    private var mMovementMultiplier = DEFAULT_MOVEMENT_MULTIPLIER.toFloat()
    private var mSensorDelay = DEFAULT_SENSOR_DELAY
    private var mMinMovedPixelsToUpdate = DEFAULT_MIN_MOVED_PIXELS
    private var mMinSensibility = DEFAULT_MIN_SENSIBILITY

    private var mSensorX: Float = 0.toFloat()
    private var mSensorY: Float = 0.toFloat()
    private var mFirstSensorX: Float? = null
    private var mFirstSensorY: Float? = null
    private var mPreviousSensorX: Float? = null
    private var mPreviousSensorY: Float? = null

    private var mTranslationX = 0f
    private var mTranslationY = 0f

    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null

    private val isSensorValuesMovedEnough: Boolean
        get() = mSensorX > mPreviousSensorX!! + mMinSensibility ||
                mSensorX < mPreviousSensorX!! - mMinSensibility ||
                mSensorY > mPreviousSensorY!! + mMinSensibility ||
                mSensorY < mPreviousSensorX!! - mMinSensibility

    enum class SensorDelay {
        FASTEST,
        GAME,
        UI,
        NORMAL
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    fun init() {
        mSensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager!!.getDefaultSensor(TYPE_ACCELEROMETER)
    }

    private fun setNewPosition() {
        val destinyX = ((mFirstSensorX!! - mSensorX) * mMovementMultiplier).toInt()
        val destinyY = ((mFirstSensorY!! - mSensorY) * mMovementMultiplier).toInt()

        calculateTranslationX(destinyX)
        calculateTranslationY(destinyY)
    }

    private fun calculateTranslationX(destinyX: Int) {
        if (mTranslationX + mMinMovedPixelsToUpdate < destinyX)
            mTranslationX++
        else if (mTranslationX - mMinMovedPixelsToUpdate > destinyX)
            mTranslationX--
    }

    private fun calculateTranslationY(destinyY: Int) {
        if (mTranslationY + mMinMovedPixelsToUpdate < destinyY)
            mTranslationY++
        else if (mTranslationY - mMinMovedPixelsToUpdate > destinyY)
            mTranslationY--
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        translationX = mTranslationX
        translationY = mTranslationY
        invalidate()
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == TYPE_ACCELEROMETER) {
            mSensorX = event.values[0]
            //mSensorY = -event.values[1]

            manageSensorValues()
        }
    }

    private fun manageSensorValues() {
        if (mFirstSensorX == null)
            setFirstSensorValues()

        if (mPreviousSensorX == null || isSensorValuesMovedEnough) {
            setNewPosition()
            setPreviousSensorValues()
        }
    }

    private fun setFirstSensorValues() {
        mFirstSensorX = mSensorX
        mFirstSensorY = mSensorY
    }

    private fun setPreviousSensorValues() {
        mPreviousSensorX = mSensorX
        mPreviousSensorY = mSensorY
    }

    fun registerSensorListener() {
        mSensorManager!!.registerListener(this, mAccelerometer, mSensorDelay)
    }

    fun registerSensorListener(sensorDelay: SensorDelay) {
        when (sensorDelay) {
            SensorDelay.FASTEST -> mSensorDelay = SensorManager.SENSOR_DELAY_FASTEST
            SensorDelay.GAME -> mSensorDelay = SensorManager.SENSOR_DELAY_GAME
            SensorDelay.UI -> mSensorDelay = SensorManager.SENSOR_DELAY_UI
            SensorDelay.NORMAL -> mSensorDelay = SensorManager.SENSOR_DELAY_NORMAL
        }
        registerSensorListener()
    }

    fun unregisterSensorListener() {
        mSensorManager!!.unregisterListener(this)
    }

    fun setMovementMultiplier(multiplier: Float) {
        mMovementMultiplier = multiplier
    }

    fun setMinimumMovedPixelsToUpdate(minMovedPixelsToUpdate: Int) {
        mMinMovedPixelsToUpdate = minMovedPixelsToUpdate
    }

    fun setMinimumSensibility(minSensibility: Int) {
        mMinSensibility = minSensibility.toFloat()
    }

    override fun onAccuracyChanged(sensor: Sensor, i: Int) {}

    companion object {

        private val DEFAULT_SENSOR_DELAY = SensorManager.SENSOR_DELAY_FASTEST
        val DEFAULT_MOVEMENT_MULTIPLIER = 3
        val DEFAULT_MIN_MOVED_PIXELS = 1
        private val DEFAULT_MIN_SENSIBILITY = 0f
    }
}