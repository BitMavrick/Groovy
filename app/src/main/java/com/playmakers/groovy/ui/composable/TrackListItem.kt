package com.playmakers.groovy.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.playmakers.groovy.models.Track
import com.playmakers.groovy.ui.theme.md_theme_light_onPrimary
import com.playmakers.groovy.ui.theme.md_theme_light_onSurfaceVariant
import com.playmakers.groovy.ui.theme.md_theme_light_primary
import com.playmakers.groovy.ui.theme.md_theme_light_surfaceVariant
import com.playmakers.groovy.player.PlayerStates.STATE_PLAYING

@Composable
fun TrackListItem(track: Track, onTrackClick: () -> Unit) {
    val bgColor = if (track.isSelected) md_theme_light_primary else md_theme_light_surfaceVariant
    val textColor =
        if (track.isSelected) md_theme_light_onPrimary else md_theme_light_onSurfaceVariant
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(all = 5.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = bgColor)
            .clickable(onClick = onTrackClick)
    ) {
        TrackImage(trackImage = track.trackImage, modifier = Modifier.size(size = 64.dp))
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .weight(weight = 1f)
        ) {

            Text(text = track.trackName, style = typography.body1, color = textColor)
            Text(text = track.artistName, style = typography.body1, color = textColor)
        }
        if (track.state == STATE_PLAYING) LottieAudioWave()
    }
}

/**
 * A composable function that displays a Lottie animation of an audio wave.
 * The animation loops indefinitely.
 */
@Composable
fun LottieAudioWave() { // From here
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.audio_wave))
    LottieAnimation(
        composition = composition,
        iterations = Int.MAX_VALUE,
        modifier = Modifier.size(size = 64.dp)
    )
}