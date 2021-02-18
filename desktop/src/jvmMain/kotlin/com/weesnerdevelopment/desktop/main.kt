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
    val sound = audio.newSound("jump.wav")

    val (playSound, setPlaySound) = remember { mutableStateOf(false) }

    if (playSound) {
        rememberCoroutineScope().launch(Dispatchers.IO) {
            sound.play(100)
        }
        setPlaySound(false)
    }

    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop Jvm ${Vector2D(1, 1)}!"
            setPlaySound(true)
        }) {
            Text(text)
        }
    }
}