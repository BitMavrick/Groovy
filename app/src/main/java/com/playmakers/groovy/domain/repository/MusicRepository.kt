package com.playmakers.groovy.domain.repository

interface MusicRepository {
    suspend fun getMusicFiles()
}