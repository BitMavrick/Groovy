package com.playmakers.groovy.di

import android.app.Application
import com.playmakers.groovy.controller.AddMusic
import com.playmakers.groovy.controller.DestroyMusicPlaybackControl
import com.playmakers.groovy.controller.GetMusicPosition
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
    fun provideMusicRepository(app: Application) : MusicRepository{
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
    fun provideDestroyMusicPlaybackControl(playbackControl: PlaybackControl): DestroyMusicPlaybackControl{
        return DestroyMusicPlaybackControl(playbackControl)
    }

    @Provides
    @Singleton
    fun provideGetMusicPosition(playbackControl: PlaybackControl) : GetMusicPosition {
        return GetMusicPosition(playbackControl)
    }

    @Provides
    @Singleton
    fun provide1(playbackControl: PlaybackControl){

    }

    @Provides
    @Singleton
    fun provide2(playbackControl: PlaybackControl){

    }

    @Provides
    @Singleton
    fun provide3(playbackControl: PlaybackControl){

    }

    @Provides
    @Singleton
    fun provide4(playbackControl: PlaybackControl){

    }

    @Provides
    @Singleton
    fun provide5(playbackControl: PlaybackControl){

    }

    @Provides
    @Singleton
    fun provide6(playbackControl: PlaybackControl){

    }

    @Provides
    @Singleton
    fun provide7(playbackControl: PlaybackControl){

    }

    @Provides
    @Singleton
    fun provide8(playbackControl: PlaybackControl){

    }
}