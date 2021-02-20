package com.weesnerDevelopment.toyGameEngine.file

import com.weesnerDevelopment.toyGameEngine.Logger
import com.weesnerDevelopment.toyGameEngine.assetsFs
import com.weesnerDevelopment.toyGameEngine.defaultFs
import java.io.File

actual class FileIO {
    /**
     * reads the given [fileName] data.
     *
     * @return the data given from the [fileName].
     */
    actual fun readAsset(fileName: String): String {
        val readText = runCatching {
            assetsFs[fileName]?.bufferedReader()?.readText()
        }

        return readText.getOrNull() ?: throw readText.exceptionOrNull()
            ?: throw IllegalStateException("Could not read asset")
    }

    /**
     * reads the given [fileName] data.
     *
     * @return the data given from the [fileName].
     */
    actual fun readFile(fileName: String): String {
        val readText = runCatching {
            defaultFs[fileName]?.bufferedReader()?.readText()
        }

        return readText.getOrNull() ?: throw readText.exceptionOrNull()
            ?: throw IllegalStateException("Could not read file $fileName")
    }

    /**
     * Writes the [data] to the given [fileName].
     */
    actual fun writeFile(fileName: String, data: String): Boolean = runCatching {
        val dir = defaultFs.getAsFile(fileName)
        if (!dir.exists()) {
            File(dir.path.replaceAfterLast("/", "")).mkdirs()
            dir.createNewFile()
        }

        dir.writeText(data).also {
            Logger.trace("should have written data to $dir:\n$data\n")
        }
    }.isSuccess
}
