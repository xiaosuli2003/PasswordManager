package com.xiaosuli.passwordmanager.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.time.Duration.Companion.milliseconds

@SuppressLint("SimpleDateFormat")
fun timestampToDateTime(timestamp: Long,pattern:String="yyyy年MM月dd日 HH:mm"): String {
    val dateFormat = SimpleDateFormat(pattern)
    val date = Date(timestamp)
    return dateFormat.format(date)
}

fun timestampToDuration(timestamp: Long): String {
    val currentTimeMillis = System.currentTimeMillis()
    val format = (currentTimeMillis - timestamp).milliseconds
        .toComponents { days, hours, minutes, _, _ ->
            when {
                // days > 365  -> "${days / 365}年前"
                // days > 30   -> "${days / 30}个月前"
                // days > 7    -> "${days / 7}周前"
                // days > 0    -> "${days}天前"
                days > 0    -> timestampToDateTime(timestamp)
                hours > 0   -> "${hours}小时前"
                minutes > 0 -> "${minutes}分钟前"
                else        -> "刚刚"
            }
        }
    return format
}