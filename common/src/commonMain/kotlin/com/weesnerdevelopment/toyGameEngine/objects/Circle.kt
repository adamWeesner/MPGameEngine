package com.weesnerDevelopment.toyGameEngine.objects

import com.weesnerDevelopment.toyGameEngine.math.Vector2D
import com.weesnerDevelopment.toyGameEngine.math.compareTo
import com.weesnerDevelopment.toyGameEngine.math.plus
import com.weesnerDevelopment.toyGameEngine.math.pow

data class Circle(
    val center: Vector2D,
    val radius: Number
) : Transform(center) {
    internal fun overlapRectangle(other: Rectangle): Boolean {
        var closestX = center.x
        var closestY = center.y

        closestX = when {
            center.x < other.lowerLeft.x -> other.lowerLeft.x
            center.x > other.lowerLeft.x + other.size.width -> other.lowerLeft.x + other.size.width
            else -> closestX
        }

        closestY = when {
            center.y < other.lowerLeft.y -> other.lowerLeft.y
            center.y > other.lowerLeft.y + other.size.height -> other.lowerLeft.y + other.size.height
            else -> closestY
        }

        return center.distanceSquared(Vector2D(closestX, closestY)) < radius.pow(2)
    }
}
