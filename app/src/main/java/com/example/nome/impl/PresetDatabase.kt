package com.example.nome.impl

import androidx.room.*
import com.example.nome.model.Preset

@Dao
interface PresetsDao{
    @Query("select id, name, BPM from presets")
    suspend fun getPresets(): List<Preset>

    @Insert
    suspend fun addPreset(preset: Preset)

    @Delete
    suspend fun deletePreset(preset: Preset)

    @Update
    suspend fun updatePreset(preset: Preset)
}

@Database(entities = [Preset::class], version = 1, exportSchema = false)
abstract class PresetDatabase: RoomDatabase() {
    abstract fun presetsDao(): PresetsDao
}