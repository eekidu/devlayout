package com.github.eekidu.dev.devlayout.widget

import android.app.Dialog
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.github.eekidu.dev.devlayout.DevLayout
import com.github.eekidu.dev.devlayout.util.DevLayoutUtil
import github.eekidu.dev.devlayout.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue
import kotlin.math.max


/**
 * 日志框
 * @author caohk
 * @date 2023/9/2
 */

class LogMonitorLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val stepSize = DevLayoutUtil.dp2px(100f)
    private val minHeight = DevLayoutUtil.dp2px(120f)
    private val animateDuration = 200L

    private val menuLayout: ViewGroup
    private val clearBt: View
    private val menuBt: View

    private val logRecyclerView: RecyclerView
    private val filter = LogFilter()
    private val logAdapter = LogAdapter(context, filter)
    private val linearLayoutManager = LinearLayoutManager(context)
    private var filterSetDialog: FilterDialog
    private var menuShowFlag = true

    private var lastClickChangeMenuShowTime = 0L

    init {
        inflate(context, R.layout.dev_layout_log_monitor, this)
        filterSetDialog = FilterDialog(getContext(), this)
        logRecyclerView = findViewById(R.id.recyclerView)
        logRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = logAdapter
            addOnItemTouchListener(object : OnItemTouchListener {
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    rv.requestDisallowInterceptTouchEvent(true)
                    return false
                }

                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                }

                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                }
            })
            isNestedScrollingEnabled = false
        }

        val autoScrollBottom = AutoScrollBottom(logRecyclerView, logAdapter)

        findViewById<CheckBox>(R.id.timeCheckBox).apply {
            setOnCheckedChangeListener { buttonView, isChecked ->
                logAdapter.setShowTime(isChecked)
            }
            isChecked = true
        }

        clearBt = findViewById<View>(R.id.clearAllBt)
        clearBt.setOnClickListener {
            logAdapter.clearData()
            showMenu()
        }

        findViewById<View>(R.id.zoomUp).setOnClickListener {
            changeSizeBy(stepSize)

        }
        findViewById<View>(R.id.zoomDown).setOnClickListener {
            changeSizeBy(-stepSize)
        }

        findViewById<View>(R.id.goBottomBt).setOnClickListener {
            if (logAdapter.itemCount > 0) {
                logRecyclerView.scrollToPosition(logAdapter.itemCount - 1)
                autoScrollBottom.canAutoScrollToBottom = true
            }
        }
        findViewById<View>(R.id.moreBt).setOnClickListener {
            filterSetDialog.show()
        }

        menuLayout = findViewById<ViewGroup>(R.id.menuLayout)
        menuBt = findViewById<ViewGroup>(R.id.menuBt)
        menuBt.setOnClickListener {
            lastClickChangeMenuShowTime = System.currentTimeMillis()
            if (menuShowFlag) {
                hideMenu()
            } else {
                showMenu()
            }
        }
        lastClickChangeMenuShowTime = System.currentTimeMillis()

        logRecyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy.absoluteValue > 8 && System.currentTimeMillis() - lastClickChangeMenuShowTime > 3_000) {
                    if (dy > 0) {
                        hideMenu()
                    } else if (dy < 0) {
                        showMenu()
                    }
                }
            }
        })
        log("日志框，在UI上展示日志信息")
    }

    fun changeSizeTo(size: Int) {
        logRecyclerView.layoutParams.height = max(size, minHeight)
        logRecyclerView.requestLayout()
    }

    fun changeSizeBy(defSize: Int) {
        logRecyclerView.layoutParams.height += max(
            defSize,
            minHeight - logRecyclerView.layoutParams.height
        )
        logRecyclerView.requestLayout()
    }

    fun log(log: CharSequence?) {
        log(Log.VERBOSE, log)
    }

    fun i(log: CharSequence?) {
        log(Log.INFO, log)
    }

    fun d(log: CharSequence?) {
        log(Log.DEBUG, log)
    }

    fun w(log: CharSequence?) {
        log(Log.WARN, log)
    }

    fun e(log: CharSequence?, e: Throwable? = null) {
        if (e != null) {
            val message = e.message
            val stackTraceToString = e.stackTraceToString()
            log(Log.ERROR, "$log : $message\n $stackTraceToString")
        } else {
            log(Log.ERROR, log)
        }
    }

    private fun log(level: Int, log: CharSequence?) {
        val logBean = LogBean(level, log ?: "")
        logRecyclerView.post {
            logAdapter.addLog(logBean)
        }
    }

    private fun showMenu() {
        if (!menuShowFlag) {
            menuLayout.animate().translationY(0f).setDuration(animateDuration).start()
            menuBt.animate().rotation(180f).setDuration(animateDuration).start()
            menuShowFlag = true
        }
    }

    private fun hideMenu() {
        if (menuShowFlag) {
            menuLayout.animate().translationY(-DevLayoutUtil.dp2px(40f).toFloat())
                .setDuration(animateDuration).start()
            menuBt.animate().rotation(0f).setDuration(animateDuration).start()
            menuShowFlag = false
        }
    }

    class AutoScrollBottom(val recyclerView: RecyclerView, val adapter: RecyclerView.Adapter<*>) {
        var canAutoScrollToBottom = true

        init {
            recyclerView.addOnScrollListener(object : OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {//滑动结束，未在最底部
                        canAutoScrollToBottom = !recyclerView.canScrollVertically(10)
                    } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {//拖拽中
                        canAutoScrollToBottom = false
                    }
                }
            })

            adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    super.onItemRangeInserted(positionStart, itemCount)
                    if (canAutoScrollToBottom) {
                        recyclerView.smoothScrollToPosition(positionStart + itemCount - 1);
                    }
                }

                override fun onChanged() {
                    super.onChanged()
                    if (adapter.itemCount == 0) {
                        canAutoScrollToBottom = true
                    }
                }
            })
        }
    }


    data class LogBean(
        val level: Int,
        val log: CharSequence,
        val time: Date = Date()
    )

    /**
     * 日志适配器
     */
    class LogAdapter(context: Context, private val mFilter: LogFilter) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val mBgColors = IntArray(2)
        private val mTextColors = IntArray(5)
        private val mAllData = mutableListOf<LogBean>()
        private val mData = mutableListOf<LogBean>()
        private var mShowTimeFlag = false
        private val mTimeFormat: SimpleDateFormat = SimpleDateFormat("HH:mm:ss:SSS", Locale.CHINA)

        init {
            mBgColors[0] = ContextCompat.getColor(context, R.color.dev_layout_log_bg0)
            mBgColors[1] = ContextCompat.getColor(context, R.color.dev_layout_log_bg1)

            mTextColors[0] = ContextCompat.getColor(context, R.color.dev_layout_log_verbose)
            mTextColors[1] = ContextCompat.getColor(context, R.color.dev_layout_log_info)
            mTextColors[2] = ContextCompat.getColor(context, R.color.dev_layout_log_debug)
            mTextColors[3] = ContextCompat.getColor(context, R.color.dev_layout_log_warn)
            mTextColors[4] = ContextCompat.getColor(context, R.color.dev_layout_log_error)
        }

        @ColorInt
        fun LogBean.getTextColor(): Int {
            return when (level) {
                Log.INFO -> mTextColors[1]
                Log.DEBUG -> mTextColors[2]
                Log.WARN -> mTextColors[3]
                Log.ERROR -> mTextColors[4]
                else -> mTextColors[0]
            }
        }

        override fun getItemCount(): Int = mData.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val textView = TextView(parent.context)
            textView.textSize = 10f
            val padding = DevLayoutUtil.dp2px(2f)
            textView.setPadding(0, padding, 0, padding)
            return object : RecyclerView.ViewHolder(textView.apply {
                layoutParams =
                    ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            }) {}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val logBean = mData[position]
            holder.itemView.setBackgroundColor(mBgColors[position % 2])
            val textView = holder.itemView as TextView
            textView.setTextColor(logBean.getTextColor())
            if (mShowTimeFlag) {
                textView.text = "${mTimeFormat.format(logBean.time)} : ${logBean.log}"
            } else {
                textView.text = "${logBean.log}"
            }
        }

        fun addLog(logBean: LogBean) {
            mAllData.add(logBean)
            if (mFilter.filter(logBean)) {
                val start = mData.size
                mData.add(logBean)
                notifyItemRangeInserted(start, 1)
            }
        }

        fun clearData() {
            mAllData.clear()
            mData.clear()
            notifyDataSetChanged()
        }

        fun setShowTime(checked: Boolean) {
            mShowTimeFlag = checked
            notifyDataSetChanged()
        }

        fun notifyFilterChanged() {
            val filterData = mAllData.filter { mFilter.filter(it) }
            mData.clear()
            mData.addAll(filterData)
            notifyDataSetChanged()
        }
    }


    /**
     * 日志过滤器
     */
    class LogFilter {
        var level: Int = 0
        var keys: List<String>? = null
        var isContainsFlag = true//关键词过滤，是满足包含还是不包含关系
        var isAndFlag = true//多关键词过滤，满足的是"与"还是"或"

        fun filter(it: LogBean): Boolean {
            if (it.level >= level) {
                if (keys != null && keys!!.isNotEmpty()) {
                    return keyFilter(it) && isContainsFlag
                }
                return true
            } else {
                return false
            }
        }

        private fun keyFilter(it: LogBean): Boolean {
            if (isAndFlag) {
                keys!!.forEach { key ->
                    if (!it.log.contains(key, true)) {
                        return false
                    }
                }
                return true
            } else {
                keys!!.forEach { key ->
                    if (it.log.contains(key, true)) {
                        return true
                    }
                }
                return false
            }
        }

        fun clear() {
            keys = null
        }
    }


    /**
     * 过滤条件对话框
     */
    class FilterDialog(context: Context, private val logMonitor: LogMonitorLayout) :
        Dialog(context) {
        private val levelSeekBar: SeekBarLayout
        private val addEditor: EditorTextLayout
        private val containRadioGroup: RadioGroupLayout
        private val andRadioGroup: RadioGroupLayout

        init {
            val devLayout = DevLayout(context)
            val linearLayout = LinearLayout(context)
            val padding = DevLayoutUtil.dp2px(12f)
            linearLayout.setPadding(padding, padding, padding, 2 * padding)
            linearLayout.orientation = LinearLayout.VERTICAL

            linearLayout.addView(devLayout)
            setContentView(linearLayout)

            devLayout.setIsLineStyle(true)
            devLayout.addTitleAndDesc("日志设置", "")
            devLayout.addLine()
            levelSeekBar = devLayout.addSeekBar("Level")
            levelSeekBar.setOnProgressChangeListener { progress ->
                val level = progress + 2
                levelSeekBar.valueTv.text = when (level) {
                    Log.VERBOSE -> "VERBOSE"
                    Log.DEBUG -> "DEBUG"
                    Log.INFO -> "INFO"
                    Log.WARN -> "WARN"
                    Log.ERROR -> "ERROR"
                    else -> "ALL"
                }
                logMonitor.filter.level = level
                logMonitor.logAdapter.notifyFilterChanged()
            }.setMax(4)

            devLayout.addTitleAndDesc("", "多个关键词使用\",\"（英文）分割")
            addEditor = devLayout.addEditor("关键词") {
                if (it.isNotBlank()) {
                    logMonitor.filter.keys = it.toString().trim().split(",")
                } else {
                    logMonitor.filter.keys = null
                }
                logMonitor.logAdapter.notifyFilterChanged()
                dismiss()
            }
            containRadioGroup = devLayout.addRadioGroup("过滤方式")
                .addItem("包含关键词") {
                    logMonitor.filter.isContainsFlag = true
                    logMonitor.logAdapter.notifyFilterChanged()
                }.addItem("不包含关键词") {
                    logMonitor.filter.isContainsFlag = false
                    logMonitor.logAdapter.notifyFilterChanged()
                }.setChecked(0)

            andRadioGroup = devLayout.addRadioGroup("多关键词")
            andRadioGroup.addItem("全部满足") {
                logMonitor.filter.isAndFlag = true
                logMonitor.logAdapter.notifyFilterChanged()
            }.addItem("满足之一") {
                logMonitor.filter.isAndFlag = false
                logMonitor.logAdapter.notifyFilterChanged()
            }.setChecked(0)

            devLayout.addAction("清空筛选") {
                levelSeekBar.setProgress(0)
                andRadioGroup.setChecked(0)
                containRadioGroup.setChecked(0)
                addEditor.editorText.setText("")
                logMonitor.filter.clear()
                logMonitor.logAdapter.notifyFilterChanged()
            }

            val win = window
            if (win != null) {
                win.setBackgroundDrawableResource(android.R.color.background_light)
                win.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                val lp = win.attributes
                lp.width = WindowManager.LayoutParams.MATCH_PARENT
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT
                lp.gravity = Gravity.BOTTOM
                win.attributes = lp
            }
        }

    }
}