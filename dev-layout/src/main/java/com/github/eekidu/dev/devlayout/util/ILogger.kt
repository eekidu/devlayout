package com.github.eekidu.dev.devlayout.util

/**
 * @author caohk
 * @date 2023/10/26
 */
interface ILogger {
    fun log(log: CharSequence?)
    fun logV(log: CharSequence?)
    fun logI(log: CharSequence?)
    fun logD(log: CharSequence?)
    fun logW(log: CharSequence?)
    fun logE(log: CharSequence?, throwable: Throwable? = null, isFromProxyListener: Boolean = false)
    fun log(level: Int, log: CharSequence?)
}