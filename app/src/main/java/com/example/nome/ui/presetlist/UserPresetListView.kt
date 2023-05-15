package com.example.nome.ui.presetlist

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nome.model.Preset
import com.example.nome.model.UserPreset
import com.example.nome.ui.AddPresetFAB
import com.example.nome.ui.LandscapeView
import com.example.nome.ui.PresetRow
import com.example.nome.ui.UserPresetRow
import com.example.nome.ui.confirmDialog.ConfirmViewModel
import com.example.nome.ui.nav.Routes
import com.example.nome.ui.newpreset.NewPresetView
import com.example.nome.ui.newpreset.NewPresetViewModel
import com.example.nome.ui.theme.globalStateDataClass
import kotlin.reflect.KProperty0

@ExperimentalFoundationApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserPresetListView(
    presets: List<UserPreset>,
    selectedPreset: UserPreset?,
    confirmViewModel: ConfirmViewModel,
    // TODO - add confirm dialog,
<<<<<<< HEAD
    onDelete: suspend (Preset) -> Unit,
    onSelectPreset: KProperty0<androidx.compose.runtime.State<Preset?>>,
    navController: NavController,
    globalState: globalStateDataClass
=======
    onDelete: suspend (UserPreset) -> Unit,
    onSelectPreset: KProperty0<androidx.compose.runtime.State<UserPreset?>>,
    navController: NavController
>>>>>>> 8fce1ec768c2c4d81882d4f1e671aebe2edf8124
) {

    val showAddPresetsScreen =  remember { mutableStateOf(false)}
    val newPresetVm: NewPresetViewModel = viewModel()

    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val config = LocalConfiguration.current
                if(config.orientation == Configuration.ORIENTATION_PORTRAIT){
                    LazyColumn{
                        itemsIndexed(presets){idx, preset ->
<<<<<<< HEAD
                            PresetRow(idx = idx, preset = preset, { idx -> confirmViewModel.showConfirmDelete(onConfirm = {onDelete(preset)})}, onSelect = onSelectPreset,globalState)
=======
                            UserPresetRow(idx = idx, preset = preset, { idx -> confirmViewModel.showConfirmDelete(onConfirm = {onDelete(preset)})}, onSelect = onSelectPreset)
>>>>>>> 8fce1ec768c2c4d81882d4f1e671aebe2edf8124
                        }
                    }

                } else {
                    LandscapeView(selectedPreset?.name) {
                        LazyColumn{
                            itemsIndexed(presets){idx, preset ->
<<<<<<< HEAD
                                PresetRow(idx = idx, preset = preset, { idx -> confirmViewModel.showConfirmDelete(onConfirm = {onDelete(preset)})}, onSelect = onSelectPreset,globalState)
=======
                                UserPresetRow(idx = idx, preset = preset, { idx -> confirmViewModel.showConfirmDelete(onConfirm = {onDelete(preset)})}, onSelect = onSelectPreset)
>>>>>>> 8fce1ec768c2c4d81882d4f1e671aebe2edf8124
                            }
                        }
                    }
                }
            }
        }
        ExtendedFloatingActionButton(
            text = { Text(text = "Add Preset") },
            onClick = {
                navController.navigate(Routes.NewPreset.route){
                    popUpTo(Routes.NewPreset.route)
                }
            },
            backgroundColor = Color(0xffE74E35),
            icon = { Icon(Icons.Filled.Add, contentDescription = "add preset") }
        )
    }
}