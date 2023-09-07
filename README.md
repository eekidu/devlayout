# DevLayout

使用代码的方式，快速添加常用调试控件，无需XML，简化调试页面开发过程

<img src="https://gitee.com/ksee/DevLayout/raw/dev/demo1.png" alt="Demo" style="zoom: 33%;" />

[![](https://jitpack.io/v/com.gitee.ksee/DevLayout.svg)](https://jitpack.io/#com.gitee.ksee/DevLayout)

## 引入依赖

在Project的build.gradle在添加以下代码

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

在Module的build.gradle在添加以下代码

最新版本：[![](https://jitpack.io/v/com.gitee.ksee/DevLayout.svg)](https://jitpack.io/#com.gitee.ksee/DevLayout)

```groovy
implementation 'com.gitee.ksee:DevLayout:版本号'

```

## 使用

DevLayout是一个ViewGroup，你可以把它摆放到页面上合适的位置，然后通过调用它的方法来添加需要子控件。

```Kotlin
var devLayout = DevLayout(context)

/**
 * 添加功能按钮
 */
devLayout.addAction("功能1") {
    //点击回调
}

/**
 * 添加开关
 */
devLayout.addSwitch("开关1") { buttonView, isChecked ->
    //状态切换回调
}

/**
 * 添加SeekBar
 */
mDevLayout.addSeekBar("参数设置1") { progress ->
   //进度回调
}.setMax(1000).setProgress(50)


/**
 * 添加输入框
 */
mDevLayout.addEditor("参数设置") { inputText ->
    textView.text = inputText
}


/**
 * 添加换行
 */
devLayout.br()
/**
 * 添加分割线
 */
devLayout.hr()

//其他类型控件见Demo MainActivity.kt

```

## 耗时监控

大部分需要调试的代码，会在控件的回调中触发，那么监控回调的执行情况，就可以了解调试代码的耗时问题了。
由于控件种类很多，回调类的类型也都不一样，如何对形形色色的回调统一进行监控？

动态代理：创建了ListenerDelegator代理类，对原有回调进行代理，优点：

- 减少了重复代码
- 解耦合（将通用的代码逻辑与具体的业务逻辑分离）
- AOP面向切面编程（在不修改原始对象的情况下，将额外的功能横切到应用程序的不同部分中）

## 日志

日志是我们调试代码的重要手段，有的时候我们需要将日志输出到UI上，方便我们在手机没有连接Logcat，无法通过控制台监测日志时，也能对程序执行的中间过程或执行结果有一定的展示。

我们可以添加一个日志框到UI界面上，以此来展示Log信息，方式如下：

```kotlin
//添加日志框，默认尺寸，添加后也可以通过UI调整
mDevLayout.addLogMonitor()
mDevLayout.addLogMonitorSmall()
mDevLayout.addLogMonitorLarge()

//输出日志
mDevLayout.log(msg)
mDevLayout.logI(msg)
mDevLayout.logD(msg)
mDevLayout.logW(msg)
mDevLayout.logE(msg)
```

支持过滤：

- 按等级过滤
- 按关键词过滤：key1,key2

