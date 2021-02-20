package com.weesnerDevelopment.toyGameEngine.graphics

import com.weesnerDevelopment.toyGameEngine.math.Size
import java.awt.image.BufferedImage

actual class GraphicsImage(
    val image: BufferedImage,
    actual val format: Graphics.GraphicsImageFormat
) {
    actual val size: Size = Size(
        image.width,
        image.height
    )

    actual fun dispose() {
        image.flush()
    }
}
