package com.weesnerDevelopment.desktop

import androidx.compose.desktop.Window
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.weesnerDevelopment.toyGameEngine.ConsoleLogger
import com.weesnerDevelopment.toyGameEngine.audio.Audio
import com.weesnerDevelopment.toyGameEngine.math.Vector2D
import kimchi.Kimchi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val audio = Audio()

fun main() = Window {
    remember { Kimchi.addLog(ConsoleLogger()) }
    val music = remember { audio.newMusic("music.mp3") }

    val (playSound, setPlaySound) = remember { mutableStateOf(false) }

    if (playSound) {
        rememberCoroutineScope().launch(Dispatchers.IO) {
            println("should be playing music")
            music.play()
        }
    } else {
        if (music.playing) {
            println("should be pausing music")
            music.pause()
        }
    }

    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop Jvm ${Vector2D(1, 1)}!"
            setPlaySound(!playSound)
        }) {
            Text(text)
        }
    }
}
