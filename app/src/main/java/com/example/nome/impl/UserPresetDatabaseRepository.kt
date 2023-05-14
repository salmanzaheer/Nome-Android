package com.example.nome.impl

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nome.model.UserPreset

class UserPresetDatabaseRepository(val app: Application) : IUserPresetsRepository {

    private val db: UserPresetDatabase

    init {
        db = Room.databaseBuilder(
            context = app,
            klass = UserPresetDatabase::class.java,
            name = "userPresets.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    override suspend fun getPresets(): List<UserPreset> {
        return db.userPresetDao().getPresets()
    }

    override suspend fun deletePreset(userPreset: UserPreset) {
        db.userPresetDao().deletePreset(userPreset)
    }

    override suspend fun addPreset(userPreset: UserPreset) {
        db.userPresetDao().addPreset(userPreset)
    }


}