package com.xiaosuli.passwordmanager.entity

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavEntity(
    val route: String, // 路由
    val title: String, // 底部导航栏的标题
    val icon: ImageVector// 底部导航栏图标
) {
    object Home : BottomNavEntity(
        route = "home",
        title = "凭证",
        icon = Icons.Rounded.Lock
    )

    object Settings : BottomNavEntity(
        route = "settings",
        title = "设置",
        icon = Icons.Rounded.Settings
    )

    object ColorScheme : BottomNavEntity(
        route = "colorScheme",
        title = "色板",
        icon = Icons.Rounded.DateRange
    )
}