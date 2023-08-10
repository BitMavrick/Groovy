package com.playmakers.groovy.data

import android.net.Uri

data class Music(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val path: String,
    val contentUri: Uri?
)

val dummyMusicList = listOf(
    Music(
        id = 1,
        title = "Song 1",
        artist = "Artist 1",
        album = "Album 1",
        duration = 180000, // Duration in milliseconds
        path = "/path/to/song1.mp3",
        contentUri = Uri.parse("content://media/external/audio/media/1")
    ),
    Music(
        id = 2,
        title = "Song 2",
        artist = "Artist 2",
        album = "Album 2",
        duration = 240000, // Duration in milliseconds
        path = "/path/to/song2.mp3",
        contentUri = Uri.parse("content://media/external/audio/media/2")
    ),
    Music(
        id = 3,
        title = "Song 3",
        artist = "Artist 3",
        album = "Album 3",
        duration = 240000, // Duration in milliseconds
        path = "/path/to/song2.mp3",
        contentUri = Uri.parse("content://media/external/audio/media/3")
    ),Music(
        id = 4,
        title = "Song 4",
        artist = "Artist 4",
        album = "Album 4",
        duration = 240000, // Duration in milliseconds
        path = "/path/to/song2.mp3",
        contentUri = Uri.parse("content://media/external/audio/media/4")
    ),
    Music(
        id = 5,
        title = "Song 5",
        artist = "Artist 5",
        album = "Album 5",
        duration = 240000, // Duration in milliseconds
        path = "/path/to/song2.mp3",
        contentUri = Uri.parse("content://media/external/audio/media/4")
    ),
    Music(
        id = 6,
        title = "Song 6",
        artist = "Artist 6",
        album = "Album 6",
        duration = 240000, // Duration in milliseconds
        path = "/path/to/song2.mp3",
        contentUri = Uri.parse("content://media/external/audio/media/6")
    ),
    Music(
        id = 7,
        title = "Song 7",
        artist = "Artist 7",
        album = "Album 7",
        duration = 240000, // Duration in milliseconds
        path = "/path/to/song2.mp3",
        contentUri = Uri.parse("content://media/external/audio/media/7")
    ),
    Music(
        id = 8,
        title = "Song 8",
        artist = "Artist 8",
        album = "Album 8",
        duration = 240000, // Duration in milliseconds
        path = "/path/to/song2.mp3",
        contentUri = Uri.parse("content://media/external/audio/media/8")
    ),
    Music(
        id = 9,
        title = "Song 9",
        artist = "Artist 9",
        album = "Album 9",
        duration = 240000, // Duration in milliseconds
        path = "/path/to/song2.mp3",
        contentUri = Uri.parse("content://media/external/audio/media/9")
    ),
    Music(
        id = 10,
        title = "Song 10",
        artist = "Artist 10",
        album = "Album 10",
        duration = 240000, // Duration in milliseconds
        path = "/path/to/song2.mp3",
        contentUri = Uri.parse("content://media/external/audio/media/10")
    ),

)