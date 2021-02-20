package com.weesnerDevelopment.desktop

import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.weesnerDevelopment.toyGameEngine.ConsoleLogger
import com.weesnerDevelopment.toyGameEngine.FileSystem
import com.weesnerDevelopment.toyGameEngine.Logger
import com.weesnerDevelopment.toyGameEngine.audio.Audio
import com.weesnerDevelopment.toyGameEngine.defaultFs
import com.weesnerDevelopment.toyGameEngine.file.FileIO
import kimchi.Kimchi
import java.io.File
import java.io.FileOutputStream
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream


val audio = Audio()
val fileIO = FileIO()

private fun FileSystem.dataCopy() {
    val location = this::class.java.protectionDomain?.codeSource?.location
    location?.openStream()?.let {
        ZipInputStream(it).apply {
            var entry: ZipEntry? = null
            while (nextEntry.also { entry = it } != null) {
                entry?.apply entry@{
                    if (name.startsWith("data") && !name.endsWith("/")) {
                        println("\nentry $name found..")

                        val file = File("${defaultFs.basePath}${name.replace("data/", "")}")
                        if(file.exists()) return@entry

                        File(file.path.replaceAfterLast("/", "")).mkdirs()
                        file.createNewFile()

                        val os = FileOutputStream(file)
                        var chars: Int = read()
                        while (chars != -1) {
                            os.write(chars)
                            chars = read()
                        }
                        os.close()
                        println("should have written file to ${file.path}")
                    }
                }
            }
        }
    }
}

fun main() = defaultFs.dataCopy().let {
    Window(
        title = "Desktop App"
    ) {
        remember { Kimchi.addLog(ConsoleLogger()) }

        val music = remember { audio.newMusic("music.mp3") }
        val (textMusic, setTextMusic) = remember { mutableStateOf("Play Music") }
        val (playMusic, setPlayMusic) = remember { mutableStateOf(false) }

        val sound = remember { audio.newSound("click.ogg") }
        val textSound by remember { mutableStateOf("Play Sound") }
        val (playSound, setPlaySound) = remember { mutableStateOf(false) }

        val (readFile, setReadFile) = remember { mutableStateOf(false) }
        val (fileText, setFileText) = remember { mutableStateOf("") }

        val (readAsset, setReadAsset) = remember { mutableStateOf(false) }
        val (assetText, setAssetText) = remember { mutableStateOf("") }

        val (writeFile, setWriteFile) = remember { mutableStateOf(false) }
        val (writtenFileData, setWrittenFileData) = remember { mutableStateOf("") }

        if (playMusic) {
            if (music.stopped) music.play()
            else music.resume()
        } else {
            if (music.playing) music.pause()
        }

        if (playSound) {
            setPlaySound(false)
            sound.play(100)
        }

        if (readFile) {
            if (fileText.isBlank()) setFileText(fileIO.readFile("file.txt"))
            else setFileText("")
            setReadFile(false)
        }

        if (readAsset) {
            if (assetText.isBlank()) setAssetText(fileIO.readFile("asset.txt"))
            else setAssetText("")
            setReadAsset(false)
        }

        if (writeFile) {
            setWriteFile(false)
            if (writtenFileData.isBlank()) {
                fileIO.writeFile("written.txt", "this is a written file\nit is here")
                setWrittenFileData(fileIO.readFile("written.txt"))
            } else {
                fileIO.writeFile("written.txt", "")
                setWrittenFileData(fileIO.readFile("written.txt"))
            }
        }

        MaterialTheme {
            Column {
                Button(
                    modifier = Modifier.padding(4.dp),
                    onClick = {
                        setPlayMusic(!playMusic)
                        setTextMusic(if (playMusic) "Play Music" else "Pause Music")
                    }) {
                    Text(textMusic)
                }

                Button(
                    modifier = Modifier.padding(4.dp),
                    onClick = {
                        setPlaySound(true)
                    }) {
                    Text(textSound)
                }

                Button(
                    modifier = Modifier.padding(4.dp),
                    onClick = {
                        setReadFile(true)
                    }) {
                    Text("Read File")
                }

                Text(
                    modifier = Modifier.padding(4.dp),
                    text = fileText
                )

                Button(
                    modifier = Modifier.padding(4.dp),
                    onClick = {
                        setReadAsset(true)
                    }) {
                    Text("Read Asset")
                }

                Text(
                    modifier = Modifier.padding(4.dp),
                    text = assetText
                )

                Button(
                    modifier = Modifier.padding(4.dp),
                    onClick = {
                        setWriteFile(true)
                    }) {
                    Text("Write File")
                }

                Text(
                    modifier = Modifier.padding(4.dp),
                    text = writtenFileData
                )
            }
        }
    }
}
