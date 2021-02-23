package com.weesnerDevelopment.toyGameEngine.input

import com.weesnerDevelopment.toyGameEngine.math.Vector2D
import com.weesnerDevelopment.toyGameEngine.math.Vector3D
import java.awt.KeyboardFocusManager.getCurrentKeyboardFocusManager
import java.awt.event.KeyEvent.KEY_PRESSED
import java.awt.event.KeyEvent.KEY_RELEASED

actual class Input {
    private val keyEventBuilder = EventPoolBuilder(KeyEvent())

    private val pressedKeys = BooleanArray(128)

    init {
        getCurrentKeyboardFocusManager().addKeyEventDispatcher {
            val keyEvent = keyEventBuilder.pool.newObject().apply {
                this.keyCode = it.keyCode
                keyChar = it.keyChar
            }

            if (it.id == KEY_PRESSED) {
                keyEvent.type = KeyEventType.Down
                if (it.keyCode in 0..127) pressedKeys[it.keyCode] = true
                println("key ${keyEvent.keyChar} pressed")
            }

            if (it.id == KEY_RELEASED) {
                keyEvent.type = KeyEventType.Up
                if (it.keyCode in 0..127) pressedKeys[it.keyCode] = false
                println("key ${keyEvent.keyChar} released")
            }

            keyEventBuilder.buffer.add(keyEvent)
            false
        }
    }

    actual val accel: Vector3D
        get() = TODO("Not yet implemented")

    actual fun isKeyPressed(keyCode: Int): Boolean = pressedKeys[keyCode]

    actual fun getKeyEvents(): List<KeyEvent> = synchronized(this) {
        keyEventBuilder.updateEvents()
    }

    actual fun isTouchDown(pointer: Int): Boolean {
        TODO("Not yet implemented")
    }

    actual fun getTouch(pointer: Int): Vector2D {
        TODO("Not yet implemented")
    }

    actual fun getTouchEvents(): List<TouchEvent> {
        TODO("Not yet implemented")
    }
}