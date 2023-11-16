package com.xiaosuli.passwordmanager.entity

sealed class ScreenEntity(
    val route: String, // 路由
){
    object Main:ScreenEntity("main")
    object Add:ScreenEntity("add_password")
    object Search:ScreenEntity("search_password")
    object CategoryItems:ScreenEntity("category_items")
}
