package com.playmakers.groovy.ui.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.playmakers.groovy.ui.theme.GroovyTheme
@Composable
private fun TopAppBar(){
    Row(Modifier.fillMaxWidth()) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Rounded.ExpandMore,
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(Modifier.weight(1f))

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Rounded.MoreVert,
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
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
                color = Color.Blue
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
                color = Color.Blue
            )
        }

        Surface(
            modifier = Modifier
                .size(sideButtonSize)
                .semantics { role = Role.Button },
            shape = RoundedCornerShape(CornerSize(50.dp)),
            color = Color.LightGray,
        ) {
            ColoredIcon(
                icon = Icons.Rounded.SkipNext,
                color = Color.Blue,
            )
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

@Preview(showBackground = true)
@Composable
fun TopAppBarPreview(){
    GroovyTheme {
        TopAppBar()
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerButtonsPreview(){
    GroovyTheme {
        PlayerButtons()
    }
}
