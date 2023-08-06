package com.playmakers.groovy.player

import com.playmakers.groovy.models.Track

interface PlayerEvents {

    /**
     * Invoked when the play or pause button is clicked.
     */
    fun onPlayPauseClick()

    /**
     * Invoked when the previous button is clicked.
     */
    fun onPreviousClick()

    /**
     * Invoked when the next button is clicked.
     */
    fun onNextClick()

    /**
     * Invoked when a track is clicked. The clicked [Track] is provided as a parameter.
     *
     * @param track The track that was clicked.
     */
    fun onTrackClick(track: Track)

    /**
     * Invoked when the position of the seek bar has changed. The new position is provided as a parameter.
     *
     * @param position The new position of the seek bar.
     */
    fun onSeekBarPositionChanged(position: Long)
}