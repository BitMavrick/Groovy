package com.playmakers.groovy.ui.screens.homeScreen

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.playmakers.groovy.ui.composables.MiniHeading
import com.playmakers.groovy.ui.composables.TopSearchBar

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun HomeScreen() {

    Scaffold(
        topBar = { TopSearchBar() },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* fab click handler */ }
            ) {
                Icon(
                    imageVector = Icons.Default.Shuffle,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Shuffle")
            }
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.consumeWindowInsets(innerPadding),
                contentPadding = innerPadding
            ) {
                item {
                    MiniHeading()
                }
                val list = List(100) { "Text $it" }
                items(count = list.size) {
                    Text(list[it],
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp))
                }
            }
        }
    )
}





