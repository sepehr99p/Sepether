package com.example.sepether.ui.music

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.widget.Toast
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sepether.ui.music.model.MusicFile


class MusicPlayerService : Service() {

    var playerNotification: Notification? = null
    private var audioManager: AudioManager? = null
    private var mediaPlayer : MediaPlayer? = null
    private var playList : ArrayList<MusicFile> = arrayListOf()

    private val binder = LocalBinder()

    fun setPlaylist(newList : ArrayList<MusicFile>) {
        playList.clear()
        playList.addAll(newList)
    }

    override fun onCreate() {
        super.onCreate()
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager

    }

    private fun play() {
        mediaPlayer = MediaPlayer.create(applicationContext,Uri.parse(playList[0].path))
        (mediaPlayer as MediaPlayer).start()
    }

    private fun pause() {

    }

    private fun playNext() {

    }

    private fun playPrevious() {

    }

    private fun showNotification() {

    }

    /**
     * Class used for the client Binder. Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods.
        fun getService(): MusicPlayerService = this@MusicPlayerService

        fun playNext() {}
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }



}