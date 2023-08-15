package com.playmakers.groovy.player

import com.playmakers.groovy.data.Music

interface PlayerEvents {
    fun onPlayPauseClick()

    fun onPreviousClick()

    fun onNextClick()

    fun onTrackClick(music: Music)

    fun onSeekBarPositionChanged(position: Long)
}