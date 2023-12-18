package com.example.chromaaid.view.ui.Main.nav

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Profile : Screen("profile")
    data object Setting : Screen("setting")
}