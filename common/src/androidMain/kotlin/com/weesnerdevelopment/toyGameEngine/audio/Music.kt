package com.weesnerDevelopment.toyGameEngine.audio

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import java.io.IOException

actual class Music(
    asset: AssetFileDescriptor
) : MediaPlayer.OnCompletionListener {
    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private var isPrepared: Boolean = false

    init {
        try {
            mediaPlayer.setDataSource(asset.fileDescriptor, asset.startOffset, asset.length)
            mediaPlayer.prepare()
            isPrepared = true
            mediaPlayer.setOnCompletionListener(this)
        } catch (e: Exception) {
            throw RuntimeException("Could not load music")
        }
    }

    actual val looping: Boolean
        get() = mediaPlayer.isLooping

    actual val playing: Boolean
        get() = mediaPlayer.isPlaying

    actual val stopped: Boolean
        get() = !isPrepared

    actual val paused: Boolean
        get() = isPrepared && !mediaPlayer.isPlaying

    actual fun play() {
        if (mediaPlayer.isPlaying) return

        try {
            synchronized(this) {
                if (!isPrepared) mediaPlayer.prepare()

                mediaPlayer.start()
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    actual fun stop() {
        mediaPlayer.stop()
        synchronized(this) {
            isPrepared = false
        }
    }

    actual fun pause() {
        if (mediaPlayer.isPlaying) mediaPlayer.pause()
    }

    actual fun setLooping(looping: Boolean) {
        mediaPlayer.isLooping = looping
    }

    actual fun setVolume(volume: Number) {
        mediaPlayer.setVolume(volume.toVolume, volume.toVolume)
    }

    actual fun dispose() {
        if (mediaPlayer.isPlaying) mediaPlayer.stop()

        mediaPlayer.release()
    }

    override fun onCompletion(mediaPlayer: MediaPlayer?) {
        synchronized(this) {
            isPrepared = false
        }
    }
}
