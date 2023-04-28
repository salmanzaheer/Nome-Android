package com.example.nome.impl

import com.example.nome.model.Preset

interface IPresetsRepository {
    suspend fun getPresets(): List<Preset>
    suspend fun deletePreset(preset: Preset)
    suspend fun addPreset(preset: Preset)

}