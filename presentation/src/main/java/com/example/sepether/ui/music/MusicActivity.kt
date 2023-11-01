package com.example.sepether.ui.music

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import com.example.sepether.ui.theme.Color

class MusicActivity : ComponentActivity() {

    //todo : ask for permission later to access musics at runtime

    private val viewModel by viewModels<MusicViewModel>()
    private var mBound: Boolean = false
    private lateinit var mService: MusicPlayerService

    private val TAG = "MusicActivity"
    private val STORAGE_PERMISSION_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = Color.LightColorScheme
            ) {
                MusicScreen(viewModel.getAllMusicFiles(this))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllMusicFiles(this)
    }

    override fun onStart() {
        super.onStart()
        // Bind to LocalService.
        Intent(this, MusicPlayerService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound = false
    }

    /** Defines callbacks for service binding, passed to bindService().  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance.
            val binder = service as MusicPlayerService.LocalBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }


}