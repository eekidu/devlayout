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
open class ProxyListener<T>(
    private val devLayout: DevLayout,
    private val title: String,
    private val realListener: T,
) : InvocationHandler {

    companion object {
        @JvmStatic
        fun <T> getProxy(
            devLayout: DevLayout,
            title: String,
            tClazz: Class<T>,
            realListener: T
        ): T {
            return Proxy.newProxyInstance(
                javaClass.classLoader,
                arrayOf<Class<*>>(tClazz),
                ProxyListener(devLayout, title, realListener)
            ) as T
        }
    }


    override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any? {
        var result: Any? = null

        val start = DevLayoutUtil.getTime()//1、记录起始时间

        try {
            result = method.invoke(realListener, *(args ?: emptyArray()))
        } catch (ex: Exception) {
            if (devLayout.hasLogMonitor() && devLayout.logMonitorLayout!!.enablePrintError()) {
                ex.printStackTrace()
                devLayout.logE("Error", ex)
            } else {
                throw ex
            }
        }

        val nanoTime = DevLayoutUtil.getTime() - start//3、计算耗时
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

        return result
    }
}