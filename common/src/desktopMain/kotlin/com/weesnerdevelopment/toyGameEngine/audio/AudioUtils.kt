package com.weesnerDevelopment.toyGameEngine.audio

import com.weesnerDevelopment.toyGameEngine.Logger
import com.weesnerDevelopment.toyGameEngine.musicFs
import com.weesnerDevelopment.toyGameEngine.soundFs
import java.io.*
import javax.sound.sampled.*
import kotlin.math.log10

internal class AudioUtils(
    private val fileName: String,
    private val type: String
) {
    var isPlaying = false
        private set

    var stopped = true
    var paused = false
    private var pausePosition = -1

    fun playAudio(volume: Number) {
        Thread {
            val data = when (type) {
                Sound::class.java.simpleName -> soundFs[fileName]
                Music::class.java.simpleName -> musicFs[fileName]
                else -> return@Thread
            } ?: return@Thread

            // open audio file
            val audioFile = BufferedInputStream(data)

            // open the stream (not yet decoded)
            val fileIn: AudioInputStream = AudioSystem.getAudioInputStream(audioFile)

            // derive a suitable PCM format that can be played by the AudioSystem
            val desiredFormat: AudioFormat = fileIn.format.toSignedPCM()

            // ask the AudioSystem for a source line for playback that corresponds to the derived PCM format
            val line: SourceDataLine = AudioSystem.getSourceDataLine(desiredFormat)

            var samplesRead = 0
            val soundBuffer = ByteArray(4096)
            // convert to raw PCM samples with the AudioSystem
            try {
                AudioSystem.getAudioInputStream(desiredFormat, fileIn).use { rawIn ->
                    line.open()

                    if (line.isControlSupported(FloatControl.Type.VOLUME)) {
                        (line.getControl(FloatControl.Type.VOLUME) as FloatControl).apply {
                            value = volume.toFloat()
                        }
                    } else if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                        (line.getControl(FloatControl.Type.MASTER_GAIN) as FloatControl).apply {
                            value = 20f * log10(volume.toFloat() / 100)
                        }
                    }

                    isPlaying = true
                    stopped = false
                    line.start()

                    var bytesRead = rawIn.read(soundBuffer)
                    while (bytesRead >= 0) {
                        if (stopped) {
                            line.stop()
                            break
                        }
                        if (paused) {
                            runCatching { Thread.sleep(20) }
                            continue
                        }

                        samplesRead += bytesRead

                        if (samplesRead >= 0) {
                            if (pausePosition < 0)
                                pausePosition = (samplesRead - bytesRead) / 4
                            // only write bytes we really read, not more!
                            line.write(soundBuffer, 0, bytesRead)
                            //Logger.trace("Current position in microseconds: ${line.microsecondPosition}")
                        }
                        bytesRead = rawIn.read(soundBuffer)
                    }
                }
            } catch (e: IOException) {
                Logger.error("Failed to play sound", e)
            } catch (e: LineUnavailableException) {
                Logger.error("Failed to play sound", e)
            } finally {
                isPlaying = false
                stopped = true
                line.drain()
                line.stop()
            }
        }.start()
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