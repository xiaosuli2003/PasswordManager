package com.xiaosuli.passwordmanager.utils

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.PersistableBundle
import com.xiaosuli.passwordmanager.App
import com.xiaosuli.passwordmanager.ui.components.showToast

private fun getClipboardManager(): ClipboardManager {
    return App.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
}

fun copyTextToClipboard(text: String) {
    val manager = getClipboardManager()
    // 创建能够存入剪贴板的ClipData对象
    // ‘Label’这是任意文字标签
    val clipData = ClipData.newPlainText("Label", text)
    clipData.description.extras = PersistableBundle().apply {
        // 添加此标志可阻止敏感内容出现在 Android 13 及更高版本中的复制视觉确认中显示的任何内容预览中。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            putBoolean(ClipDescription.EXTRA_IS_SENSITIVE, true)
        } else {
            putBoolean("android.content.extra.IS_SENSITIVE", true)
        }
    }
    // 将ClipData数据复制到剪贴板：
    manager.setPrimaryClip(clipData)
    // 在 Android 12 及更低版本显示消息
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
        showToast("复制成功！")
    }
}