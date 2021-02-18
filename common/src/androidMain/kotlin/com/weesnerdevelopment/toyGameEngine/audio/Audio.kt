package com.weesnerDevelopment.toyGameEngine.audio

import android.app.Activity
import android.content.res.AssetManager
import android.media.SoundPool
import java.io.IOException

actual class Audio(
    activity: Activity
) {
    private val assetManager: AssetManager = activity.assets
    private val soundPool: SoundPool = SoundPool.Builder().build()

    actual fun newMusic(fileName: String): Music {
        try {
            val asset = assetManager.openFd(fileName)
            return Music(asset)
        } catch (e: IOException) {
            throw RuntimeException("Could not load music $fileName")
        }
    }

    actual fun newSound(fileName: String): Sound {
        try {
            val asset = assetManager.openFd(fileName)
            val soundId = soundPool.load(asset, 0)
            return Sound(soundPool, soundId)
        } catch (e: IOException) {
            throw RuntimeException("Could not load sound $fileName")
        }
    }
}