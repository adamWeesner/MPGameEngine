package com.weesnerDevelopment.desktop

import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.weesnerDevelopment.toyGameEngine.*
import com.weesnerDevelopment.toyGameEngine.audio.Audio
import com.weesnerDevelopment.toyGameEngine.file.FileIO
import com.weesnerDevelopment.toyGameEngine.graphics.Graphics
import com.weesnerDevelopment.toyGameEngine.graphics.GraphicsImage
import com.weesnerDevelopment.toyGameEngine.graphics.GraphicsImageFormat
import com.weesnerDevelopment.toyGameEngine.math.Vector2D
import kimchi.Kimchi
import java.awt.Color
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import javax.imageio.ImageIO

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
                        if (file.exists()) return@entry

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
        val sound = remember { audio.newSound("click.ogg") }
        val (fileText, setFileText) = remember { mutableStateOf("") }
        val (assetText, setAssetText) = remember { mutableStateOf("") }
        val (writtenFileData, setWrittenFileData) = remember { mutableStateOf("") }
        val (image, setImage) = remember { mutableStateOf<ImageBitmap?>(null) }
        val (drawLine, setDrawLine) = remember { mutableStateOf(false) }

        MaterialTheme {
            ScrollableColumn {
                TestingModule(
                    text = "Play Music",
                    lowerPart = {}
                ) { updateText ->
                    updateText(if (music.playing && !music.paused) "Play Music" else "Pause Music")
                    println("button pressed ${music.stopped} | ${music.playing} | ${music.paused}")
                    when {
                        music.stopped -> music.play()
                        music.paused -> music.resume()
                        music.playing -> music.pause()
                    }
                }

                TestingModule(
                    text = "Play Sound",
                    lowerPart = {}
                ) {
                    sound.play(100)
                }

                TestingModule(
                    text = "Read File",
                    lowerPart = {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = fileText
                        )
                    }) {
                    if (fileText.isBlank()) setFileText(fileIO.readFile("file.txt"))
                    else setFileText("")
                }

                TestingModule(
                    text = "Read Asset",
                    lowerPart = {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = assetText
                        )
                    }) {
                    if (assetText.isBlank()) setAssetText(fileIO.readFile("asset.txt"))
                    else setAssetText("")
                }

                TestingModule(
                    text = "Write File",
                    lowerPart = {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = writtenFileData
                        )
                    }
                ) {
                    if (writtenFileData.isBlank()) {
                        fileIO.writeFile("written.txt", "this is a written file\nit is here")
                        setWrittenFileData(fileIO.readFile("written.txt"))
                    } else {
                        fileIO.writeFile("written.txt", "")
                        setWrittenFileData(fileIO.readFile("written.txt"))
                    }
                }

                TestingModule(
                    text = "Read Image",
                    lowerPart = {
                        image?.let {
                            Image(
                                modifier = Modifier.padding(4.dp),
                                bitmap = it
                            )
                        }
                    }
                ) {
                    if (image != null) {
                        setImage(null)
                    } else {
                        val myImage = GraphicsImage(
                            ImageIO.read(assetsFs["image.jpg"]),
                            GraphicsImageFormat.ARGB8888
                        )
                        val byteStream = ByteArrayOutputStream()

                        ImageIO.write(myImage.image, "jpg", byteStream)

                        setImage(
                            org.jetbrains.skija.Image.makeFromEncoded(byteStream.toByteArray())
                                .asImageBitmap()
                        )
                    }
                }

                TestingModule(
                    text = "Draw Line",
                    lowerPart = {
                        if (drawLine) {
                            Canvas(modifier = Modifier.size(100.dp)) {
                                Graphics(this).drawLine(
                                    Vector2D(10, 10),
                                    Vector2D(90, 40),
                                    Color.BLACK.rgb
                                )
                            }
                        }
                    }
                ) {
                    setDrawLine(!drawLine)
                }
            }
        }
    }
}
