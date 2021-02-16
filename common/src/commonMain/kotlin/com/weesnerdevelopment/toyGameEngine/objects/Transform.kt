package com.weesnerDevelopment.toyGameEngine.objects

import com.weesnerDevelopment.toyGameEngine.math.Vector2D
import com.weesnerDevelopment.toyGameEngine.math.compareTo
import com.weesnerDevelopment.toyGameEngine.math.plus
import com.weesnerDevelopment.toyGameEngine.math.pow

open class Transform(
    open val position: Vector2D
) {
    companion object {
        fun Vector2D.inCircle(circle: Circle) =
            circle.center.distanceSquared(this) < circle.radius.pow(2)

        fun Vector2D.inRectangle(rectangle: Rectangle) = rectangle.lowerLeft.x <= x &&
                rectangle.lowerLeft.x + rectangle.size.width >= x &&
                rectangle.lowerLeft.y <= y &&
                rectangle.lowerLeft.y + rectangle.size.height >= y

        fun Vector2D.isIn(transform: Transform) = when (transform) {
            is Circle -> this.inCircle(transform)
            is Rectangle -> this.inRectangle(transform)
            else -> throw IllegalArgumentException()
        }
    }

    open fun isOverlapping(other: Transform) = when (this) {
        is Circle -> {
            when (other) {
                is Circle -> {
                    val distance = center.distanceSquared(other.center)
                    distance <= (radius + other.radius).pow(2)
                }
                is Rectangle -> overlapRectangle(other)
                else -> throw IllegalArgumentException()
            }
        }
        is Rectangle -> {
            when (other) {
                is Circle -> other.overlapRectangle(this)
                is Rectangle ->
                    lowerLeft.x < other.lowerLeft.x + other.size.width &&
                            lowerLeft.x + size.width > other.lowerLeft.x &&
                            lowerLeft.y < other.lowerLeft.y + other.size.height &&
                            lowerLeft.y + size.height > other.lowerLeft.y
                else -> throw IllegalArgumentException()
            }
        }
        else -> throw IllegalArgumentException()
    }
}
