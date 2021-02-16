package com.weesnerDevelopment.toyGameEngine.math

import kotlin.math.PI
import kotlin.random.Random

/**
 * Noise referenced from https://github.com/processing/p5.js/blob/1.1.9/src/com.weesnerdevelopment.playingwithgames.com.weesnerDevelopment.gameEngine.com.weesnerDevelopment.toyGameEngine.math/noise.js#L36.
 */
object Noise {
    private val perlinYWrapB: Number = 4
    private val perlinYWrap: Number = 1.shl(perlinYWrapB)
    private val perlinZWrapB: Number = 8
    private val perlinZWrap: Number = 1.shl(perlinZWrapB)
    private val perlinSize: Number = 4095

    private var perlinOctaves: Number = 4 // default to medium smooth
    private var perlinAmplitudeFalloff: Number = 0.5 // 50% reduction/octave

    private val Number.scaledCosine: Number get() = 0.5 * (1.0 - cos(this * PI))

    // will be initialized lazily by [perlin]
    private var perlin: Array<Number>? = null

    private fun perlinValue(of: Number, extra: Number = 0): Number =
        perlin!![(of + extra).and(perlinSize)]

    fun perlin(value: Number, valueY: Number = 0, valueZ: Number = 0): Number {
        var r: Number = 0
        var amplitude: Number = 0.5
        val startPoint = Vector3D(value, valueY, valueZ).apply {
            toAbs()
        }
        val startFloored = Vector3D(
            floor(startPoint.x).toInt(),
            floor(startPoint.y).toInt(),
            floor(startPoint.z).toInt()
        )
        val startMinusFloor = startPoint - startFloored

        if (perlin == null)
            perlin = Array((perlinSize + 1).toInt()) { Random.nextDouble() }


        for (i in 0 until perlinOctaves.toInt()) {
            var of: Number =
                startFloored.x + startFloored.y.shl(perlinYWrapB) + startFloored.z.shl(perlinZWrapB)

            val pointF = Vector2D(startMinusFloor.x.scaledCosine, startMinusFloor.y.scaledCosine)
            val point3DN = Vector3D(
                perlinValue(of),
                perlinValue(of, perlinYWrap),
                perlinValue(of, perlinYWrap)
            )

            point3DN.apply {
                x += pointF.x * (perlinValue(of, 1) - point3DN.x)
                y += pointF.y * (perlinValue(of, perlinYWrap + 1) - y)
                x += pointF.y * (y - x)
            }

            of += perlinZWrap

            point3DN.apply {
                y = perlinValue(of)
                y += pointF.x * (perlinValue(of, 1) - y)
                z += pointF.x * (perlinValue(of, perlinYWrap + 1) - z)
                y += pointF.y * (z - y)
                x += startMinusFloor.z.scaledCosine * (y - x)
            }

            r += point3DN.x * amplitude
            amplitude *= perlinAmplitudeFalloff

            startFloored.x = startFloored.x.shl(1)
            startMinusFloor.x *= 2
            if (startMinusFloor.x >= 1) {
                startFloored.x++
                startMinusFloor.x--
            }

            startFloored.y = startFloored.y.shl(1)
            startMinusFloor.y *= 2
            if (startMinusFloor.y >= 1) {
                startFloored.y++
                startMinusFloor.y--
            }

            startFloored.z = startFloored.z.shl(1)
            startMinusFloor.z *= 2
            if (startMinusFloor.z >= 1) {
                startFloored.z++
                startMinusFloor.z--
            }
        }
        return r
    }
}
