package com.weesnerDevelopment.toyGameEngine.file

expect class FileIO {
    /**
     * reads the given [fileName] data.
     *
     * @return the data given from the [fileName].
     */
    fun readAsset(fileName: String): String

    /**
     * reads the given [fileName] data.
     *
     * @return the data given from the [fileName].
     */
    fun readFile(fileName: String): String

    /**
     * Writes the [data] to the given [fileName].
     */
    fun writeFile(fileName: String, data: String): Boolean
}
