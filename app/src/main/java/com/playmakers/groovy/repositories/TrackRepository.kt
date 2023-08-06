package com.playmakers.groovy.repositories

import com.playmakers.groovy.models.Track

interface TrackRepository {

    fun getTrackList(): List<Track>
}