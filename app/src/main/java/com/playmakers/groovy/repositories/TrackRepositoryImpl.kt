package com.playmakers.groovy.repositories

import com.playmakers.groovy.R
import com.playmakers.groovy.models.Track
import javax.inject.Inject

class TrackRepositoryImpl @Inject constructor() : TrackRepository{

    private val tracks = mutableListOf<Track>()

    init {
        createTracks()
    }

    private fun createTracks(){
        tracks.add(
            Track.Builder().trackName("295").trackUrl("295.mp3")
                .trackImage(R.drawable.sample_album_art)
                .artistName("Music Track Title 1 | Artist 1").trackId(1).build()
        )

        tracks.add(
            Track.Builder().trackName("295").trackUrl("295.mp3")
                .trackImage(R.drawable.sample_album_art)
                .artistName("Music Track Title 2 | Artist 2").trackId(1).build()
        )

        tracks.add(
            Track.Builder().trackName("295").trackUrl("295.mp3")
                .trackImage(R.drawable.sample_album_art)
                .artistName("Music Track Title 3 | Artist 3").trackId(1).build()
        )

        tracks.add(
            Track.Builder().trackName("295").trackUrl("295.mp3")
                .trackImage(R.drawable.sample_album_art)
                .artistName("Music Track Title 4 | Artist 4").trackId(1).build()
        )

        tracks.add(
            Track.Builder().trackName("295").trackUrl("295.mp3")
                .trackImage(R.drawable.sample_album_art)
                .artistName("Music Track Title 5 | Artist 5").trackId(1).build()
        )


        tracks.add(
            Track.Builder().trackName("295").trackUrl("295.mp3")
                .trackImage(R.drawable.sample_album_art)
                .artistName("Music Track Title 6 | Artist 6").trackId(1).build()
        )

        tracks.add(
            Track.Builder().trackName("295").trackUrl("295.mp3")
                .trackImage(R.drawable.sample_album_art)
                .artistName("Music Track Title 7 | Artist 7").trackId(1).build()
        )

        tracks.add(
            Track.Builder().trackName("295").trackUrl("295.mp3")
                .trackImage(R.drawable.sample_album_art)
                .artistName("Music Track Title 8 | Artist 8").trackId(1).build()
        )

        tracks.add(
            Track.Builder().trackName("295").trackUrl("295.mp3")
                .trackImage(R.drawable.sample_album_art)
                .artistName("Music Track Title 9 | Artist 9").trackId(1).build()
        )

        tracks.add(
            Track.Builder().trackName("295").trackUrl("295.mp3")
                .trackImage(R.drawable.sample_album_art)
                .artistName("Music Track Title 10 | Artist 10").trackId(1).build()
        )

        tracks.add(
            Track.Builder().trackName("295").trackUrl("295.mp3")
                .trackImage(R.drawable.sample_album_art)
                .artistName("Music Track Title 11 | Artist 11").trackId(1).build()
        )

        tracks.add(
            Track.Builder().trackName("295").trackUrl("295.mp3")
                .trackImage(R.drawable.sample_album_art)
                .artistName("Music Track Title 12 | Artist 12").trackId(1).build()
        )

        tracks.add(
            Track.Builder().trackName("295").trackUrl("295.mp3")
                .trackImage(R.drawable.sample_album_art)
                .artistName("Music Track Title 13 | Artist 13").trackId(1).build()
        )

        tracks.add(
            Track.Builder().trackName("295").trackUrl("295.mp3")
                .trackImage(R.drawable.sample_album_art)
                .artistName("Music Track Title 14 | Artist 14").trackId(1).build()
        )

        tracks.add(
            Track.Builder().trackName("295").trackUrl("295.mp3")
                .trackImage(R.drawable.sample_album_art)
                .artistName("Music Track Title 15 | Artist 15").trackId(1).build()
        )

        tracks.add(
            Track.Builder().trackName("295").trackUrl("295.mp3")
                .trackImage(R.drawable.sample_album_art)
                .artistName("Music Track Title 16 | Artist 16").trackId(1).build()
        )

        tracks.add(
            Track.Builder().trackName("295").trackUrl("295.mp3")
                .trackImage(R.drawable.sample_album_art)
                .artistName("Music Track Title 17 | Artist 17").trackId(1).build()
        )

        tracks.add(
            Track.Builder().trackName("295").trackUrl("295.mp3")
                .trackImage(R.drawable.sample_album_art)
                .artistName("Music Track Title 18 | Artist 18").trackId(1).build()
        )

        tracks.add(
            Track.Builder().trackName("295").trackUrl("295.mp3")
                .trackImage(R.drawable.sample_album_art)
                .artistName("Music Track Title 19 | Artist 19").trackId(1).build()
        )

        tracks.add(
            Track.Builder().trackName("295").trackUrl("295.mp3")
                .trackImage(R.drawable.sample_album_art)
                .artistName("Music Track Title 20 | Artist 20").trackId(1).build()
        )
    }

    override fun getTrackList(): List<Track> {
        return tracks
    }
}