package com.example.nome.ui.newpreset

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.nome.model.Preset


class NewPresetViewModel: ViewModel() {
    private val _name: MutableState<String> = mutableStateOf("")
    val name: State<String> = _name
    private val _bpm: MutableState<String> = mutableStateOf("")
    val bpm: State<String> = _bpm

    fun setName(name: String){
        _name.value = name
    }

    fun setBpm(bpm: String){
        _bpm.value = bpm
    }

    fun validate(): Preset {
        if (name.value.isEmpty()){
            throw Exception("Preset name required")
        }
        if(bpm.value.isEmpty()){
            throw Exception("Preset BPM required")
        }
        val presetBPM = bpm.value.toIntOrNull() ?: throw Exception("BPM must be Int")
        return Preset(
            id = 1,
            name = name.value,
            BPM = presetBPM,
            url = ""
        )
    }
}