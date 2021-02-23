package com.weesnerDevelopment.toyGameEngine.input

import android.content.Context
import android.view.View
import com.weesnerDevelopment.toyGameEngine.input.touch.MultiTouchHandler
import com.weesnerDevelopment.toyGameEngine.math.Size
import com.weesnerDevelopment.toyGameEngine.math.Vector2D
import com.weesnerDevelopment.toyGameEngine.math.Vector3D

actual class Input(
    context: Context,
    view: View,
    scale: Size
) {
    private val accelHandler = AccelerometerHandler(context)
    private val keyHandler = KeyboardHandler(view)
    private val touchHandler = MultiTouchHandler(view, scale)

    actual val accel: Vector3D
        get() = accelHandler.accel

    actual fun isKeyPressed(keyCode: Int): Boolean = keyHandler.isKeyPressed(keyCode)
    actual fun getKeyEvents(): List<KeyEvent> = keyHandler.getKeyEvents()

    actual fun isTouchDown(pointer: Int): Boolean = touchHandler.isTouchDown(pointer)
    actual fun getTouch(pointer: Int): Vector2D = touchHandler.getTouch(pointer)
    actual fun getTouchEvents(): List<TouchEvent> = touchHandler.getTouchEvents()
}
