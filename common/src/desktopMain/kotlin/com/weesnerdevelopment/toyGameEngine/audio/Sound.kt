package com.weesnerDevelopment.toyGameEngine.audio

import com.weesnerDevelopment.toyGameEngine.math.map
import kimchi.Kimchi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.BufferedInputStream
import java.io.IOException
import javax.sound.sampled.*
import kotlin.random.Random

actual class Sound(
    private val fileName: String,
    private val scope: CoroutineScope
) {
    private lateinit var clip: Clip

    init {
        scope.launch {
            val file = Sound::class.java.classLoader?.getResourceAsStream(fileName)
            val bufferedStream = BufferedInputStream(file)
            val audioStream = AudioSystem.getAudioInputStream(bufferedStream)
            clip = AudioSystem.getClip().apply {
                Kimchi.trace("opening file $fileName")
                open(audioStream)
                framePosition = 0
            }
        }
    }

    actual fun play(volume: Number) {
        scope.launch {
            delay(Random.nextInt(500).toLong())
            try {
                clip.apply {
                    Kimchi.trace("Attempting to play $fileName - Format: ${format} | Active: ${isActive} | Running: ${isRunning} | Frame Length: ${frameLength}")
                    if (isOpen) {
                        if (isRunning || isActive) {
                            stop()
                            Kimchi.trace("was running or active... should have stopped")
                        }

                        (getControl(FloatControl.Type.MASTER_GAIN) as FloatControl).apply {
                            value = volume.map(1, 100, 0, 6).toFloat()
                        }
                        framePosition = 0
                        Kimchi.trace("reset frame position to zero")
                        start()
                        Kimchi.trace("should be playing sound")
                    } else {
                        Kimchi.warn("Clip line was not open...")
                    }
                }
            } catch (e: UnsupportedAudioFileException) {
                Kimchi.error("Failed to load, unsupported file type $fileName", e)
            } catch (e: LineUnavailableException) {
                Kimchi.error("Failed to load $fileName", e)
            } catch (e: IOException) {
                Kimchi.error("Failed to load $fileName", e)
            }
        }
    }

    actual fun dispose() {
        // no-op
    }
}
