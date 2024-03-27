package com.bitmavrick.groovy.core

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.audiofx.AudioEffect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSourceBitmapLoader
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.CacheBitmapLoader
import androidx.media3.session.CommandButton
import androidx.media3.session.DefaultMediaNotificationProvider
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import androidx.preference.PreferenceManager
import com.bitmavrick.groovy.R
import com.bitmavrick.groovy.core.util.LastPlayedManager
import com.bitmavrick.groovy.core.util.MediaStoreUtils
import com.bitmavrick.groovy.ui.MainActivity
import com.bitmavrick.groovy.core.util.LrcUtils.extractAndParseLyrics
import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.SettableFuture

@androidx.annotation.OptIn(UnstableApi::class)
class PlaybackService : MediaLibraryService(),
    MediaLibraryService.MediaLibrarySession.Callback, Player.Listener {

    companion object {
        private const val PLAYBACK_SHUFFLE_ACTION_ON = "shuffle_on"
        private const val PLAYBACK_SHUFFLE_ACTION_OFF = "shuffle_off"
        private const val PLAYBACK_REPEAT_OFF = "repeat_off"
        private const val PLAYBACK_REPEAT_ALL = "repeat_all"
        private const val PLAYBACK_REPEAT_ONE = "repeat_one"

        const val SERVICE_SET_TIMER = "set_timer"
        const val SERVICE_QUERY_TIMER = "query_timer"
        const val SERVICE_GET_LYRICS = "get_lyrics"
        const val SERVICE_TIMER_CHANGED = "changed_timer"
    }

    private var mediaSession: MediaLibrarySession? = null
    private var lyrics: List<MediaStoreUtils.Lyric>? = null
    private lateinit var customCommands: List<CommandButton>
    private lateinit var handler: Handler
    private lateinit var lastPlayedManager: LastPlayedManager

    private fun getRepeatCommand() =
        when (mediaSession?.player!!.repeatMode) {
            Player.REPEAT_MODE_OFF -> customCommands[2]
            Player.REPEAT_MODE_ALL -> customCommands[3]
            Player.REPEAT_MODE_ONE -> customCommands[4]
            else -> throw IllegalArgumentException()
        }

    private fun getShufflingCommand() =
        if (mediaSession?.player!!.shuffleModeEnabled)
            customCommands[1]
        else
            customCommands[0]

    private val timer: Runnable = Runnable {
        mediaSession?.player?.pause()
        timerDuration = 0
    }

    private var timerDuration = 0
        set(value) {
            field = value
            if (value > 0) {
                handler.postDelayed(timer, value.toLong())
            } else {
                handler.removeCallbacks(timer)
            }
            mediaSession!!.broadcastCustomCommand(
                SessionCommand(SERVICE_TIMER_CHANGED, Bundle.EMPTY),
                Bundle.EMPTY
            )
        }

    private lateinit var headSetReceiver: HeadSetReceiver

    override fun onCreate() {
        headSetReceiver = HeadSetReceiver()
        registerReceiver(
            headSetReceiver,
            IntentFilter(Intent.ACTION_HEADSET_PLUG)
        )

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        customCommands =
            listOf(
                CommandButton.Builder()
                    .setDisplayName(getString(R.string.shuffle))
                    .setSessionCommand(
                        SessionCommand(PLAYBACK_SHUFFLE_ACTION_ON, Bundle.EMPTY)
                    )
                    .setIconResId(R.drawable.ic_shuffle)
                    .build(),
                CommandButton.Builder()
                    .setDisplayName(getString(R.string.shuffle))
                    .setSessionCommand(
                        SessionCommand(PLAYBACK_SHUFFLE_ACTION_OFF, Bundle.EMPTY)
                    )
                    .setIconResId(R.drawable.ic_shuffle_on)
                    .build(),
                CommandButton.Builder()
                    .setDisplayName(getString(R.string.repeat_mode))
                    .setSessionCommand(
                        SessionCommand(PLAYBACK_REPEAT_ALL, Bundle.EMPTY)
                    )
                    .setIconResId(R.drawable.ic_repeat)
                    .build(),
                CommandButton.Builder()
                    .setDisplayName(getString(R.string.repeat_mode))
                    .setSessionCommand(
                        SessionCommand(PLAYBACK_REPEAT_ONE, Bundle.EMPTY)
                    )
                    .setIconResId(R.drawable.ic_repeat_on)
                    .build(),
                CommandButton.Builder()
                    .setDisplayName(getString(R.string.repeat_mode))
                    .setSessionCommand(
                        SessionCommand(PLAYBACK_REPEAT_OFF, Bundle.EMPTY)
                    )
                    .setIconResId(R.drawable.ic_repeat_one_on)
                    .build(),
            )
        handler = Handler(Looper.getMainLooper())

        val player = ExoPlayer.Builder(
            this,
            DefaultRenderersFactory(this)
                .setEnableAudioFloatOutput(prefs.getBoolean("floatoutput", false))
                .setEnableDecoderFallback(true)
                .setEnableAudioTrackPlaybackParams(
                    prefs.getBoolean(
                        "ps_hardware_acc",
                        true
                    )
                )
                .setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER)
        )
            .setWakeMode(C.WAKE_MODE_LOCAL)
            .setSkipSilenceEnabled(prefs.getBoolean("skip_silence", false))
            .setAudioAttributes(
                AudioAttributes
                    .Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                    .build(), true
            )
            .build()
        sendBroadcast(Intent(AudioEffect.ACTION_OPEN_AUDIO_EFFECT_CONTROL_SESSION).apply {
            putExtra(AudioEffect.EXTRA_PACKAGE_NAME, packageName)
            putExtra(AudioEffect.EXTRA_AUDIO_SESSION, player.audioSessionId)
            putExtra(AudioEffect.EXTRA_CONTENT_TYPE, AudioEffect.CONTENT_TYPE_MUSIC)
        })

        setMediaNotificationProvider(DefaultMediaNotificationProvider(this).apply {
            setSmallIcon(R.drawable.ic_album)
        })
        mediaSession =
            MediaLibrarySession
                .Builder(this, player, this)
                .setBitmapLoader(CacheBitmapLoader(DataSourceBitmapLoader(this)))
                .setSessionActivity(
                    PendingIntent.getActivity(
                        this,
                        0,
                        Intent(this, MainActivity::class.java),
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
                    ),
                )
                .build()
        lastPlayedManager = LastPlayedManager(this, mediaSession!!)
        handler.post {
            try {
                val restoreInstance = lastPlayedManager.restore()
                if (restoreInstance != null) {
                    player.setMediaItems(
                        restoreInstance.mediaItems,
                        restoreInstance.startIndex, restoreInstance.startPositionMs
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        onShuffleModeEnabledChanged(mediaSession!!.player.shuffleModeEnabled)
        mediaSession!!.player.addListener(this)
        super.onCreate()
    }

    override fun onDestroy() {
        lastPlayedManager.save()
        sendBroadcast(Intent(AudioEffect.ACTION_CLOSE_AUDIO_EFFECT_CONTROL_SESSION).apply {
            putExtra(AudioEffect.EXTRA_PACKAGE_NAME, packageName)
            putExtra(
                AudioEffect.EXTRA_AUDIO_SESSION,
                (mediaSession!!.player as ExoPlayer).audioSessionId
            )
        })
        mediaSession!!.player.release()
        mediaSession!!.release()
        mediaSession = null
        lyrics = null
        unregisterReceiver(headSetReceiver)
        super.onDestroy()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaLibrarySession? =
        mediaSession

    override fun onConnect(session: MediaSession, controller: MediaSession.ControllerInfo)
            : MediaSession.ConnectionResult {
        val availableSessionCommands =
            MediaSession.ConnectionResult.DEFAULT_SESSION_AND_LIBRARY_COMMANDS.buildUpon()
        for (commandButton in customCommands) {
            commandButton.sessionCommand?.let { availableSessionCommands.add(it) }
        }
        availableSessionCommands.add(SessionCommand(SERVICE_SET_TIMER, Bundle.EMPTY))
        availableSessionCommands.add(SessionCommand(SERVICE_QUERY_TIMER, Bundle.EMPTY))
        availableSessionCommands.add(SessionCommand(SERVICE_GET_LYRICS, Bundle.EMPTY))
        return MediaSession.ConnectionResult.AcceptedResultBuilder(session)
            .setAvailableSessionCommands(availableSessionCommands.build())
            .build()
    }

    override fun onCustomCommand(
        session: MediaSession,
        controller: MediaSession.ControllerInfo,
        customCommand: SessionCommand,
        args: Bundle
    ): ListenableFuture<SessionResult> {
        return Futures.immediateFuture(when (customCommand.customAction) {
            PLAYBACK_SHUFFLE_ACTION_ON -> {
                session.player.shuffleModeEnabled = true
                SessionResult(SessionResult.RESULT_SUCCESS)
            }

            PLAYBACK_SHUFFLE_ACTION_OFF -> {
                session.player.shuffleModeEnabled = false
                SessionResult(SessionResult.RESULT_SUCCESS)
            }

            SERVICE_SET_TIMER -> {
                timerDuration = customCommand.customExtras.getInt("duration")
                SessionResult(SessionResult.RESULT_SUCCESS)
            }

            SERVICE_QUERY_TIMER -> {
                SessionResult(SessionResult.RESULT_SUCCESS).also {
                    it.extras.putInt("duration", timerDuration)
                }
            }

            SERVICE_GET_LYRICS -> {
                SessionResult(SessionResult.RESULT_SUCCESS).also {
                    it.extras.putParcelableArray("lyrics", lyrics?.toTypedArray())
                }
            }

            PLAYBACK_REPEAT_OFF -> {
                session.player.repeatMode = Player.REPEAT_MODE_OFF
                SessionResult(SessionResult.RESULT_SUCCESS)
            }

            PLAYBACK_REPEAT_ONE -> {
                session.player.repeatMode = Player.REPEAT_MODE_ONE
                SessionResult(SessionResult.RESULT_SUCCESS)
            }

            PLAYBACK_REPEAT_ALL -> {
                session.player.repeatMode = Player.REPEAT_MODE_ALL
                SessionResult(SessionResult.RESULT_SUCCESS)
            }

            else -> {
                SessionResult(SessionResult.RESULT_ERROR_BAD_VALUE)
            }
        })
    }

    override fun onPlaybackResumption(
        mediaSession: MediaSession,
        controller: MediaSession.ControllerInfo
    ): ListenableFuture<MediaSession.MediaItemsWithStartPosition> {
        val settable = SettableFuture.create<MediaSession.MediaItemsWithStartPosition>()
        handler.post {
            settable.set(lastPlayedManager.restore())
        }
        return settable
    }

    override fun onTracksChanged(tracks: Tracks) {
        lyrics = null
        for (i in tracks.groups) {
            for (j in 0 until i.length) {
                val trackMetadata = i.getTrackFormat(j).metadata ?: continue
                lyrics = extractAndParseLyrics(
                    mediaSession?.player?.currentMediaItem?.getFile(), trackMetadata
                ) ?: continue
            }
        }
        mediaSession!!.broadcastCustomCommand(
            SessionCommand(SERVICE_GET_LYRICS, Bundle.EMPTY),
            Bundle.EMPTY
        )
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        lyrics = null
        lastPlayedManager.save()
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        lastPlayedManager.save()
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        super.onShuffleModeEnabledChanged(shuffleModeEnabled)
        mediaSession!!.setCustomLayout(ImmutableList.of(getRepeatCommand(), getShufflingCommand()))
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
        super.onRepeatModeChanged(repeatMode)
        mediaSession!!.setCustomLayout(ImmutableList.of(getRepeatCommand(), getShufflingCommand()))
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        if (!mediaSession!!.player.playWhenReady) {
            stopSelf()
        }
    }

    inner class HeadSetReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action.equals(Intent.ACTION_HEADSET_PLUG) &&
                intent.getIntExtra("state", -1) == 0) {
                mediaSession?.player?.pause()
            }
        }
    }
}