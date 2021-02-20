package com.weesnerDevelopment.toyGameEngine.graphics

import com.weesnerDevelopment.toyGameEngine.math.Size

expect class GraphicsImage {
    val size: Size
    val format: Graphics.GraphicsImageFormat

    fun dispose()
}
