package com.playmakers.groovy.module

import com.playmakers.groovy.repositories.MusicRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideTrackRepository(musicRepository: MusicRepository) : MusicRepository {
        return musicRepository
    }
}