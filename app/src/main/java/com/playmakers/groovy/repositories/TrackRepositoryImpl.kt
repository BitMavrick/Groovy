package com.playmakers.groovy.repositories

import com.playmakers.groovy.models.Track
import javax.inject.Inject

class TrackRepositoryImpl @Inject constructor() : TrackRepository{
    private val tracks = mutableListOf<Track>()



    override fun getTrackList(): List<Track> {
        TODO("Not yet implemented")
    }
}