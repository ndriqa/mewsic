package com.ndriqa.rootapp.viewmodels

import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.lifecycle.ViewModel
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ndriqa.rootapp.R
import com.ndriqa.rootapp.models.Song
import java.io.File

class MainActivityViewModel: ViewModel() {
    val allSongs = MutableLiveData<List<Song>>()

    fun getAllAudioFromDevice(context: Context){
        val tempAudioList: MutableList<Song> = mutableListOf()

        try {
            val pathColumn = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    MediaStore.Audio.AudioColumns.BUCKET_DISPLAY_NAME
                } else {
                    MediaStore.Audio.AudioColumns.DATA
                }
            val projection = arrayOf(
                MediaStore.Audio.AudioColumns.ARTIST,       // 0
                MediaStore.Audio.AudioColumns.YEAR,         // 1
                MediaStore.Audio.AudioColumns.TITLE,        // 2
                MediaStore.Audio.AudioColumns.DISPLAY_NAME, // 3
                MediaStore.Audio.AudioColumns.DURATION,     // 4
                MediaStore.Audio.AudioColumns.ALBUM,        // 5
                pathColumn,                                 // 6
                MediaStore.Audio.AudioColumns._ID,          // 7
                MediaStore.MediaColumns.DATE_MODIFIED       // 8
            )

            val publicPath = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val selection = "${MediaStore.Audio.AudioColumns.IS_MUSIC}=1"
            val sortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER

            val musicCursor = context.contentResolver.query(publicPath, projection, selection, null, sortOrder)

            // Query the storage for music files
            musicCursor?.use { cursor ->
                val artistIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST)
                val yearIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.YEAR)
                val titleIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE)
                val displayNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME)
                val durationIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION)
                val albumIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM)
                val relativePathIndex = cursor.getColumnIndexOrThrow(pathColumn)
                val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID)
                val dateAddedIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATE_MODIFIED)

                while (cursor.moveToNext()) {
                    // Now loop through the music files
                    val audioId = cursor.getLong(idIndex)
                    val audioArtist = cursor.getString(artistIndex)
                    val audioYear = cursor.getInt(yearIndex)
                    val audioTitle = cursor.getString(titleIndex)
                    val audioDisplayName = cursor.getString(displayNameIndex)
                    val audioDuration = cursor.getLong(durationIndex)
                    val audioAlbum = cursor.getString(albumIndex)
                    val audioRelativePath = cursor.getString(relativePathIndex)
                    val audioDateAdded = cursor.getInt(dateAddedIndex)

                    val audioFolderName =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            audioRelativePath ?: context.getString(R.string.slash)
                        } else {
                            val returnedPath = File(audioRelativePath).parentFile?.name
                                ?: context.getString(R.string.slash)
                            if (returnedPath != "0") {
                                returnedPath
                            } else {
                                context.getString(
                                    R.string.slash
                                )
                            }
                        }

                    // Add the current music to the list
                    tempAudioList.add(
                        Song(
                            audioArtist,
                            audioYear,
                            audioTitle,
                            audioDisplayName,
                            audioDuration,
                            audioAlbum,
                            audioFolderName,
                            audioId,
                            audioDateAdded
                        )
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Log.i("mytag", tempAudioList.map { it.audioArtist }.toString())
        allSongs.postValue(tempAudioList)
    }
}