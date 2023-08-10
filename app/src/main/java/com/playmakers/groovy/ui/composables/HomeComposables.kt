package com.playmakers.groovy.ui.composables

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import com.playmakers.groovy.R
import com.playmakers.groovy.data.Music
import com.playmakers.groovy.data.getMusic
import com.playmakers.groovy.ui.screens.homeScreen.MusicPlayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent() {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .semantics { traversalIndex = -1f }
                .padding(horizontal = 16.dp, vertical = 8.dp),
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
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        val context = LocalContext.current
        val musicFiles = remember { getMusic(context) }
        val musicPlayer = MusicPlayer(context = LocalContext.current)

        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, top = 80.dp, end = 16.dp, bottom = 136.dp),
            // verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            val list = List(100) { "number $it" }
            items(count = list.size) {
                MusicList(musicFiles[it]) { clickedMusic ->
                    musicPlayer.playMusic(clickedMusic.contentUri!!)
                }
            }
        }
    }
}

@Composable
fun MusicList(music : Music, onItemClick: (Music) -> Unit){
    Row(
        Modifier
            .clickable { onItemClick(music) }
            .fillMaxWidth()
            .height(72.dp)
            .padding(vertical = 8.dp),
    ){
        Box(
            Modifier.clip(RoundedCornerShape(5.dp))
        ){
            val bitmap = music.contentUri?.let { getAlbumArt(LocalContext.current, it) }

            if (bitmap != null){
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.aspectRatio(1f)
                )
            }else{
                Image(
                    painter = painterResource(R.drawable.album_art),
                    contentDescription = null,
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

fun getAlbumArt(context: Context, uri: Uri): Bitmap {
    val mmr = MediaMetadataRetriever()
    mmr.setDataSource(context, uri)
    val data = mmr.embeddedPicture
    return if(data != null){
        BitmapFactory.decodeByteArray(data, 0, data.size)

    }else{
        BitmapFactory.decodeResource(context.resources, R.drawable.album_art)
    }
}