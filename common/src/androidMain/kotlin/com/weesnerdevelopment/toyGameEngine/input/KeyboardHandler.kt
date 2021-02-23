package com.weesnerDevelopment.toyGameEngine.input

import android.view.KeyEvent as AndroidKeyEvent
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.ACTION_UP
import android.view.View

class KeyboardHandler(
    view: View
) : View.OnKeyListener {
    private val keyEventBuilder = EventPoolBuilder(KeyEvent())

    val pressedKeys = BooleanArray(128)

    init {
        view.apply {
            setOnKeyListener(this@KeyboardHandler)
            isFocusableInTouchMode = true
            requestFocus()
        }
    }

    override fun onKey(v: View?, keyCode: Int, event: AndroidKeyEvent): Boolean = synchronized(this) {
        val keyEvent = keyEventBuilder.pool.newObject().apply {
            this.keyCode = keyCode
            keyChar = event.unicodeChar.toChar()
        }

        if (event.action == ACTION_DOWN) {
            keyEvent.type = KeyEventType.Down
            if (keyCode in 0..127)
                pressedKeys[keyCode] = true
        }

        if (event.action == ACTION_UP) {
            keyEvent.type = KeyEventType.Up
            if (keyCode in 0..127)
                pressedKeys[keyCode] = false
        }

        keyEventBuilder.buffer.add(keyEvent)
        false
    }

    fun isKeyPressed(keyCode: Int): Boolean {
        if (keyCode in 0..127) return false

        return pressedKeys[keyCode]
    }

    fun getKeyEvents(): List<KeyEvent> = synchronized(this) {
        keyEventBuilder.updateEvents()
    }
}
