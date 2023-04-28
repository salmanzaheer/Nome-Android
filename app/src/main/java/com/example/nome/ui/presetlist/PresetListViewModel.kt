package com.example.nome.ui.presetlist

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nome.impl.IPresetsRepository
import com.example.nome.impl.PresetsMemoryRepository
import com.example.nome.model.Preset
import com.example.nome.ui.network.PresetsFetcher
import kotlinx.coroutines.launch

class PresetListViewModel(app:Application) : AndroidViewModel(app){
    private val _presets: MutableState<List<Preset>> = mutableStateOf(listOf())
    val presets: State<List<Preset>> = _presets

    private val _selected: MutableState<Preset?>
    val selectedPreset: State<Preset?>

    private val _presetFetcher = PresetsFetcher(getApplication())
    private var _repository: IPresetsRepository

    private val _waiting: MutableState<Boolean> = mutableStateOf(false)
    val waiting: State<Boolean> = _waiting

    init{
        _repository = PresetsMemoryRepository(listOf())
        viewModelScope.launch {
            val presetList = _presetFetcher.fetchPresets()
            _repository = PresetsMemoryRepository(presetList)
            _presets.value = _repository.getPresets()
        }
        _selected = mutableStateOf(null)
        selectedPreset = _selected
    }
}