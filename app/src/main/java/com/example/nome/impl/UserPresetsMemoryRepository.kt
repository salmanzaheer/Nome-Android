package com.example.nome.impl

import com.example.nome.model.UserPreset
import kotlinx.coroutines.delay

class UserPresetsMemoryRepository(private var _userPresets: List<UserPreset>) : IUserPresetsRepository {
    init {

    }

    override suspend fun getPresets(): List<UserPreset> {
        return _userPresets
    }

    override suspend fun deletePreset(userPreset: UserPreset) {
        delay(5000)
        _userPresets = _userPresets.filter { s -> s.id != userPreset.id }
    }

    override suspend fun addPreset(userPreset: UserPreset) {
        _userPresets = listOf(userPreset) + _userPresets
    }

}