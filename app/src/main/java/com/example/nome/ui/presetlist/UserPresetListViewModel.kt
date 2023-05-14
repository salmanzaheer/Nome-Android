package com.example.nome.ui.presetlist

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nome.impl.*
import com.example.nome.model.Preset
import com.example.nome.model.UserPreset
import com.example.nome.ui.network.PresetsFetcher
import kotlinx.coroutines.launch

class UserPresetListViewModel(app: Application) : AndroidViewModel(app){
    private val _userPresets: MutableState<List<UserPreset>> = mutableStateOf(listOf())
    val userPresets: State<List<UserPreset>> = _userPresets

    private val _waiting: MutableState<Boolean> = mutableStateOf(false)
    val waiting: State<Boolean> = _waiting

    private val _selected: MutableState<UserPreset?>
    val selectedPreset: State<UserPreset?>

    private lateinit var _repository: IUserPresetsRepository
    private val _presetFetcher = PresetsFetcher(getApplication())

    init {
        _repository = UserPresetsMemoryRepository(listOf())
        viewModelScope.launch {
            _waiting.value = true
            val presets = _presetFetcher.fetchPresets()
            _userPresets.value = _repository.getPresets()
            _waiting.value = false
        }
        _selected = mutableStateOf(null)
        selectedPreset = _selected
    }

    fun addPreset(userPreset: UserPreset){
        viewModelScope.launch {
            _repository.addPreset(userPreset)
        }
    }

    suspend fun deletePreset(userPreset: UserPreset){
        viewModelScope.launch {
            _repository.deletePreset(userPreset)
            _userPresets.value = _repository.getPresets()
        }
    }

}