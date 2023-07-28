package com.playmakers.groovy.ui.home

import androidx.compose.runtime.Composable

typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var title: String, var screen: ComposableFun){

    object Home : TabItem("Home", { HomeScreen() })
    object Profile : TabItem("Profile", { ProfileScreen() })
    object Settings : TabItem("Settings", { SettingsScreen() })

}