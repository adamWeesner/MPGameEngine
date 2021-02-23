package com.weesnerDevelopment.toyGameEngine.input.touch

import android.view.MotionEvent
import android.view.View
import com.weesnerDevelopment.toyGameEngine.input.EventPoolBuilder
import com.weesnerDevelopment.toyGameEngine.input.TouchEvent
import com.weesnerDevelopment.toyGameEngine.input.TouchEventType
import com.weesnerDevelopment.toyGameEngine.math.Size
import com.weesnerDevelopment.toyGameEngine.math.Vector2D

class SingleTouchHandler(
    view: View,
    private val scale: Size
) : TouchHandler {
    private val touchEventBuilder = EventPoolBuilder(TouchEvent())
    var isTouched: Boolean = false
    lateinit var touch: Vector2D

    init {
        view.setOnTouchListener(this)
    }

    override fun isTouchDown(pointer: Int): Boolean = synchronized(this) {
        if (pointer == 0) isTouched else false
    }

    override fun getTouch(pointer: Int): Vector2D = synchronized(this) {
        touch
    }

    override fun getTouchEvents(): List<TouchEvent> = synchronized(this) {
        touchEventBuilder.updateEvents()
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean = synchronized(this) {
        val touchEvent = touchEventBuilder.pool.newObject()

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchEvent.type = TouchEventType.Down
                isTouched = true
            }
            MotionEvent.ACTION_MOVE -> {
                touchEvent.type = TouchEventType.Dragged
                isTouched = true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                touchEvent.type = TouchEventType.Up
                isTouched = false
            }
        }

        touchEvent.position = Vector2D(
            event.x * scale.width.toFloat(),
            event.y * scale.height.toFloat()
        )
        touchEventBuilder.buffer.add(touchEvent)

        true
    }
}
