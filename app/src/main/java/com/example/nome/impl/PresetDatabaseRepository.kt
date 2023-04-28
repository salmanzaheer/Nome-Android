package com.example.nome.impl

import android.app.Application
import androidx.room.Room
import com.example.nome.model.Preset

class PresetDatabaseRepository(app: Application) : IPresetsRepository{
    private val db: PresetDatabase

    init {
        db = Room.databaseBuilder(
            app,
            PresetDatabase::class.java,
            "presets.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    override suspend fun getPresets(): List<Preset> {
        return db.presetsDao().getPresets()
    }

    override suspend fun deletePreset(preset: Preset) {
        db.presetsDao().deletePreset(preset)
    }

    override suspend fun addPreset(preset: Preset) {
        db.presetsDao().addPreset(preset)
    }
}