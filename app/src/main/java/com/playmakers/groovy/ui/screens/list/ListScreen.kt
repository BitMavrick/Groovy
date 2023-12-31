package com.playmakers.groovy.ui.screens.list

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Equalizer
import androidx.compose.material.icons.outlined.OpenInBrowser
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.playmakers.groovy.R
import com.playmakers.groovy.domain.model.RoomMusic
import com.playmakers.groovy.ui.components.MusicList
import com.playmakers.groovy.ui.screens.player.PlayerViewModel
import com.playmakers.groovy.ui.util.ListState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ListScreen(){
    val listViewModel = hiltViewModel<ListViewModel>()
    val playerViewModel = hiltViewModel<PlayerViewModel>()

    val listUiState = listViewModel.listUiState.collectAsState().value
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = listUiState.refreshState)

    val listEvent = listViewModel::onEvent

    when (listUiState.listState) {
        ListState.LOADING -> {
            Loading(
                loadingText = listUiState.loadingText
            )
        }
        ListState.LOADED -> {
            Drawer(
                listMusic = listUiState.musicListAsList,
                refreshState = swipeRefreshState,
                playerViewModel = playerViewModel,
                onSwipeRefresh = {
                    listEvent(ListEvent.RefreshList)
                }
            )
        }
        ListState.NOT_FOUND -> {
            NotFound(
                onRefreshClick = {
                    listEvent(ListEvent.RefreshList)
                }
            )
        }
    }
}

@Composable
fun Drawer(
    listMusic: List<RoomMusic>,
    refreshState: SwipeRefreshState,
    playerViewModel: PlayerViewModel,
    onSwipeRefresh: () -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    if(drawerState.isOpen){
        BackHandler {
            scope.launch { drawerState.close() }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                SidebarContent(
                    drawerState = drawerState,
                    scope = scope

                )
            }
        },
        content = {
            MusicList(
                listMusic = listMusic,
                refreshState = refreshState,
                playerViewModel = playerViewModel,
                onSwipeRefresh = {
                    onSwipeRefresh()
                },
                onDrawerButtonClick = {
                    scope.launch { drawerState.open() }
                }
            )
        }
    )
}

@Composable
fun SidebarContent(
    drawerState: DrawerState,
    scope: CoroutineScope
){
    val context = LocalContext.current
    Column(
        Modifier.padding(vertical = 8.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "release: 1.0.0-beta",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 26.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(12.dp))
        option(
            icon = Icons.Outlined.Equalizer,
            title = "Equalizer",
            onOptionClick = {
                showToast("Unavailable in this release", context)
            }
        )
        option(
            icon = Icons.Outlined.AutoAwesome,
            title = "Party tricks",
            onOptionClick = {
                showToast("Unavailable in this release", context)
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(12.dp))
        option(
            icon = Icons.Outlined.Settings,
            title = "Settings",
            onOptionClick = {
                showToast("Unavailable in this release", context)
            }
        )

        val openUrlContract = object : ActivityResultContract<String, Boolean>() {
            override fun createIntent(context: Context, input: String): Intent {
                return Intent(Intent.ACTION_VIEW, Uri.parse(input))
            }

            override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
                return resultCode == Activity.RESULT_OK
            }
        }

        val openUrlLauncher = rememberLauncherForActivityResult(openUrlContract){}

        option(
            icon = Icons.Outlined.OpenInBrowser,
            title = "View source-code",
            onOptionClick = {
                val url = "https://github.com/BitMavrick/Groovy"
                openUrlLauncher.launch(url)
                scope.launch { drawerState.close() }
            }
        )

        option(
            icon =  Icons.AutoMirrored.Outlined.HelpOutline,
            title = "Help & feedback",
            onOptionClick = {
                val url = "https://github.com/BitMavrick/Groovy/issues"
                openUrlLauncher.launch(url)
                scope.launch { drawerState.close() }
            }
        )
    }
}

fun showToast(message: String, context : Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun option(
    icon: ImageVector,
    title: String,
    onOptionClick : () -> Unit,
){
    Box(
        Modifier.clickable {
            onOptionClick()
        }
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 26.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = icon,
                contentDescription = title)
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun Loading(
    loadingText : String
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        LinearProgressIndicator(
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = loadingText,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun NotFound(
    onRefreshClick: () -> Unit
){
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.not_found))
    var isArtVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100L)
        isArtVisible = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = isArtVisible) {
            Box(
                modifier = Modifier.height(400.dp),
            ){
                LottieAnimation(
                    composition = composition,
                    iterations = 30,
                )
            }
        }

        Text(
            text = "No music found!",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Looks like you don't have any music files on this device.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 16.dp),
            textAlign = TextAlign.Center
        )

        AnimatedVisibility(visible = isArtVisible){
            OutlinedButton(
                modifier = Modifier.padding(top = 30.dp),
                onClick = {
                    onRefreshClick()
                }
            ) {
                Text(
                    text = "Refresh",
                )
            }
        }
    }
}