package com.playmakers.groovy.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.playmakers.groovy.data.room.RoomMusic
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration.Companion.seconds

@Composable
fun MusicList(
    listMusic: Flow<List<RoomMusic>>
){
    val musicListSate by listMusic.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopSearchBar()
        },

        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {

            var isFABVisible by remember { mutableStateOf(false) }

            LaunchedEffect(Unit){
                delay(1500L)
                isFABVisible = true
            }

            AnimatedVisibility(
                visible = isFABVisible,
                enter = slideInHorizontally (initialOffsetX = { it })
            ) {
                ExtendedFloatingActionButton(
                    onClick = {
                        // TODO : Shuffle click event
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Shuffle,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Shuffle")
                }
            }
        },

        content = { innerPadding ->
            var isListVisible by remember { mutableStateOf(false) }

            LaunchedEffect(Unit){
                delay(1.seconds)
                isListVisible = true
            }

            AnimatedVisibility(
                visible = isListVisible,
                enter = slideInVertically(initialOffsetY = { it }),
            ) {
                LazyColumn(
                    modifier = Modifier.consumeWindowInsets(innerPadding),
                    contentPadding = innerPadding
                ){
                    items(count = musicListSate.size){
                        MusicRow(
                            musicListSate[it],
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSearchBar() {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    var isSearchVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        isSearchVisible = true
    }

    AnimatedVisibility(
        visible = isSearchVisible,
        enter = slideInVertically(initialOffsetY = { -it }),
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .semantics { isTraversalGroup = true }) {
            SearchBar(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
                    .fillMaxWidth(),
                query = text,
                onQueryChange = { text = it },
                onSearch = { active = false },
                active = active,
                onActiveChange = {
                    active = it
                },
                placeholder = { Text("Search your music") },
                leadingIcon = { Icon(Icons.Default.Menu, contentDescription = null) },
                trailingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            ) {
                repeat(4) { idx ->
                    val resultText = "Suggestion $idx"
                    ListItem(
                        headlineContent = { Text(resultText) },
                        supportingContent = { Text("Additional info") },
                        leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
                        modifier = Modifier
                            .clickable {
                                text = resultText
                                active = false
                            }
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun MusicRow(music : RoomMusic){
    Row(
        Modifier
            .clickable {
                // TODO : Music click event
            }
            .fillMaxWidth()
            .height(72.dp)
            .padding(vertical = 8.dp, horizontal = 16.dp),
    ){
        Box(
            Modifier.clip(RoundedCornerShape(5.dp))
        ){
//            AsyncImage(
//                model = music.actualImage,
//                placeholder = painterResource(R.drawable.default_album_art_mini),
//                contentDescription = "Album art",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.aspectRatio(1f)
//            )

            music.actualImage?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Album art",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.aspectRatio(1f)
                )
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = music.title,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = music.artist,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1
            )
        }
    }
}

/*
fun getAlbumArt(context: Context, uri: Uri, targetWidth: Int, targetHeight: Int): Bitmap {
    val mmr = MediaMetadataRetriever()
    mmr.setDataSource(context, uri)
    val data = mmr.embeddedPicture

    return if(data != null){
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(data, 0, data.size, options)
        options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight)
        options.inJustDecodeBounds = false
        BitmapFactory.decodeByteArray(data, 0, data.size, options)

    } else {
        BitmapFactory.decodeResource(context.resources, R.drawable.default_album_art_mini)
    }
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt()
        val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt()
        inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
    }
    return inSampleSize
}

 */
