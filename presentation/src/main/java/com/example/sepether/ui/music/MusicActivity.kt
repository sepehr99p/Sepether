package com.example.sepether.ui.music

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import com.example.sepether.ui.theme.Color

class MusicActivity : ComponentActivity() {

    private val viewModel by viewModels<MusicViewModel>()
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


//    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
//    private fun isStoragePermissionGranted(): Boolean {
//        return ContextCompat.checkSelfPermission(
//            this,
//            Manifest.permission.READ_MEDIA_AUDIO
//        ) == PackageManager.PERMISSION_GRANTED
//    }
//
//    private fun requestStoragePermission() {
//        requestPermissions(
//            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//            STORAGE_PERMISSION_CODE
//        )
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            requestPermissions(
//                arrayOf(Manifest.permission.READ_MEDIA_AUDIO),
//                STORAGE_PERMISSION_CODE
//            )
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.READ_MEDIA_AUDIO),
//                STORAGE_PERMISSION_CODE
//            )
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if (requestCode == STORAGE_PERMISSION_CODE) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted by the user, you can proceed with your logic
//            } else {
//                // Permission denied by the user, handle this case (e.g., show a message)
//            }
//        }
//    }


}