package com.weesnerDevelopment.toyGameEngine.audio

actual class Music(
    val fileName: String
) {
    private var volume: Number = 100
    private var mutableLooping = false
    private val audioUtils = AudioUtils(fileName)

    actual val looping: Boolean
        get() = mutableLooping

    actual val playing: Boolean
        get() = audioUtils.isPlaying

    actual val stopped: Boolean
        get() = audioUtils.stopped


    actual fun play() = audioUtils.playAudio(volume)

    actual fun stop() {
        audioUtils.stopped = true
    }

    actual fun pause() {
        audioUtils.paused = true
    }

    fun resume() {
        audioUtils.paused = false
    }

    actual fun setLooping(looping: Boolean) {
        mutableLooping = looping
    }

    actual fun setVolume(volume: Number) {
        this.volume = volume
    }

    actual fun dispose() {

    }
}
