package com.xiaosuli.passwordmanager.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_password")
data class PasswordEntity(
    // 该密码信息的唯一id（同时还是创建时间和修改时间）
    @PrimaryKey val id: Long,
    // 分类（类别）
    val category: String,
    // 表示该密码信息所属的APP、网站或银行
    val name: String,
    // 账号，表示该密码信息对应的手机号、邮箱或银行卡号
    val number: String,
    // 密码
    val password: String,
    // 修改时间（以时间戳形式存储）
    val createTime:Long,
    // 修改时间（以时间戳形式存储）
    val updateTime:Long,
)