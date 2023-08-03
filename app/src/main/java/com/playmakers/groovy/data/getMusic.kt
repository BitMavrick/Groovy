@file:Suppress("NAME_SHADOWING")

package com.playmakers.groovy.data

import android.annotation.SuppressLint
import android.content.Context
import android.provider.MediaStore

@SuppressLint("Recycle")
fun getMusic(context: Context): List<Music>{
    val musicList = mutableListOf<Music>()
    val musicResolver = context.contentResolver

    val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.ALBUM,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Media.DATA
    )

    val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
    val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

    val cursor = musicResolver.query(
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        projection,
        selection,
        null,
        sortOrder
    )

    cursor?.use { cursor ->
        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
        val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
        val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
        val albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
        val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
        val pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

        while (cursor.moveToNext()){
            val id = cursor.getLong(idColumn)
            val title = cursor.getString(titleColumn)
            val artist = cursor.getString(artistColumn)
            val album = cursor.getString(albumColumn)
            val duration = cursor.getLong(durationColumn)
            val path = cursor.getString(pathColumn)

            val music = Music(
                id = id,
                title = title,
                artist = artist,
                album = album,
                duration = duration,
                path = path
            )

            musicList.add(music)
        }
    }

    return musicList
}