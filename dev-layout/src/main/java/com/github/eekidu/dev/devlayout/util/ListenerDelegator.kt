package com.github.eekidu.dev.devlayout.util

import android.util.Log
import com.github.eekidu.dev.devlayout.DevLayout
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.Locale

/**
 * 监听器代理类
 * @author caohk
 * @date 2023/9/2
 */
open class ListenerDelegator<T>(
    private val devLayout: DevLayout,
    private val title: String,
    private val target: T,
) : InvocationHandler {

    companion object {
        @JvmStatic
        fun <T> getDelegator(devLayout: DevLayout, title: String, tClazz: Class<T>, target: T): T {
            return Proxy.newProxyInstance(
                javaClass.classLoader,
                arrayOf<Class<*>>(tClazz),
                ListenerDelegator(devLayout, title, target)
            ) as T
        }
    }


    override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any? {
        var result: Any? = null
        try {
            val start = DevLayoutUtil.getTime()

            result = method.invoke(target, *(args ?: emptyArray()))

            val nanoTime = DevLayoutUtil.getTime() - start
            val ms = nanoTime / 1000000f
            val format = String.format(Locale.US, "%.3fms", ms)
            val log = "$title [耗时 : $format]"

            if (ms > 16) {
                if (devLayout.hasLogMonitor()) {
                    devLayout.logW(log)
                } else {
                    Log.w(DevLayout.TAG, log)
                }
            } else {
                if (devLayout.hasLogMonitor()) {
                    devLayout.log(log)
                } else {
                    Log.v(DevLayout.TAG, log)
                }
            }
        } catch (ex: Exception) {
            if (devLayout.hasLogMonitor() && devLayout.logMonitorLayout!!.enablePrintError()) {
                ex.printStackTrace()
                devLayout.logE("Error", ex)
            } else {
                throw ex
            }
        }
        return result
    }
}