package com.weesnerDevelopment.toyGameEngine.input

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.weesnerDevelopment.toyGameEngine.math.Vector3D

class AccelerometerHandler(
    context: Context
) : SensorEventListener {
    lateinit var accel: Vector3D
        private set

    init {
        val manager: SensorManager =
            context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensorList = manager.getSensorList(Sensor.TYPE_ACCELEROMETER)
        if (sensorList.size != 0) {
            val accelerometer = sensorList[0]
            manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        accel = Vector3D(event.values[0], event.values[1], event.values[2])
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
