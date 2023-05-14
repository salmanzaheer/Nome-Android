package com.example.nome.impl

import com.example.nome.model.Preset
import com.example.nome.model.UserPreset

interface IUserPresetsRepository {
    suspend fun getPresets(): List<UserPreset>

    suspend fun deletePreset(userPreset: UserPreset)

    suspend fun addPreset(userPreset: UserPreset)

}