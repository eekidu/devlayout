package com.github.eekidu.dev.devlayout.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.github.eekidu.dev.devlayout.DevLayout

/**
 * @author caohk
 * @date 2023/8/24
 */
class DevLayoutUtil {
    companion object {
        /**
         * dp转px
         *
         * @param dpValue dp值
         * @return px值
         */
        @JvmStatic
        fun dp2px(dpValue: Float): Int {
            val scale = Resources.getSystem().displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * 生成标题TextView
         *
         * @param context
         * @return
         */
        @JvmStatic
        fun generateTitleTv(context: Context): TextView? {
            val textView = TextView(context)
            textView.gravity = Gravity.CENTER
            textView.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            textView.setTextColor(Color.BLACK)
            return textView
        }
    }

}

/**
 * 调节View的宽度
 */
fun DevLayout.addViewHeightAdjust(view: View) {
    val viewHeight = if (view.layoutParams.height <= 0) {
        DevLayoutUtil.dp2px(200f)
    } else {
        view.layoutParams.height
    }
    this.addSeekBar("高度调节") { height ->
        view.layoutParams.height = height
        view.layoutParams = view.layoutParams
    }.setMax(2 * viewHeight).setProgress(viewHeight)

}