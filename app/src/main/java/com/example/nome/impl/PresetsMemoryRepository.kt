package com.example.nome.impl

import com.example.nome.model.Preset
import kotlinx.coroutines.delay

class PresetsMemoryRepository(private var _presets: List<Preset>): IPresetsRepository {
    init{
    }

    override suspend fun getPresets(): List<Preset> {
        return _presets
    }

    override suspend fun deletePreset(preset: Preset) {
        delay(5000)
        _presets = _presets.filter { s -> s.id != preset.id}
    }

    override suspend fun addPreset(preset: Preset) {
        _presets = listOf(preset) + _presets
    }


}
