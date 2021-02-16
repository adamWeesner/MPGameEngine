package com.weesnerDevelopment.toyGameEngine.objects

import com.weesnerDevelopment.toyGameEngine.math.Size
import com.weesnerDevelopment.toyGameEngine.math.Vector2D

data class Rectangle(
    var lowerLeft: Vector2D,
    val size: Size
) : Transform(lowerLeft)
