package com.playmakers.groovy.di

import android.app.Application
import com.playmakers.groovy.controller.AddMusic
import com.playmakers.groovy.controller.DestroyMusicPlaybackControl
import com.playmakers.groovy.controller.GetMusicPosition
import com.playmakers.groovy.controller.PauseMusic
import com.playmakers.groovy.controller.PlayMusic
import com.playmakers.groovy.controller.ResumeMusic
import com.playmakers.groovy.controller.SetMediaControlCallback
import com.playmakers.groovy.controller.SetShuffleMode
import com.playmakers.groovy.controller.SkipNextMusic
import com.playmakers.groovy.controller.SkipPreviousMusic
import com.playmakers.groovy.data.MusicDataContainer
import com.playmakers.groovy.data.MusicsRepository
import com.playmakers.groovy.data.repository.MusicRepositoryImpl
import com.playmakers.groovy.data.repository.PlaybackControlImpl
import com.playmakers.groovy.domain.model.PlaybackControl
import com.playmakers.groovy.domain.repository.MusicRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMusicRepository(app: Application) : MusicRepository {
        return MusicRepositoryImpl(app)
    }

    @Provides
    @Singleton
    fun provideMusicContainer(app: Application) : MusicsRepository {
        return MusicDataContainer(app).musicsRepository
    }

    @Provides
    @Singleton
    fun providePlaybackControl(app: Application) : PlaybackControl {
        return PlaybackControlImpl(app.applicationContext)
    }

    @Provides
    @Singleton
    fun provideAddMusic(playbackControl: PlaybackControl): AddMusic {
        return AddMusic(playbackControl)
    }

    @Provides
    @Singleton
    fun provideDestroyMusicPlaybackControl(playbackControl: PlaybackControl): DestroyMusicPlaybackControl {
        return DestroyMusicPlaybackControl(playbackControl)
    }

    @Provides
    @Singleton
    fun provideGetMusicPosition(playbackControl: PlaybackControl) : GetMusicPosition {
        return GetMusicPosition(playbackControl)
    }

    @Provides
    @Singleton
    fun providePauseMusic(playbackControl: PlaybackControl) : PauseMusic {
        return PauseMusic(playbackControl)
    }

    @Provides
    @Singleton
    fun providePlayMusic(playbackControl: PlaybackControl) : PlayMusic {
        return PlayMusic(playbackControl)
    }

    @Provides
    @Singleton
    fun provideResumeMusic(playbackControl: PlaybackControl) : ResumeMusic {
        return ResumeMusic(playbackControl)
    }

    @Provides
    @Singleton
    fun provideSetMediaControlCallback(playbackControl: PlaybackControl) : SetMediaControlCallback {
        return SetMediaControlCallback(playbackControl)
    }

    @Provides
    @Singleton
    fun provideSkipNextMusic(playbackControl: PlaybackControl) : SkipNextMusic {
        return SkipNextMusic(playbackControl)
    }

    @Provides
    @Singleton
    fun provideSkipPreviousMusic(playbackControl: PlaybackControl) : SkipPreviousMusic {
        return SkipPreviousMusic(playbackControl)
    }


    @Provides
    @Singleton
    fun provideSetShuffleMode(playbackControl: PlaybackControl) : SetShuffleMode {
        return SetShuffleMode(playbackControl)
    }
}