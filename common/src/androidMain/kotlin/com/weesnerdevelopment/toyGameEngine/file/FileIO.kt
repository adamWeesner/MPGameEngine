package com.weesnerDevelopment.toyGameEngine.file

import android.content.Context
import android.content.res.AssetManager
import java.io.*

actual class FileIO(
    private val context: Context
) {
    private val assetManager: AssetManager = context.assets
    private val externalStoragePath: String =
        context.filesDir.absolutePath + File.separator

    /**
     * reads the given [fileName] data.
     *
     * @return the data given from the [fileName].
     */
    actual fun readAsset(fileName: String): String =
        assetManager.open(fileName).bufferedReader().readText()

    /**
     * reads the given [fileName] data.
     *
     * @return the data given from the [fileName].
     */
    actual fun readFile(fileName: String): String =
        FileInputStream(externalStoragePath + fileName).bufferedReader().readText()

    /**
     * Writes the [data] to the given [fileName].
     */
    actual fun writeFile(fileName: String, data: String): Boolean = runCatching {
        BufferedWriter(
            OutputStreamWriter(
                FileOutputStream(externalStoragePath + fileName)
            )
        ).write(data)
    }.isSuccess
}
