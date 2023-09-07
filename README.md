# DevLayout

使用代码的方式，快速添加常用调试控件，无需XML，简化调试页面开发过程

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

DevLayout是一个ViewGroup子类，你可以把它摆放到页面上合适的位置，通过调用相应的方法来添加需要的子控件。

```Kotlin

var devLayout = DevLayout(this)

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

大部分需要调试的代码，会在控件的回调中触发，那么监控回调的执行，就可以了解调试代码执行耗时情况。
由于控件种类很多，回调类也多种多样，如何对形形色色的回调统一进行监控？

创建了ListenerDelegator代理类，动态代理各种控件的回调，优点：
- 减少了重复代码
- 解耦合（将通用的代码逻辑与具体的业务逻辑分离）
- AOP面向切面编程（在不修改原始对象的情况下，将额外的功能横切到应用程序的不同部分中）

## 日志

日志是调试代码重要的手段，有的时候我们需要将日志输出到UI上，例如,在手机没有连接Logcat无法通过控制台监测日志时，或者某些操作后需要界面上也有些反馈时，
我们可以添加一个日志框到UI界面上，以此来展示Log信息，方式如下：

```kotlin
//添加日志框
mDevLayout.addLogMonitor()
mDevLayout.addLogMonitorSmall()//小尺寸
mDevLayout.addLogMonitorLarge()//大尺寸

//输出日志
mDevLayout.log(msg)
mDevLayout.logI(msg)
mDevLayout.logD(msg)
mDevLayout.logW(msg)
mDevLayout.logE(msg)
```

支持过滤：

- 按等级
- 按过滤词：key1,key2

## Demo
<img src="https://gitee.com/ksee/DevLayout/raw/dev/demo1.png" alt="Demo" style="zoom: 33%;" />