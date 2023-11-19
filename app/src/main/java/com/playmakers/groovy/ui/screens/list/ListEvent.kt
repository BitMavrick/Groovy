package com.playmakers.groovy.ui.screens.list

sealed class ListEvent {
    object RefreshList : ListEvent()
}