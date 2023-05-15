package com.example.nome.ui.nav

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nome.ui.Body
import com.example.nome.ui.confirmDialog.ConfirmViewModel
import com.example.nome.ui.newpreset.NewPresetView
import com.example.nome.ui.newpreset.NewPresetViewModel
import com.example.nome.ui.presetlist.PresetListView
import com.example.nome.ui.presetlist.PresetListViewModel
import com.example.nome.ui.presetlist.UserPresetListView
import com.example.nome.ui.presetlist.UserPresetListViewModel
import com.example.nome.ui.theme.globalStateDataClass

@Composable
fun PresetsNavGraph(
    navController: NavController = rememberNavController()
) {
    val vm: PresetListViewModel = viewModel()
    val userVm: UserPresetListViewModel = viewModel()
    val confirmVm: ConfirmViewModel = viewModel()
    val newPresetVm: NewPresetViewModel = viewModel()
    //Global State vals. Added to fix values from resetting to default mutableStates when changing navs
    val globalStates = globalStateDataClass(
        State = false,
        Slider = 60f,
        Bpm = 60)
    NavHost(
        navController = navController as NavHostController,
        startDestination = Routes.MainScreen.route
    ) {
        composable(Routes.MainScreen.route) {
            Body(globalStates)
        }
        composable(Routes.OnlinePresetScreen.route) {
            OnlinePresetListScreen(vm = vm, globalStates)
        }
        composable(Routes.UserPresetScreen.route) {
            UserPresetListScreen(vm = userVm, confirmVm, navController,globalStates)
        }
        composable(Routes.NewPreset.route){
            NewPresetView(
                newPresetVm,
                navController,
                onAddPreset = {preset->
                    userVm.addPreset(preset)
                }
            )
        }
    }
}




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserPresetListScreen(
    vm: UserPresetListViewModel,
    cvm: ConfirmViewModel,
    navController: NavController,
    globalState: globalStateDataClass
) {

    val userPresets by vm.userPresets
    val selectedPreset by vm.selectedPreset
    
    UserPresetListView(
        presets = userPresets,
        selectedPreset = selectedPreset,
        confirmViewModel = cvm,
        onDelete = vm::deletePreset,
        onSelectPreset = vm::selectedPreset,
        navController = navController,
        globalState = globalState)

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnlinePresetListScreen(
    vm: PresetListViewModel,
    globalState: globalStateDataClass
) {

    val presets by vm.presets
    val selectedPreset by vm.selectedPreset

    //Get rid of on delete.
    PresetListView(
        presets = presets,
        selectedPreset = selectedPreset,
        waiting = vm.waiting.value,
        onDelete = {/*Don't delete online database*/},
        onSelectPreset = vm::selectedPreset,
        globalState = globalState)
}