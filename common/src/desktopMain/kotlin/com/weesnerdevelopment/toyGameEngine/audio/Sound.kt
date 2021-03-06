package com.weesnerDevelopment.toyGameEngine.audio

actual class Sound(
    val fileName: String,
) {
    private val audioUtils = AudioUtils(fileName, Sound::class.java.simpleName)

    actual fun play(volume: Number) = audioUtils.playAudio(volume)

    actual fun dispose() {
        // no-op
    }
}
