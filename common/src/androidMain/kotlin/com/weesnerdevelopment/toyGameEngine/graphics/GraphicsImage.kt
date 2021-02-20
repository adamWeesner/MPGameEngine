package com.weesnerDevelopment.toyGameEngine.graphics

import android.graphics.Bitmap
import com.weesnerDevelopment.toyGameEngine.math.Size

actual class GraphicsImage(
    val bitmap: Bitmap,
    actual val format: Graphics.GraphicsImageFormat
) {
    actual val size: Size = Size(
        bitmap.width,
        bitmap.height
    )

    actual fun dispose() {
        bitmap.recycle()
    }
}
