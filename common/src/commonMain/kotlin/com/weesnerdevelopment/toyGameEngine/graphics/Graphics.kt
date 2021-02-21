package com.weesnerDevelopment.toyGameEngine.graphics

import com.weesnerDevelopment.toyGameEngine.math.Size
import com.weesnerDevelopment.toyGameEngine.math.Vector2D

expect class Graphics {
    val size: Size

    /**
     * load an image given the [fileName] in either JPEG or PNG format. We specify a desired [format]
     * for the resulting [Image], which is a hint for the loading mechanism.
     */
    fun newImage(fileName: String, format: GraphicsImageFormat): GraphicsImage

    /**
     * Take a slice out of the given [image] and return a new [Image].
     */
    fun sliceImage(image: GraphicsImage, position: Vector2D, size: Size, format: GraphicsImageFormat): GraphicsImage

    /**
     * Clears the framebuffer with the given [color].
     */
    fun clear(color: Int)

    /**
     * Set the pixel ata [position] in the framebuffer to the given [color]. Coordinates outside of
     * the screen will be ignored.
     */
    fun drawPixel(position: Vector2D, color: Int)

    /**
     * Draws a line from [startPosition] to [endPosition] with the given [color]. Coordinates
     * outside of the frame buffers raster will be ignored.
     */
    fun drawLine(startPosition: Vector2D, endPosition: Vector2D, color: Int)

    /**
     * Draws a rectangle to the framebuffer at the given [position] (starting at the top left corner)
     * with the given [size] and fill [color].
     */
    fun drawRect(position: Vector2D, size: Size, color: Int)

    /**
     * Draws rectangular portions of a [image] to the framebuffer at the [position] (top left corner)
     * [srcPosition] is the specified coordinating to be used from the [image], given its own
     * coordinate system. The [srcSize] is the size of the portion to take from the [image].
     */
    fun drawImage(image: GraphicsImage, position: Vector2D, srcPosition: Vector2D, srcSize: Size)

    /**
     * Draws rectangular portions of a image to the framebuffer at the position (top left corner).
     */
    fun drawImage(image: GraphicsImage, position: Vector2D)
}

enum class GraphicsImageFormat {
    ARGB8888,
    RGB565,
}