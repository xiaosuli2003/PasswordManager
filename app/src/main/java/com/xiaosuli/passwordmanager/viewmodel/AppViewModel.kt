package com.xiaosuli.passwordmanager.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Computer
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.Games
import androidx.compose.material.icons.rounded.HomeWork
import androidx.compose.material.icons.rounded.LibraryMusic
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.VideoLibrary
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.xiaosuli.passwordmanager.entity.PasswordEntity
import com.xiaosuli.passwordmanager.model.AppDatabase
import com.xiaosuli.passwordmanager.model.DataStoreRepository
import com.xiaosuli.passwordmanager.utils.FlowPassList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppViewModel(
    private val dataStoreRepository: DataStoreRepository,
    appDatabase: AppDatabase,
) : ViewModel() {

    /* passwordDao相关操作 */
    private val passwordDao = appDatabase.passwordDao()

    val allPassword = passwordDao.getAll()

    fun searchPasswords(keyword: String) =
        passwordDao.getPassByKeyword(keyword)

    val categoryInfo = passwordDao.getCategoryInfo()

    fun insertPassword(passwordEntity: PasswordEntity) {
        viewModelScope.launch {
            passwordDao.insert(passwordEntity)
        }
    }

    fun deletePassword(id: Long) {
        viewModelScope.launch {
            passwordDao.deleteById(id)
        }
    }

    /* datastore相关操作 */
    fun saveString(str: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveString(str)
        }
    }

    fun searchPassByCategory(category: String, keyword: String) =
        passwordDao.getPassByCategoryAndKeyword(category, keyword)

    val str = dataStoreRepository.str

    /* 主界面分类图标相关操作 */
    val categoryIcon = mapOf(
        "游戏" to Icons.Rounded.Games,
        "工作" to Icons.Rounded.Computer,
        "银行卡" to Icons.Rounded.CreditCard,
        "生活" to Icons.Rounded.HomeWork,
        "教育学习" to Icons.Rounded.Games,
        "音乐" to Icons.Rounded.LibraryMusic,
        "影视" to Icons.Rounded.VideoLibrary,
    )
}

class AppViewModelFactory(
    private val dataStoreRepository: DataStoreRepository,
    private val appDatabase: AppDatabase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AppViewModel(dataStoreRepository, appDatabase) as T
    }
}