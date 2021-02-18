package com.weesnerDevelopment.toyGameEngine

import java.io.InputStream

object FileSystem {
    operator fun get(fileName: String): InputStream? =
        FileSystem::class.java.classLoader?.getResourceAsStream(fileName)
}

val resourcesFs = FileSystem