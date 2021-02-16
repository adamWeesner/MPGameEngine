package com.weesnerDevelopment.toyGameEngine.audio

import com.weesnerDevelopment.toyGameEngine.math.map

interface Audio {
    fun newMusic(fileName: String): Music
    fun newSound(fileName: String): Sound
}

interface Music {
    val looping: Boolean
    val playing: Boolean
    val stopped: Boolean

    fun play()
    fun stop()
    fun pause()

    fun setLooping(looping: Boolean)
    fun setVolume(volume: Number)

    fun dispose()
}

interface Sound {
    fun play(volume: Number)

    fun dispose()
}

val Number.toVolume: Float
    get() =
        if (this is Float) this
        else this.map(0, 100, 0, 1).toFloat()
