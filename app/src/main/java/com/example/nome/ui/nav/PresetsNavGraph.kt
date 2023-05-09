package com.example.nome.ui.nav

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nome.ui.Body
import com.example.nome.ui.presetlist.PresetListView
import com.example.nome.ui.presetlist.PresetListViewModel
import com.example.nome.ui.presetlist.UserPresetListViewModel

@Composable
fun PresetsNavGraph(
    navController: NavController = rememberNavController()
) {
    val vm: PresetListViewModel = viewModel()
    val userVm: UserPresetListViewModel = viewModel()
    NavHost(
        navController = navController as NavHostController,
        startDestination = Routes.MainScreen.route
    ) {
        composable(Routes.MainScreen.route) {
            Body()
        }
        composable(Routes.OnlinePresetScreen.route) {
            OnlinePresetListScreen(vm = vm)
        }
        composable(Routes.UserPresetScreen.route) {
            UserPresetListScreen(vm = userVm)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserPresetListScreen(
    vm: UserPresetListViewModel
) {
    /*TODO - Confirmation*/
    val presets by vm.presets
    val selectedPreset by vm.selectedPreset
    
    PresetListView(
        presets = presets, 
        selectedPreset = selectedPreset, 
        waiting = vm.waiting.value, 
        onDelete = vm::deletePreset,
        onSelectPreset = vm::selectedPreset)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnlinePresetListScreen(
    vm: PresetListViewModel
) {
    /*TODO - Confirmation*/
    val presets by vm.presets
    val selectedPreset by vm.selectedPreset

    PresetListView(
        presets = presets,
        selectedPreset = selectedPreset,
        waiting = vm.waiting.value,
        onDelete = {/*TODO - deletePreset function*/},
        onSelectPreset = vm::selectedPreset)
}