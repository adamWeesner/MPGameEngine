package com.weesnerDevelopment.desktop

import androidx.compose.desktop.Window
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.weesnerDevelopment.toyGameEngine.ConsoleLogger
import com.weesnerDevelopment.toyGameEngine.math.Vector2D
import kimchi.Kimchi

fun main() = Window {
    remember { Kimchi.addLog(ConsoleLogger()) }

    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop Jvm ${Vector2D(1, 1)}!"
        }) {
            Text(text)
        }
    }
}