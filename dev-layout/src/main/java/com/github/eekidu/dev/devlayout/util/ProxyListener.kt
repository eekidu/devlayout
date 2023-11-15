package com.github.eekidu.dev.devlayout.util

import java.lang.reflect.InvocationHandler
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.Locale

/**
 * 监听器代理类
 * @author caohk
 * @date 2023/9/2
 */
open class ProxyListener<T>(
    private val iLogger: ILogger,
    private val title: String,
    private val realListener: T,
) : InvocationHandler {

    companion object {
        @JvmStatic
        fun <T> getProxy(
            devLayout: ILogger,
            title: String,
            tInterface: Class<T>,
            realListener: T
        ): T {
            return Proxy.newProxyInstance(
                javaClass.classLoader,
                arrayOf<Class<*>>(tInterface),
                ProxyListener(devLayout, title, realListener)
            ) as T
        }
    }


    override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any? {
        var result: Any? = null

        val start = DevLayoutUtil.getTime()//1、记录起始时间

        try {//2、执行
            result = method.invoke(realListener, *(args ?: emptyArray()))
        } catch (ex: Throwable) {
            var targetException = ex
            if (ex is InvocationTargetException) {
                targetException = ex.targetException
            }
            iLogger.logE("Error", targetException, true)
        }

        val nanoTime = DevLayoutUtil.getTime() - start//3、计算耗时
        val ms = nanoTime / 1000000f
        val format = String.format(Locale.US, "%.3fms", ms)
        val log = "$title [耗时 : $format]"
        if (ms > 16) {
            iLogger.logW(log)
        } else {
            iLogger.logI(log)
        }
        return result
    }
}