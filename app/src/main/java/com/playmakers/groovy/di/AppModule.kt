package com.playmakers.groovy.di

import android.app.Application
import com.playmakers.groovy.data.MusicDataContainer
import com.playmakers.groovy.data.repository.MusicRepositoryImpl
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
    fun provideMusicContainer(app: Application) : MusicDataContainer {
        return MusicDataContainer(app)
    }
}