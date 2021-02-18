package com.weesnerDevelopment.toyGameEngine.audio

import com.weesnerDevelopment.toyGameEngine.Logger

actual class Audio {
    actual fun newMusic(fileName: String): Music {
        TODO("Not yet implemented")
    }

    actual fun newSound(fileName: String): Sound {
        Logger.trace("Added new sound with name $fileName")
        return Sound(fileName)
    }
}
