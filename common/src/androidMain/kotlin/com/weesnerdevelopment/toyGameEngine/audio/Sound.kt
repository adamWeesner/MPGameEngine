package com.weesnerDevelopment.toyGameEngine.audio

import android.media.SoundPool

actual class Sound(
    private val soundPool: SoundPool,
    private val soundId: Int
) {
    actual fun play(volume: Number) {
        soundPool.play(soundId, volume.toVolume, volume.toVolume, 0, 0, 1f)
    }

    actual fun dispose() {
        soundPool.unload(soundId)
    }
}
