package com.github.eekidu.dev.devlayout.util

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import com.github.eekidu.dev.devlayout.DevLayout
import github.eekidu.dev.devlayout.R

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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                textView.setTextAppearance(R.style.DevLayoutTitleTv)
            } else {
                textView.setTextAppearance(context, R.style.DevLayoutTitleTv)
            }
            return textView
        }

        @JvmStatic
        fun getTime(): Long {
            return System.nanoTime()
        }

        @JvmStatic
        fun getParentDevLayout(view: View): DevLayout? {
            if (view is DevLayout) {
                return view
            } else {
                if (view.parent != null) {
                    return getParentDevLayout(view.parent as View)
                }
            }
            return null
        }
    }

}

/**
 * 调节View的宽度
 */
fun DevLayout.addViewHeightAdjust(targetView: View) {
    val viewHeight = if (targetView.layoutParams.height <= 0) {
        DevLayoutUtil.dp2px(200f)
    } else {
        targetView.layoutParams.height
    }
    this.addSeekBar("高度调节") { height ->
        targetView.layoutParams.height = height
        targetView.layoutParams = targetView.layoutParams
    }.setMax(2 * viewHeight).setProgress(viewHeight)
}

fun TextView.setTextViewAutoSize(minSp: Int, maxSp: Int) {
    TextViewCompat.setAutoSizeTextTypeWithDefaults(
        this,
        TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM
    )
    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
        this,
        minSp,
        maxSp,
        1,
        TypedValue.COMPLEX_UNIT_SP
    )
}