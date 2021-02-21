package com.weesnerDevelopment.toyGameEngine.graphics

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.weesnerDevelopment.toyGameEngine.assetsFs
import com.weesnerDevelopment.toyGameEngine.math.Size
import com.weesnerDevelopment.toyGameEngine.math.Vector2D
import org.jetbrains.skija.Image
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import androidx.compose.ui.geometry.Size as ComposeSize

val Vector2D.asOffset get() = Offset(x.toFloat(), y.toFloat())
val Vector2D.asIntOffset get() = IntOffset(x.toInt(), y.toInt())

actual class Graphics(
    private val scope: DrawScope
) {
    actual val size: Size = Size(scope.size.width, scope.size.height)

    /**
     * load an image given the [fileName] in either JPEG or PNG format. We specify a desired [format]
     * for the resulting [Image], which is a hint for the loading mechanism.
     */
    actual fun newImage(
        fileName: String,
        format: GraphicsImageFormat
    ) = GraphicsImage(ImageIO.read(assetsFs[fileName]), format)

    /**
     * Take a slice out of the given [image] and return a new [Image].
     */
    actual fun sliceImage(
        image: GraphicsImage,
        position: Vector2D,
        size: Size,
        format: GraphicsImageFormat
    ) = GraphicsImage(
        image.image.getSubimage(
            position.x.toInt(),
            position.y.toInt(),
            size.width.toInt(),
            size.height.toInt()
        ),
        format
    )

    /**
     * Clears the framebuffer with the given [color].
     */
    actual fun clear(color: Int) {}

    /**
     * Set the pixel ata [position] in the framebuffer to the given [color]. Coordinates outside of
     * the screen will be ignored.
     */
    actual fun drawPixel(
        position: Vector2D,
        color: Int
    ) = scope.drawLine(Color(color), position.asOffset, position.asOffset)

    /**
     * Draws a line from [startPosition] to [endPosition] with the given [color]. Coordinates
     * outside of the frame buffers raster will be ignored.
     */
    actual fun drawLine(
        startPosition: Vector2D,
        endPosition: Vector2D,
        color: Int
    ) = scope.drawLine(Color(color), startPosition.asOffset, endPosition.asOffset)

    /**
     * Draws a rectangle to the framebuffer at the given [position] (starting at the top left corner)
     * with the given [size] and fill [color].
     */
    actual fun drawRect(
        position: Vector2D,
        size: Size,
        color: Int
    ) = scope.drawRect(
        Color(color),
        position.asOffset,
        ComposeSize(size.width.toFloat(), size.height.toFloat())
    )

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
        val byteStream = ByteArrayOutputStream()

        ImageIO.write(image.image, "jpg", byteStream)

        scope.drawImage(
            Image.makeFromEncoded(byteStream.toByteArray()).asImageBitmap(),
            srcPosition.asIntOffset,
            IntSize(srcSize.width.toInt(), srcSize.height.toInt()),
            position.asIntOffset,
            IntSize(srcSize.width.toInt(), srcSize.height.toInt())
        )
    }

    /**
     * Draws rectangular portions of a image to the framebuffer at the position (top left corner).
     */
    actual fun drawImage(
        image: GraphicsImage,
        position: Vector2D
    ) {
        val byteStream = ByteArrayOutputStream()

        ImageIO.write(image.image, "jpg", byteStream)

        scope.drawImage(
            Image.makeFromEncoded(byteStream.toByteArray()).asImageBitmap(),
            position.asIntOffset
        )
    }
}
