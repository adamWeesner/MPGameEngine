package com.weesnerDevelopment.toyGameEngine.audio

import com.weesnerDevelopment.toyGameEngine.Logger

actual class Audio {
    private val loadedMusic = mutableListOf<Music>()
    private val loadedSounds = mutableListOf<Sound>()

    actual fun newMusic(fileName: String): Music =
        loadedMusic.firstOrNull { it.fileName == fileName }?.also {
            Logger.trace("Using cached music with name $fileName")
        } ?: Music(fileName).also {
            Logger.trace("Added new music with name $fileName")
            loadedMusic.add(it)
        }

    actual fun newSound(fileName: String): Sound =
        loadedSounds.firstOrNull { it.fileName == fileName }?.also {
            Logger.trace("Using cached sound with name $fileName")
        } ?: Sound(fileName).also {
            Logger.trace("Added new sound with name $fileName")
            loadedSounds.add(it)
        }
}
