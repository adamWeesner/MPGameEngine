package com.weesnerDevelopment.toyGameEngine.input.touch

import android.view.View
import com.weesnerDevelopment.toyGameEngine.input.TouchEvent
import com.weesnerDevelopment.toyGameEngine.math.Vector2D

interface TouchHandler : View.OnTouchListener {
    fun isTouchDown(pointer: Int): Boolean
    fun getTouch(pointer: Int): Vector2D
    fun getTouchEvents(): List<TouchEvent>
}
