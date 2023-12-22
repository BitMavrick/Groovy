package com.playmakers.groovy.service

import android.app.PendingIntent
import android.content.Intent
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.playmakers.groovy.MainActivity

class PlaybackService : MediaSessionService() {

    private var mediaSession : MediaSession? = null
    private lateinit var exoPlayer: ExoPlayer

    private val audioAttributes = AudioAttributes.Builder()
        .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
        .setUsage(C.USAGE_MEDIA)
        .build()

    private fun createPendingIntent(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    override fun onCreate(){
        super.onCreate()

        initExoPlayer()
        mediaSession = MediaSession.Builder(this, exoPlayer)
            .setCallback(MediaSessionCallback())
            .setSessionActivity(createPendingIntent())
            .build()
    }
    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo) = mediaSession

    override fun onDestroy() {
        mediaSession?.run {
            exoPlayer.release()
            release()
            mediaSession = null
        }

        super.onDestroy()
    }

    private fun initExoPlayer() {
        exoPlayer = ExoPlayer.Builder(this)
            .setAudioAttributes(audioAttributes, true)
            .setHandleAudioBecomingNoisy(true)
            .build()
    }

    private inner class MediaSessionCallback : MediaSession.Callback {
        override fun onAddMediaItems(
            mediaSession: MediaSession,
            controller: MediaSession.ControllerInfo,
            mediaItems: MutableList<MediaItem>
        ): ListenableFuture<MutableList<MediaItem>> {
            val updatedMediaItems = mediaItems.map {
                it.buildUpon().setUri(it.mediaId).build()
            }.toMutableList()

            return Futures.immediateFuture(updatedMediaItems)
        }
    }
}