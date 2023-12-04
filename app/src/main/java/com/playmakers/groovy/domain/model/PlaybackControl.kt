package com.playmakers.groovy.domain.model

interface PlaybackControl {
    var mediaControllerCallback: (
        (playerState: PlayerState,
         currentMusic: RoomMusic?,
         currentPosition: Long,
         totalDuration: Long,
         isShuffleEnabled: Boolean,
         isRepeatOneEnabled: Boolean) -> Unit
    )?

    fun addMediaItems(musics: List<RoomMusic>)

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