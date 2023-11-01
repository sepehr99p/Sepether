package com.example.sepether.ui.music

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import com.example.sepether.ui.music.model.MusicFile
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util


class MusicPlayerService : Service() {

    var playerNotification: Notification? = null
    private var audioManager: AudioManager? = null
    private var mediaPlayer : MediaPlayer? = null
    private var playList : ArrayList<MusicFile> = arrayListOf()
    private var exoPlayer : SimpleExoPlayer? = null

    private val binder = LocalBinder()

    fun setPlaylist(newList : ArrayList<MusicFile>) {
        playList.clear()
        playList.addAll(newList)
    }

    override fun onCreate() {
        super.onCreate()
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        exoPlayer = SimpleExoPlayer.Builder(applicationContext).build()
    }

    private fun play() {
        val dataSourceFactory = DefaultDataSourceFactory(
            this,
            Util.getUserAgent(this, "exoplayer2example"),
            null
        )
        val source: MediaSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(
            MediaItem.fromUri(Uri.parse(playList[0].path)))

        exoPlayer?.prepare(source)
        exoPlayer?.playWhenReady
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