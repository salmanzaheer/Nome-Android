package com.example.nome.ui.presetlist

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.nome.model.Preset
import com.example.nome.ui.LandscapeView
import com.example.nome.ui.PresetRow
import com.example.nome.ui.theme.globalStateDataClass
import kotlin.reflect.KProperty0

@ExperimentalFoundationApi
@Composable
fun PresetListView(
    presets: List<Preset>,
    selectedPreset: Preset?,
    waiting: Boolean,
    onDelete: suspend (Preset) -> Unit,
    onSelectPreset: KProperty0<State<Preset?>>,
    globalState: globalStateDataClass
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(bottom = 60.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val config = LocalConfiguration.current
            if (config.orientation == Configuration.ORIENTATION_PORTRAIT){
                if (waiting){
                    CircularProgressIndicator()
                } else {
                    LazyColumn{
                        itemsIndexed(presets) { idx, preset ->
                            PresetRow(idx = idx, preset = preset, onDelete = { }, onSelect = onSelectPreset, globalState)

                        }
                    }
                }
            }else{
                LandscapeView(selectedPreset?.name){
                    LazyColumn{
                        itemsIndexed(presets){idx, preset ->
                            PresetRow(idx = idx, preset = preset, onDelete = {}, onSelect = onSelectPreset,globalState)
                        }
                    }
                }

            }
        }
    }
}
