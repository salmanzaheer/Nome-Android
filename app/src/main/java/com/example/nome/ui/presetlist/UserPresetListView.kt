package com.example.nome.ui.presetlist

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nome.model.Preset
import com.example.nome.ui.AddPresetFAB
import com.example.nome.ui.LandscapeView
import com.example.nome.ui.PresetRow
import com.example.nome.ui.confirmDialog.ConfirmViewModel
import com.example.nome.ui.newpreset.NewPresetView
import com.example.nome.ui.newpreset.NewPresetViewModel
import kotlin.reflect.KProperty0

@ExperimentalFoundationApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserPresetListView(
    presets: List<Preset>,
    selectedPreset: Preset?,
    confirmViewModel: ConfirmViewModel,
    // TODO - add confirm dialog,
    onDelete: suspend (Preset) -> Unit,
    onSelectPreset: KProperty0<androidx.compose.runtime.State<Preset?>>
) {

    val showAddPresetsScreen =  remember { mutableStateOf(false)}
    val newPresetVm: NewPresetViewModel = viewModel()



    Box(contentAlignment = Alignment.Center){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val config = LocalConfiguration.current
            if(config.orientation == Configuration.ORIENTATION_PORTRAIT){
                LazyColumn{
                    itemsIndexed(presets){idx, preset ->
                        PresetRow(idx = idx, preset = preset, { idx -> confirmViewModel.showConfirmDelete(onConfirm = {onDelete(preset)})}, onSelect = onSelectPreset)
                    }
                }

            } else {
                LandscapeView(selectedPreset?.name) {
                    LazyColumn{
                        itemsIndexed(presets){idx, preset ->
                            PresetRow(idx = idx, preset = preset, { idx -> confirmViewModel.showConfirmDelete(onConfirm = {onDelete(preset)})}, onSelect = onSelectPreset)
                        }
                    }
                }
            }
        }
        // TODO - navigate to NewPresetView here!!
        ExtendedFloatingActionButton(
            text = { Text(text = "Add Preset") },
            onClick = {

            },
            icon = { Icon(Icons.Filled.Add, contentDescription = "add preset") }
        )
    }
}