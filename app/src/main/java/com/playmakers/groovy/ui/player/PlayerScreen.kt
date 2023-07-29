package com.playmakers.groovy.ui.player

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlaylistPlay
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.playmakers.groovy.R
import com.playmakers.groovy.ui.theme.GroovyTheme
import java.time.Duration

@Composable
private fun TopAppBar(){
    Row(Modifier.fillMaxWidth()) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Rounded.ExpandMore,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(Modifier.weight(1f))

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Rounded.MoreVert,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
private fun AlbumArt(){
    Box(
        Modifier
            .clip(RoundedCornerShape(32.dp))
    ) {
        Image(
            painter = painterResource(R.drawable.sample_album_art),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun MusicInformation(
    title: String,
    artist: String,
    album: String
){
    Row(modifier = Modifier.fillMaxWidth()){
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 3.dp)
            )

            Row {
                Text(text = artist, fontSize = 14.sp)
                Text(text = " | ", fontSize = 14.sp)
                Text(text = album, fontSize = 14.sp)
            }
        }

        Spacer(Modifier.weight(1f))

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Rounded.Favorite,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun PlayerSlider(duration: Duration?){
    if(duration != null){
        Column(Modifier.fillMaxWidth()) {
            Slider(value = 0f, onValueChange = {})
            Row(Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "0s", fontSize = 12.sp)
                Text(text = " / " , fontSize = 12.sp)
                Text("${duration.seconds}s", fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun PlayerButtons(
    modifier: Modifier = Modifier,
    playerButtonSize: Dp = 72.dp,
    sideButtonSize: Dp = 60.dp
){
    Row (
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ){

        Surface(
            modifier = Modifier
                .size(sideButtonSize)
                .semantics { role = Role.Button },
            shape = RoundedCornerShape(CornerSize(50.dp)),
            color = Color.LightGray
        ) {
            ColoredIcon(
                icon = Icons.Rounded.SkipPrevious,
                color = Color.DarkGray
            )
        }

        Surface(
            modifier = Modifier
                .height(playerButtonSize)
                .width(100.dp)
                .semantics { role = Role.Button },
            shape = RoundedCornerShape(CornerSize(20.dp)),
            color = Color.LightGray,

        ) {
            ColoredIcon(
                icon = Icons.Rounded.Pause,
                color = Color.DarkGray,
            )
        }

        Surface(
            modifier = Modifier
                .size(sideButtonSize)
                .semantics { role = Role.Button },
            shape = RoundedCornerShape(CornerSize(50.dp)),
            color = MaterialTheme.colorScheme.primary,
        ) {
            ColoredIcon(
                icon = Icons.Rounded.SkipNext,
                color = Color.Green
            )
        }
    }
}

@Composable
private fun BottomBar(modifier: Modifier = Modifier){
    Surface(modifier = modifier) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(48.dp)){

            IconButton(onClick = { /*TODO*/ }) {
                Surface(
                    modifier = Modifier
                        .size(40.dp)
                        .semantics { role = Role.Button },
                    shape = RoundedCornerShape(CornerSize(50.dp)),
                    color = Color.LightGray,
                ) {
                    ColoredIcon(
                        icon = Icons.Rounded.VolumeUp,
                        color = Color.DarkGray,
                    )
                }
            }

            Box(modifier = Modifier
                .fillMaxHeight()
                .wrapContentHeight(Alignment.CenterVertically)
                .padding(start = 5.dp)
            ){
                Text(
                    text = "Galaxy A51",
                    fontSize = 14.sp
                )
            }

            Spacer(Modifier.weight(1f))

            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.PlaylistPlay,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Composable
fun ColoredIcon(icon: ImageVector, color: Color) {
    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = color,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlayerScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(.8f)

        ) {
            TopAppBar()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f)
        ) {
            AlbumArt()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)

        ) {
            Surface(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(top = 15.dp)) {
                    MusicInformation(
                        title = "Agar Tu Hota",
                        artist = "Ankit Tiwari",
                        album = "Baaghi"
                    )

                    PlayerSlider(duration = Duration.ofSeconds(100))

                    Box(modifier = Modifier.padding(top = 10.dp)){
                        PlayerButtons()
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)

        ) {
            BottomBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview("Light Theme")
// @Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PlayerScreenPreview(){
    GroovyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            PlayerScreen()
        }
    }
}

