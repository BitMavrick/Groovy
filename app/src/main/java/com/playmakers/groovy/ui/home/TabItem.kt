package com.playmakers.groovy.ui.home

import androidx.compose.runtime.Composable

typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var title: String, var screen: ComposableFun){

    object Tracks : TabItem("Tracks", { TracksScreen() })
    object Albums : TabItem("Albums", { AlbumsScreen() })
    object Favorites : TabItem("Favorites", { FavoritesScreen() })

}