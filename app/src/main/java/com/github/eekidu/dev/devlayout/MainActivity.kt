package com.github.eekidu.dev.devlayout

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.github.eekidu.dev.devlayout.child.RadioGroupLayout
import com.github.eekidu.dev.devlayout.child.SeekBarLayout

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val devLayout = DevLayout(this)

        val nestedScrollView = NestedScrollView(this)
        nestedScrollView.addView(devLayout)
        setContentView(nestedScrollView)



        devLayout.setIsLineStyle(false)//ture：线性布局，false：流式布局

        /**
         * 添加标题和描述
         */
        devLayout.addTitleAndDesc("DevLayout", "动态添加常用调试控件，无需XML，简化调试页面开发过程")
        devLayout.addLine()//添加分割线

        /**
         * 添加功能按钮
         */
        devLayout.addAction("功能1") {
            toast("功能1")
        }

        devLayout.addAction("功能2") {
            toast("功能2")
        }
        /**
         * 添加换行
         */
        devLayout.br()

        devLayout.addAction("功能3") {
            toast("功能3")
        }.addAction("功能4") {
            toast("功能4")
        }
        val getButton = devLayout.addButton("功能5") {
            toast("功能5")
        }


        devLayout.addTextViewAndButton("为后面按钮的功能添加一些说明……", "按钮") {
            toast("按钮")
        }

        /**
         * 添加分割线
         */
        devLayout.hr()

        /**
         * 添加开关
         */
        devLayout.addSwitch("开关1") { buttonView, isChecked ->
            toast("开关1状态：$isChecked")
        }
        devLayout.addSwitch("开关2") { _, isChecked ->
            toast("开关2状态：$isChecked")
        }.apply { isChecked = true }

        /**
         * 添加分割线
         */
        devLayout.addLine()

        /**
         * 添加进度条
         */
        devLayout.addSeekBar("参数设置1") { }
        val seekBar2: SeekBarLayout = devLayout.addSeekBar("参数设置2").setMax(100).setProgress(50)
        seekBar2.setOnProgressChangeListener {
            seekBar2.valueTv.text = "${it}dp"//自定义显示
        }.setEnableStep(true)//设置是否开启步进模式


        devLayout.addLine()

        /**
         * 添加文本框
         */
        val textView = devLayout.addTextView("文本展示")
        devLayout.addAction("Toast展示") {
            toast(textView.text)

        }

        /**
         * 添加输入框
         */
        devLayout.addEditor("请输入") { editable ->
            textView.text = editable.toString()
        }


        devLayout.addLine()

        /**
         * 添加多选框
         */

        devLayout.addCheckBox("菜单1") { _, isChecked -> toast("菜单1状态：$isChecked") }
            .apply {
                isChecked = true
            }
        devLayout.br()
        devLayout.addCheckBox("菜单2") { _, isChecked -> toast("菜单2状态：$isChecked") }
        devLayout.addCheckBox("菜单3") { _, isChecked -> toast("菜单3状态：$isChecked") }


        devLayout.addLine()
        devLayout.p()//添加空白行，添加间距


        /**
         * 添加单选框
         */
        val radioGroup = devLayout.addRadioGroup()
        radioGroup.addItem("选项1")
        radioGroup.addItem("选项2") { toast("选项2选中") }//直接设置该项选中监听
        radioGroup.addItem("选项3") { toast("选项3选中") }
        radioGroup.addItem(RadioGroupLayout.RadioItem("选项4") { toast("选项4执行") })
        radioGroup.setChecked(2)

        val radioGroup2 = devLayout.addRadioGroup("带标题的单项选择")
        radioGroup2.addItem("选项4")
        radioGroup2.addItem("选项5")
        radioGroup2.addItem("选项6")
        radioGroup2.addItem("选项7")

        radioGroup2.setListener { index, checkedId ->//设置整体选中监听
            toast("第${index}项选中")
        }


        devLayout.addLine()//分割线
        val addKeyValueText = devLayout.addKeyValueTextView()//键值展示TextView
        addKeyValueText.maxHeight = 150
        addKeyValueText.clearAndAddKV("说明", "键值展示")
        addKeyValueText.addKV("参数1", "value1")
        addKeyValueText.addKVLn("换行", "添加参数并换行")
        addKeyValueText.addKV("耗时", 123)

    }

    private fun toast(info: CharSequence) {
        Toast.makeText(this@MainActivity, info, Toast.LENGTH_SHORT).show()
    }


}