package com.weesnerDevelopment.toyGameEngine

import java.io.File
import java.io.InputStream

interface FileSystem {
    val basePath: String
    operator fun get(fileName: String): InputStream? =
        getAsFile(fileName).inputStream()

    fun getAsFile(fileName: String) = File("$basePath$fileName")
}

/**
 * Resources file system.
 */
val resourcesFs = object : FileSystem {
    override val basePath: String = "data/resources/"
}

/**
 * Music file system.
 */
val musicFs = object : FileSystem {
    override val basePath: String = "data/music/"
}

/**
 * Sound file system.
 */
val soundFs = object : FileSystem {
    override val basePath: String = "data/sound/"
}

/**
 * Assets file system.
 */
val assetsFs = object : FileSystem {
    override val basePath: String = "data/assets/"
}

/**
 * Default (base) file system.
 */
val defaultFs = object : FileSystem {
    override val basePath: String = "data/"
}
