package com.weesnerDevelopment.toyGameEngine.objects

import com.weesnerDevelopment.toyGameEngine.math.Size
import com.weesnerDevelopment.toyGameEngine.math.Vector2D
import com.weesnerDevelopment.toyGameEngine.math.div
import com.weesnerDevelopment.toyGameEngine.math.minus

abstract class GameObject {
    abstract val position: Vector2D
    abstract val size: Size
    val bounds
        get() = Rectangle(
            Vector2D(position.x - size.width / 2f, position.y - size.height / 2f),
            size
        )

    open fun update(deltaTime: Number) {}
}

data class StaticGameObject(
    override val position: Vector2D,
    override val size: Size
) : GameObject()

open class DynamicGameObject(
    override var position: Vector2D,
    override val size: Size
) : GameObject() {
    var velocity = Vector2D(0,0)
    val acceleration = Vector2D(0,0)
}
