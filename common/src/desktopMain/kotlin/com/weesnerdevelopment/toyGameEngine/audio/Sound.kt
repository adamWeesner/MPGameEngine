package com.weesnerDevelopment.toyGameEngine.audio

actual class Sound(
    val fileName: String,
) {
    private val audioUtils = AudioUtils(fileName)

    actual fun play(volume: Number) = audioUtils.playAudio(volume)

    actual fun dispose() {
        // no-op
    }
}
