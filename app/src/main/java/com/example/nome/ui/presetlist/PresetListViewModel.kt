package com.example.nome.ui.presetlist

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.nome.model.Preset

class PresetListViewModel(app: Application) : AndroidViewModel(app) {
    private val _presets: MutableState<List<Preset>> = mutableStateOf(listOf())
    val presets: State<List<Preset>> = _presets

    private val _selected: MutableState<Preset?>
    val selectedPreset: State<Preset?>

    private val _waiting: MutableState<Boolean> = mutableStateOf(false)
    val waiting: State<Boolean> = _waiting

    init {
        _waiting.value = true

        _selected = mutableStateOf(null)
        selectedPreset = _selected
    }

}