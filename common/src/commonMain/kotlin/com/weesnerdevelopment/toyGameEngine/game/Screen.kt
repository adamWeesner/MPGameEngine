package com.weesnerDevelopment.toyGameEngine.game

import com.weesnerDevelopment.toyGameEngine.input.TouchEvent
import com.weesnerDevelopment.toyGameEngine.math.*

abstract class Screen(game: Game) {
    open fun update(deltaTime: Float) {}
    open fun present(deltaTime: Float) {}

    open fun pause() {}
    open fun resume() {}

    open fun dispose() {}

    fun inBounds(
        event: TouchEvent,
        position: Vector2D,
        size: Size,
        truth: () -> Unit
    ) {
        val pos = event.position
        if (pos.x > position.x && pos.x < position.x + size.width - 1 &&
            pos.y > position.y && pos.y < position.y + size.height - 1
        )
            truth()
    }
}
