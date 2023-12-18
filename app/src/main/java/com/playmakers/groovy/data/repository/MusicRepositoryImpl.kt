package com.playmakers.groovy.data.repository

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import com.playmakers.groovy.R
import com.playmakers.groovy.domain.model.RoomMusic
import com.playmakers.groovy.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.math.roundToInt

@Suppress("NAME_SHADOWING")
class MusicRepositoryImpl(
    private val application: Application
) : MusicRepository {
    override suspend fun getMusicFiles(): List<RoomMusic> {
        val musicList = mutableListOf<RoomMusic>()
        val musicResolver = application.contentResolver
        var idValue = 0

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


                val bitmap = getAlbumArt(application, Uri.withAppendedPath(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    id.toString()
                ), 100, 100)


                val music = RoomMusic(
                    id = idValue.toString(),
                    title = title,
                    artist = artist,
                    album = album,
                    source = path,
                    image = path,
                    imagePath = imagePath,
                    actualImage = bitmap
                )
                musicList.add(music)
                idValue ++
            }
        }
        return musicList
    }

    override suspend fun getMusicsFlow(): Flow<RoomMusic> {

        val musicResolver = application.contentResolver
        var idValue = 0

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

        val musicFlow  = flow{
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


                    val bitmap = getAlbumArt(application, Uri.withAppendedPath(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        id.toString()
                    ), 100, 100)


                    val music = RoomMusic(
                        id = idValue.toString(),
                        title = title,
                        artist = artist,
                        album = album,
                        source = path,
                        image = path,
                        imagePath = imagePath,
                        actualImage = bitmap
                    )
                    emit(music)
                    idValue ++
                    // delay(1.seconds)
                }
            }
        }

        return musicFlow
    }

    override suspend fun quickFetchMusicFiles(): List<RoomMusic> {
        val musicList = mutableListOf<RoomMusic>()
        val musicResolver = application.contentResolver
        var idValue = 0

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

                val music = RoomMusic(
                    id = idValue.toString(),
                    title = title,
                    artist = artist,
                    album = album,
                    source = path,
                    image = path,
                    imagePath = imagePath,
                    actualImage = null
                )
                musicList.add(music)
                idValue ++
            }
        }
        return musicList
    }
}

fun getAlbumArt(context: Context, uri: Uri, targetWidth: Int, targetHeight: Int): Bitmap {
    val mmr = MediaMetadataRetriever()
    mmr.setDataSource(context, uri)
    val data = mmr.embeddedPicture

    return if(data != null){
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(data, 0, data.size, options)
        options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight)
        options.inJustDecodeBounds = false
        BitmapFactory.decodeByteArray(data, 0, data.size, options)

    } else {
        BitmapFactory.decodeResource(context.resources, R.drawable.default_album_art_mini)
    }
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt()
        val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt()
        inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
    }
    return inSampleSize
}