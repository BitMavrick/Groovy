package com.playmakers.groovy.data.repository

import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import com.playmakers.groovy.domain.model.Music
import com.playmakers.groovy.domain.repository.MusicRepository

@Suppress("NAME_SHADOWING")
class MusicRepositoryImpl(
    private val application: Application
) : MusicRepository {
    override suspend fun getMusicFiles() : List<Music>{
        val musicList = mutableListOf<Music>()
        val musicResolver = application.contentResolver

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
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
            val pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

            while (cursor.moveToNext()){
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)
                val album = cursor.getString(albumColumn)
                val path = cursor.getString(pathColumn)
                val imagePath: Uri? = Uri.withAppendedPath(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    id.toString()
                )

                // music.imagePath?.let { getAlbumArt(LocalContext.current, it) }

                val music = Music(
                    id = id.toString(),
                    title = title,
                    artist = artist,
                    album = album,
                    source = path,
                    image = path,
                    imagePath = imagePath,
                    // actualImage = imagePath?.let { getAlbumArt(application.applicationContext, it) }!!.asImageBitmap()
                )
                musicList.add(music)
            }
        }

        return musicList
    }
}

//fun getAlbumArt(context: Context, uri: Uri): Bitmap {
//    val mmr = MediaMetadataRetriever()
//    mmr.setDataSource(context, uri)
//    val data = mmr.embeddedPicture
//    return if(data != null){
//        BitmapFactory.decodeByteArray(data, 0, data.size)
//
//    }else{
//        BitmapFactory.decodeResource(context.resources, R.drawable.default_album_art)
//    }
//}