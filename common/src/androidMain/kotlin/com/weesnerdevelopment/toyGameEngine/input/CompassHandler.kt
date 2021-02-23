package com.weesnerDevelopment.toyGameEngine.input

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class CompassHandler(
    context: Context
) : SensorEventListener {
    private val manager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var accelerometer: Sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private var magnetometer: Sensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    private var rotation = FloatArray(9) { 0f }
    private var lastAccelerometer = FloatArray(3) { 0f }
    private var lastMagnetometer = FloatArray(3) { 0f }
    private var orientation = FloatArray(3) { 0f }

    lateinit var yaw: Number
        private set
    lateinit var pitch: Number
        private set
    lateinit var roll: Number
        private set

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor == accelerometer) {
            System.arraycopy(event.values, 0, lastAccelerometer, 0, event.values.size)
        } else if (event.sensor == magnetometer) {
            System.arraycopy(event.values, 0, lastMagnetometer, 0, event.values.size)
        }

        SensorManager.getRotationMatrix(rotation, null, lastAccelerometer, lastMagnetometer)
        yaw = orientation[0]
        pitch = orientation[1]
        roll = orientation[2]
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
