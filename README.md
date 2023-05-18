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
implementation 'com.gitee.ksee:DevLayout:1.0.6'

```

## 使用

```Kotlin

var devLayout = DevLayout(this)

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

```