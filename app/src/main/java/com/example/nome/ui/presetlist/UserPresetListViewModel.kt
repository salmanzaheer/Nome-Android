package com.example.nome.ui.presetlist

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nome.impl.IPresetsRepository
import com.example.nome.impl.PresetDatabaseRepository
import com.example.nome.impl.PresetsMemoryRepository
import com.example.nome.model.Preset
import com.example.nome.ui.network.PresetsFetcher
import kotlinx.coroutines.launch

class UserPresetListViewModel(app: Application) : AndroidViewModel(app){
    private val _presets: MutableState<List<Preset>> = mutableStateOf(listOf())
    val presets: State<List<Preset>> = _presets

    private val _waiting: MutableState<Boolean> = mutableStateOf(false)
    val waiting: State<Boolean> = _waiting

    private val _selected: MutableState<Preset?>
    val selectedPreset: State<Preset?>

    private lateinit var _repository: IPresetsRepository
    private val _presetFetcher = PresetsFetcher(getApplication())

    init {
        viewModelScope.launch {
            _repository = PresetsMemoryRepository(listOf())
            _waiting.value = true
            _repository = PresetDatabaseRepository(getApplication())
            try {
                val presets = _presetFetcher.fetchPresets()
                _repository = PresetsMemoryRepository(presets)
                presets.forEach { preset -> _repository.addPreset(preset) }
            } catch (e: Exception) {
                Log.e(this@UserPresetListViewModel.javaClass.simpleName, e.message, e)
            }
        }
        _selected = mutableStateOf(null)
        selectedPreset = _selected
    }

    suspend fun addPreset(preset: Preset){
        viewModelScope.launch {
            _repository.addPreset(preset)
        }
        _presets.value = _repository.getPresets()
    }

    suspend fun deletePreset(preset: Preset){
        viewModelScope.launch {
            _repository.deletePreset(preset)
            _presets.value = _repository.getPresets()
        }
    }

}