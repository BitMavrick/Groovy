package com.playmakers.groovy.model

interface PlayBackControl {
    var mediaControllerCallback: (
        (playerState: PlayerState,
         currentMusic: Music?,
         currentPosition: Long,
         totalDuration: Long,
         isShuffleEnabled: Boolean,
         isRepeatOneEnabled: Boolean) -> Unit
    )?

    fun addMediaItems(musics: List<Music>)

    fun play(mediaItemIndex: Int)

    fun resume()

    fun pause()

    fun seekTo(position: Long)

    fun skipNext()

    fun skipPrevious()

    fun setShuffleModeEnabled(isEnabled: Boolean)

    fun setRepeatOneEnabled(isEnabled: Boolean)

    fun getCurrentPosition(): Long

    fun destroy()
}