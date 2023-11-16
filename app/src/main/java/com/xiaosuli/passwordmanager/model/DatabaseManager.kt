package com.xiaosuli.passwordmanager.model

import android.util.Log
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.xiaosuli.passwordmanager.App
import com.xiaosuli.passwordmanager.entity.CategoryInfo
import com.xiaosuli.passwordmanager.entity.PasswordEntity
import com.xiaosuli.passwordmanager.utils.FlowCategoryList
import com.xiaosuli.passwordmanager.utils.FlowPassList
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {
    @Query("SELECT * FROM tb_password")
    fun getAll(): FlowPassList

    @Query(
        """SELECT * FROM tb_password 
        where category like '%'|| :keyword ||'%' 
        or name like '%'|| :keyword ||'%' 
        or number like '%'|| :keyword ||'%'
        """
    )
    fun getPassByKeyword(keyword: String): FlowPassList

    @Query(
        """SELECT * FROM tb_password 
        where category = :category 
        and ( name like '%'|| :keyword ||'%' 
        or number like '%'|| :keyword ||'%' )
        """
    )
    fun getPassByCategoryAndKeyword(category:String,keyword: String):FlowPassList

    @Query("select category,count(id) as count from tb_password GROUP BY category")
    fun getCategoryInfo(): FlowCategoryList

    @Insert
    suspend fun insertAll(vararg passwordEntity: PasswordEntity)

    @Insert
    suspend fun insert(passwordEntity: PasswordEntity)

    @Delete
    suspend fun delete(passwordEntity: PasswordEntity)

    @Query("delete from tb_password where id = :id")
    suspend fun deleteById(id:Long)
}

@Database(entities = [PasswordEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun passwordDao(): PasswordDao

    companion object {
        private const val name = "db_app"
        private var db: AppDatabase? = null
        fun getDB() = db ?: Room.databaseBuilder(
            context = App.context,
            klass = AppDatabase::class.java,
            name = name
        ).build().also {
            db = it
        }
    }
}