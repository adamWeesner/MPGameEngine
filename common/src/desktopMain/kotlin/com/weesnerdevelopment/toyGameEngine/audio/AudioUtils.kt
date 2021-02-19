package com.weesnerDevelopment.toyGameEngine.audio

import com.weesnerDevelopment.toyGameEngine.Logger
import com.weesnerDevelopment.toyGameEngine.resourcesFs
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import javax.sound.sampled.*
import kotlin.math.log10

internal class AudioUtils(
    fileName: String
) {
    // open audio file
    private val data: InputStream? = resourcesFs[fileName]
    private val audioFile = BufferedInputStream(data)

    // open the stream (not yet decoded)
    private val fileIn: AudioInputStream = AudioSystem.getAudioInputStream(audioFile)

    // derive a suitable PCM format that can be played by the AudioSystem
    private val desiredFormat: AudioFormat = fileIn.format.toSignedPCM()

    // ask the AudioSystem for a source line for playback that corresponds to the derived PCM format
    private val line: SourceDataLine = AudioSystem.getSourceDataLine(desiredFormat)

    var isPlaying = false
        private set

    var stopped = false
    var paused = false

    fun playAudio(volume: Number) {
        val soundBuffer = ByteArray(4096)
        var justRead: Int
        // convert to raw PCM samples with the AudioSystem
        try {
            AudioSystem.getAudioInputStream(desiredFormat, fileIn).use { rawIn ->
                line.open()

                if (line.isControlSupported(FloatControl.Type.VOLUME)) {
                    (line.getControl(FloatControl.Type.VOLUME) as FloatControl).apply {
                        value = volume.toFloat()
                        Logger.trace("volume used $volume")
                    }
                } else if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    (line.getControl(FloatControl.Type.MASTER_GAIN) as FloatControl).apply {
                        value = 20f * log10(volume.toFloat() / 100)
                        Logger.trace("gain used $value")
                    }
                }

                isPlaying = true
                line.start()

                while (rawIn.read(soundBuffer).also { justRead = it } >= 0) {
                    // only write bytes we really read, not more!
                    line.write(soundBuffer, 0, justRead)
                    Logger.trace("Current position in microseconds: ${line.microsecondPosition}")

                }
            }
        } catch (e: IOException) {
            Logger.error("Failed to play sound", e)
        } catch (e: LineUnavailableException) {
            Logger.error("Failed to play sound", e)
        } finally {
            println("finally tripped...")
            isPlaying = false
            line.drain()
            line.stop()
        }
    }

    /**
     * Derive a PCM format.
     */
    private fun AudioFormat.toSignedPCM(): AudioFormat {
        val sampleSizeInBits = if (sampleSizeInBits <= 0) 16 else sampleSizeInBits
        val channels = if (channels <= 0) 2 else channels
        val sampleRate = if (sampleRate <= 0) 44100f else sampleRate
        return AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED,
            sampleRate,
            sampleSizeInBits,
            channels,
            if (sampleSizeInBits > 0 && channels > 0) sampleSizeInBits / 8 * channels else AudioSystem.NOT_SPECIFIED,
            sampleRate,
            isBigEndian
        )
    }
}