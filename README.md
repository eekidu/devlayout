# DevLayout

快速添加测试控件的布局

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

## 使用，见Demo

```Kotlin

var devLayout = DevLayout(this)

/**
 * 添加功能按钮
 */
devLayout.addAction("功能1") {
    toast("功能1")
}

/**
 * 添加开关
 */
devLayout.addSwitch("开关1") { buttonView, isChecked ->
    toast("开关1状态：$isChecked")
}

/**
 * 添加换行
 */
devLayout.br()

```