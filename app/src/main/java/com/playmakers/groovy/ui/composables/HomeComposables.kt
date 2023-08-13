package com.playmakers.groovy.ui.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.playmakers.groovy.R

@OptIn(ExperimentalMaterial3Api::class)
// @Preview(showBackground = true)
@Composable
fun TopSearchBar() {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

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


// @Preview(showBackground = true)
@Composable
fun MusicList(){
    Row(
        Modifier
            .clickable { }
            .fillMaxWidth()
            .height(72.dp)
            .padding(vertical = 8.dp, horizontal = 16.dp),
    ){
        Box(
            Modifier.clip(RoundedCornerShape(5.dp))
        ){
            Image(
                painter = painterResource(R.drawable.album_art),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.aspectRatio(1f)
            )
        }

        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = "Music Title",
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Music Artist",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1
            )
        }
    }
}

// @Preview(showBackground = true)
@Composable
fun MiniHeading(){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            )
    ){
        Text(
            text = "Name",
            style = MaterialTheme.typography.labelMedium,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "278 Songs",
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomPlayback() {

    var isExpanded by rememberSaveable { mutableStateOf(true) }
    val cardModifier = Modifier.fillMaxWidth()

    Card(
        Modifier.fillMaxWidth()
    ) {
        Row(
            if(isExpanded){
                Modifier
                    .clickable { isExpanded = !isExpanded }
                    .animateContentSize()
                    .fillMaxSize()
                    .padding(vertical = 16.dp, horizontal = 16.dp)

            }else{
                Modifier
                    .clickable { isExpanded = !isExpanded }
                    .animateContentSize()
                    .fillMaxWidth()
                    .height(86.dp)
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            }
        ){
            if(isExpanded){
                Column(
                    Modifier.fillMaxSize()
                ) {
                    Text(text = "This is the expanded screen")
                }

            }else{
                Box(
                    Modifier.clip(RoundedCornerShape(5.dp))
                ){
                    Image(
                        painter = painterResource(R.drawable.album_art),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.aspectRatio(1f)
                    )
                }

                Column(
                    Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = "Music Title",
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Music Artist",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Box(modifier = Modifier.align(Alignment.CenterVertically)
                ){
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Rounded.Pause,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        }
    }
}