package com.weesnerDevelopment.toyGameEngine.graphics

import com.weesnerDevelopment.toyGameEngine.math.Size

interface GraphicsImage {
    val size: Size
    val format: Graphics.GraphicsImageFormat

    fun dispose()
}
