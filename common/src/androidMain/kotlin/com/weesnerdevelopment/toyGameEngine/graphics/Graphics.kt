package com.weesnerDevelopment.toyGameEngine.graphics

import android.content.res.AssetManager
import android.graphics.*
import com.weesnerDevelopment.toyGameEngine.math.Size
import com.weesnerDevelopment.toyGameEngine.math.Vector2D
import com.weesnerDevelopment.toyGameEngine.math.minus
import com.weesnerDevelopment.toyGameEngine.math.plus
import java.io.IOException
import java.io.InputStream

actual class Graphics(
    private val assets: AssetManager,
    frameBuffer: Bitmap
) {
    private val canvas: Canvas = Canvas(frameBuffer)
    private val paint: Paint = Paint()

    private val srcRect = Rect()
    private val destRect = Rect()

    actual val size: Size = Size(frameBuffer.width, frameBuffer.height)

    /**
     * load an image given the [fileName] in either JPEG or PNG format. We specify a desired [format]
     * for the resulting [Image], which is a hint for the loading mechanism.
     */
    actual fun newImage(
        fileName: String,
        format: GraphicsImageFormat
    ): GraphicsImage {
        val config: Bitmap.Config = when (format) {
            GraphicsImageFormat.RGB565 -> Bitmap.Config.RGB_565
            else -> Bitmap.Config.ARGB_8888
        }

        BitmapFactory.Options().apply {
            inPreferredConfig = config
        }

        var input: InputStream? = null
        val bitmap: Bitmap?

        try {
            input = assets.open(fileName)
            bitmap = BitmapFactory.decodeStream(input)
            if (bitmap == null)
                throw RuntimeException("Could not load bitmap from asset $fileName")
        } catch (e: IOException) {
            throw RuntimeException("Could not load bitmap from asset $fileName")
        } finally {
            if (input != null)
                try {
                    input.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
        }

        val newFormat =
            if (bitmap?.config == Bitmap.Config.RGB_565) GraphicsImageFormat.RGB565
            else GraphicsImageFormat.ARGB8888

        return GraphicsImage(bitmap!!, newFormat)
    }

    /**
     * Take a slice out of the given [image] and return a new [Image].
     */
    actual fun sliceImage(
        image: GraphicsImage,
        position: Vector2D,
        size: Size,
        format: GraphicsImageFormat
    ): GraphicsImage {
        val config: Bitmap.Config = when (format) {
            GraphicsImageFormat.RGB565 -> Bitmap.Config.RGB_565
            else -> Bitmap.Config.ARGB_8888
        }

        BitmapFactory.Options().apply {
            inPreferredConfig = config
        }

        val bitmap: Bitmap = image.bitmap
        val slicedBitmap = Bitmap.createBitmap(
            bitmap,
            position.x.toInt(),
            position.y.toInt(),
            size.width.toInt(),
            size.height.toInt()
        )

        val newFormat =
            if (slicedBitmap.config == Bitmap.Config.RGB_565) GraphicsImageFormat.RGB565
            else GraphicsImageFormat.ARGB8888

        return GraphicsImage(slicedBitmap, newFormat)
    }

    /**
     * Clears the framebuffer with the given [color].
     */
    actual fun clear(color: Int) {
        canvas.drawRGB((color and 0Xff0000) shr 16, (color and 0xff00) shr 8, (color and 0xff))
    }

    /**
     * Set the pixel ata [position] in the framebuffer to the given [color]. Coordinates outside of
     * the screen will be ignored.
     */
    actual fun drawPixel(
        position: Vector2D,
        color: Int
    ) {
        paint.color = color
        canvas.drawPoint(position.x.toFloat(), position.y.toFloat(), paint)
    }

    /**
     * Draws a line from [startPosition] to [endPosition] with the given [color]. Coordinates
     * outside of the frame buffers raster will be ignored.
     */
    actual fun drawLine(
        startPosition: Vector2D,
        endPosition: Vector2D,
        color: Int
    ) {
        paint.color = color
        canvas.drawLine(
            startPosition.x.toFloat(),
            startPosition.y.toFloat(),
            endPosition.x.toFloat(),
            endPosition.y.toFloat(),
            paint
        )
    }

    /**
     * Draws a rectangle to the framebuffer at the given [position] (starting at the top left corner)
     * with the given [size] and fill [color].
     */
    actual fun drawRect(
        position: Vector2D,
        size: Size,
        color: Int
    ) {
        paint.apply {
            this.color = color
            style = Paint.Style.FILL
        }
        canvas.drawRect(
            position.x.toFloat(),
            position.y.toFloat(),
            (position.x + size.width - 1).toFloat(),
            (position.y + size.width - 1).toFloat(),
            paint
        )
    }

    /**
     * Draws rectangular portions of a [image] to the framebuffer at the [position] (top left corner)
     * [srcPosition] is the specified coordinating to be used from the [image], given its own
     * coordinate system. The [srcSize] is the size of the portion to take from the [image].
     */
    actual fun drawImage(
        image: GraphicsImage,
        position: Vector2D,
        srcPosition: Vector2D,
        srcSize: Size
    ) {
        srcRect.apply {
            left = srcPosition.x.toInt()
            top = srcPosition.y.toInt()
            right = (srcPosition.x + srcSize.width - 1).toInt()
            bottom = (srcPosition.y + srcSize.height - 1).toInt()
        }
        destRect.apply {
            left = position.x.toInt()
            top = position.y.toInt()
            right = (position.x + srcSize.width - 1).toInt()
            bottom = (position.y + srcSize.height - 1).toInt()
        }

        canvas.drawBitmap(
            image.bitmap,
            srcRect,
            destRect,
            null
        )
    }

    /**
     * Draws rectangular portions of a image to the framebuffer at the position (top left corner).
     */
    actual fun drawImage(
        image: GraphicsImage,
        position: Vector2D
    ) {
        canvas.drawBitmap(
            image.bitmap,
            position.x.toFloat(),
            position.y.toFloat(),
            null
        )
    }
}
