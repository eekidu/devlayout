package github.eekidu.dev.devlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import github.eekidu.dev.devlayout.child.SeekBarLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val devLayout = DevLayout(this)
        setContentView(devLayout)


        devLayout.addAction("功能1") {
            Toast.makeText(this@MainActivity, "功能1", Toast.LENGTH_SHORT).show()
        }.addAction("功能2") {
            Toast.makeText(this@MainActivity, "功能2", Toast.LENGTH_SHORT).show()
        }.addAction("功能3") {
            Toast.makeText(this@MainActivity, "功能3", Toast.LENGTH_SHORT).show()
        }.addAction("功能4") {
            Toast.makeText(this@MainActivity, "功能4", Toast.LENGTH_SHORT).show()
        }.addAction("功能5") {
            Toast.makeText(this@MainActivity, "功能5", Toast.LENGTH_SHORT).show()
        }


        devLayout.addSwitch("开关1") { buttonView, isChecked ->
            Toast.makeText(
                this@MainActivity,
                "开关1状态：$isChecked",
                Toast.LENGTH_SHORT
            ).show()
        }.addSwitch("开关2") { buttonView, isChecked ->
            Toast.makeText(
                this@MainActivity,
                "开关1状态：$isChecked",
                Toast.LENGTH_SHORT
            ).show()
        }



        devLayout.addSeekBar("参数设置", object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        val seekBar2: SeekBarLayout = devLayout.addSeekBar("参数设置2")
        seekBar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBar2.valueTv.text = "自定义值：$progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
        seekBar2.seekBar.max = 1000


        val addEditorText = devLayout.addEditor("输入项目")
        val editorText = addEditorText.editorText

        val addTextView1 = devLayout.addTextView("文本展示")
        devLayout.addAction("确定") {
            addTextView1.text = editorText.text
        }

    }
}