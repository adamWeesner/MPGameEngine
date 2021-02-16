package com.weesnerDevelopment.toyGameEngine.game

import com.weesnerDevelopment.toyGameEngine.audio.Audio
import com.weesnerDevelopment.toyGameEngine.file.FileIO
import com.weesnerDevelopment.toyGameEngine.graphics.Graphics
import com.weesnerDevelopment.toyGameEngine.input.Input
import com.weesnerDevelopment.toyGameEngine.util.Stack

interface Game {
    val startScreen: Screen

    var graphics: Graphics
    var audio: Audio
    var input: Input
    var fileIO: FileIO
    var screen: Screen

    val backStack: Stack<Screen>

    fun back()
}
