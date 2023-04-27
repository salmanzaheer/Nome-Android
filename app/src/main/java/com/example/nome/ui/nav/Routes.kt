package com.example.nome.ui.nav

sealed class Routes(val route: String) {
    object MainScreen : Routes("main")
    object UserPresetScreen : Routes("userPreset")
    object OnlinePresetScreen : Routes("onlinePreset")
}