package com.xiaosuli.passwordmanager.utils

import com.xiaosuli.passwordmanager.entity.CategoryInfo
import com.xiaosuli.passwordmanager.entity.PasswordEntity
import kotlinx.coroutines.flow.Flow

typealias FlowPassList = Flow<List<PasswordEntity>>

typealias PassList = List<PasswordEntity>

typealias FlowCategoryList = Flow<List<CategoryInfo>>

typealias CategoryList = List<CategoryInfo>