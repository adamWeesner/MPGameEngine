package com.weesnerDevelopment.toyGameEngine

import kimchi.logger.LogLevel
import kimchi.logger.LogWriter

class ConsoleLogger : LogWriter {
    override fun log(level: LogLevel, message: String, cause: Throwable?) {
        when (level) {
            LogLevel.TRACE -> print(level, message, cause)
            LogLevel.DEBUG -> print(level, message, cause)
            LogLevel.INFO -> print(level, message, cause)
            LogLevel.WARNING -> print(level, message, cause)
            LogLevel.ERROR -> print(level, message, cause)
        }
    }

    override fun shouldLog(level: LogLevel, cause: Throwable?): Boolean = true

    private fun print(level: LogLevel, message: String, cause: Throwable?) {
        var theCause = ""
        if (cause != null) theCause = " - caused by `$cause`"

        val theMessage = "$message$theCause"

        when (level) {
            LogLevel.TRACE -> println(ConsoleColors.trace(theMessage))
            LogLevel.DEBUG -> println(ConsoleColors.debug(theMessage))
            LogLevel.INFO -> println(ConsoleColors.info(theMessage))
            LogLevel.WARNING -> println(ConsoleColors.warn(theMessage))
            LogLevel.ERROR -> println(ConsoleColors.error(theMessage))
        }
    }
}

object ConsoleColors {
    private const val codeStart = "\u001b"

    private const val normal = "[0"
    private const val bold = "[1"
    private const val underline = "[4"

    private const val red = ";31m"
    private const val green = ";32m"
    private const val yellow = ";33m"
    private const val blue = ";34m"
    private const val purple = ";35m"
    private const val cyan = ";36m"
    private const val white = ";37m"

    private const val reset = "${codeStart}${normal}m"

    fun trace(message: String) = "$codeStart$normal$white$message$reset"
    fun debug(message: String) = message
    fun info(message: String) = "$codeStart$normal$green$message$reset"
    fun warn(message: String) = "$codeStart$normal$yellow$message$reset"
    fun error(message: String) = "$codeStart$bold$red$message$reset"
}