package com.github.eekidu.dev.devlayout

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import github.eekidu.dev.devlayout.demo.R
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var mDevLayout: DevLayout

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDevLayout = DevLayout(this)
        setContentView(mDevLayout)


        /**
         * 添加标题和描述
         */
        mDevLayout.addTitleAndDesc(
            "DevLayout",
            "使用代码的方式，快速添加常用调试控件，无需XML，简化调试页面开发过程"
        )
        mDevLayout.hr()//添加分割线

        /**
         * 单选，切换布局样式
         */
        mDevLayout.addRadioGroup("布局方式")
            .addItem("流式布局") {
                mDevLayout.setIsLineStyle(false)
            }.addItem("线性布局") {
                mDevLayout.setIsLineStyle(true)
            }.setChecked(0)


        /**
         * 添加日志框
         */
        mDevLayout.addLogMonitor()
//        mDevLayout.addLogMonitorSmall()
//        mDevLayout.addLogMonitorLarge()
        mDevLayout.logMonitorLayout?.setShowTime(true)

        mDevLayout.hr()

        /**
         * 添加功能按钮
         */
        mDevLayout.addAction("功能1") {
            1 + 1
        }.addAction("功能2") {
            toast("功能2执行")
        }

        /**
         * 添加换行
         */
        mDevLayout.br()

        mDevLayout.addAction("子线程日志") {
            startPrintLog()
        }.addAction("功能4:耗时方法") {
            Thread.sleep(100)
        }
        val addButton = mDevLayout.addButton("功能5:异常") {
            1 / 0
        }
        addButton.setTextColor(Color.RED)

        mDevLayout.addDescribeAndButton("为后面按钮的功能添加一些说明……", "按钮") {
            toast("按钮")
        }

        /**
         * 添加分割线
         */
        mDevLayout.hr()

        /**
         * 添加开关
         */
        mDevLayout.addSwitch("开关1") { buttonView, isChecked ->
            toast("开关1状态：$isChecked")
        }
        mDevLayout.addSwitch("开关2") { _, isChecked ->
            toast("开关2状态：$isChecked")
        }.isChecked = true

        /**
         * 添加分割线
         */
        mDevLayout.addLine()

        /**
         * 添加SeekBar
         */
        mDevLayout.addSeekBar("参数设置1") { progress ->
            toast("参数设置1：$progress")
        }.setMax(1000).setProgress(50)

        mDevLayout.addSeekBar("参数设置2").apply {
            setEnableStep(true)//设置是否开启步进模式
            setOnProgressChangeListener { progress ->
                this.valueTv.text = "${progress}dp"//自定义显示
            }
            setMax(100).setProgress(50)
        }


        mDevLayout.addLine()

        /**
         * 添加文本框
         */
        val textView = mDevLayout.addTextView("文本展示")

        /**
         * 添加输入框
         */
        mDevLayout.addEditor("参数设置") { inputText ->
            textView.text = inputText
        }



        mDevLayout.addLine()

        /**
         * 添加多选框
         */
        mDevLayout.addCheckBox("菜单1") { _, isChecked -> toast("菜单1状态：$isChecked") }
            .apply {
                isChecked = true
            }
        mDevLayout.addCheckBox("菜单2") { _, isChecked -> toast("菜单2状态：$isChecked") }
        mDevLayout.addCheckBox("菜单3") { _, isChecked -> toast("菜单3状态：$isChecked") }


        mDevLayout.addLine()
        mDevLayout.p()//添加空白行，添加间距


        /**
         * 添加单选框
         */
        mDevLayout.addRadioGroup()
            .addItem("选项1")
            .addItem("选项2") { toast("选项2选中") }//监听方式1：直接设置该项选中监听
            .addItem("选项3") { toast("选项3选中") }
            .addItem("选项4") { toast("选项4执行") }
            .setChecked(2)

        mDevLayout.addRadioGroup("带标题的单项选择")
            .addItem("选项4")
            .addItem("选项5")
            .addItem("选项6")
            .addItem("选项7")
            .setListener { index, checkedId ->//监听方式2：设置整体监听
                toast("第${index}项选中")
            }

        mDevLayout.p()

        /**
         * 添加一个布局
         */
        val frameLayout = mDevLayout.addViewByClass(FrameLayout::class.java)
        frameLayout.minimumHeight = 200
        frameLayout.setBackgroundColor(Color.LTGRAY)
        val imageView = ImageView(this)
        imageView.apply {
            setImageResource(R.drawable.ic_launcher_foreground)
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply { gravity = Gravity.CENTER }
        }
        frameLayout.addView(imageView)
    }


    private lateinit var mToast: Toast
    private fun toast(info: CharSequence) {
        if (!::mToast.isInitialized) {
            mToast = Toast.makeText(this@MainActivity, info, Toast.LENGTH_SHORT)
        }
        mToast.setText(info)
        mToast.show()
    }


    private fun startPrintLog() {
        repeat(3) {//模拟子线程输出log
            Thread {
                repeat(10) {
                    val msg = "这是一条日志信息 from ${Thread.currentThread()}"
                    when (Random().nextInt(5)) {// 日志等级
                        0 -> mDevLayout.log(msg)
                        1 -> mDevLayout.logI(msg)
                        2 -> mDevLayout.logD(msg)
                        3 -> mDevLayout.logW(msg)
                        4 -> mDevLayout.logE(msg)
                    }
                    Thread.sleep(200L + Random().nextInt(300))

                }
            }.start()
        }
    }


}