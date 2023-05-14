package com.example.nome.impl

import androidx.room.*
import com.example.nome.model.UserPreset

@Dao
interface UserPresetDao {
    @Query("select * from userPresets")
    suspend fun getPresets(): List<UserPreset>

    @Insert
    suspend fun addPreset(userPreset: UserPreset)

    @Delete
    suspend fun deletePreset(userPreset: UserPreset)

}

@Database(entities = [UserPreset::class], version = 1, exportSchema = false)
abstract class UserPresetDatabase : RoomDatabase() {
    abstract fun userPresetDao(): UserPresetDao
}