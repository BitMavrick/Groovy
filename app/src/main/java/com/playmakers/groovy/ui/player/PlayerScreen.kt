package com.playmakers.groovy.ui.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.rounded.PlayCircleFilled
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.playmakers.groovy.ui.theme.GroovyTheme

@Composable
private fun PlayerButtons(
    modifier: Modifier = Modifier,
    playerButtonSize: Dp = 72.dp,
    sideButtonSize: Dp = 48.dp
){
    Row (
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        val buttonsModifier = Modifier
            .size(sideButtonSize)
            .semantics { role = Role.Button }

        Image(
            imageVector = Icons.Filled.SkipPrevious,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = buttonsModifier
        )

        Image(
            imageVector = Icons.Rounded.PlayCircleFilled,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(playerButtonSize)
                .semantics { role = Role.Button }
        )

        Image(
            imageVector = Icons.Filled.SkipNext,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = buttonsModifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerButtonsPreview(){
    GroovyTheme {
        PlayerButtons()
    }
}
