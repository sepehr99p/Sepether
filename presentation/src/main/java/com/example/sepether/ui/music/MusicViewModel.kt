package com.example.sepether.ui.music

import androidx.lifecycle.ViewModel
import android.content.ContentResolver
import android.content.Context
import android.provider.MediaStore
import com.example.sepether.ui.music.model.MusicFile

class MusicViewModel : ViewModel() {



    fun getAllMusicFiles(context: Context): List<MusicFile> {
        val musicFiles = mutableListOf<MusicFile>()
        val contentResolver: ContentResolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA, // File path
            MediaStore.Audio.Media.DURATION
        )
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        val cursor = contentResolver.query(uri, projection, selection, null, sortOrder)
        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val artistColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val pathColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            val durationColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val artist = it.getString(artistColumn)
                val title = it.getString(titleColumn)
                val path = it.getString(pathColumn)
                val duration = it.getLong(durationColumn)

                val musicFile = MusicFile(id, artist, title, path, duration)
                musicFiles.add(musicFile)
            }
        }

        return musicFiles
    }

}