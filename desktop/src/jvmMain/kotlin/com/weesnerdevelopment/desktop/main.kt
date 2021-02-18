package com.weesnerDevelopment.desktop

import androidx.compose.desktop.Window
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.weesnerDevelopment.toyGameEngine.ConsoleLogger
import com.weesnerDevelopment.toyGameEngine.audio.Sound
import com.weesnerDevelopment.toyGameEngine.math.Vector2D
import kimchi.Kimchi

fun main() = Window {
    remember { Kimchi.addLog(ConsoleLogger()) }
    val sound = Sound("jump.wav", rememberCoroutineScope())
    val sound2 = Sound("jump.wav", rememberCoroutineScope())

    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop Jvm ${Vector2D(1, 1)}!"
            sound.play(100)
            sound2.play(50)
        }) {
            Text(text)
        }
    }
}