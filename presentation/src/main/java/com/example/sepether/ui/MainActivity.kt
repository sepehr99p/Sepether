package com.example.sepether.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.domain.entities.responses.CurrentServerEntity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<WeatherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                HomeScreen()
            }
        }
    }

    @Composable
    private fun HomeScreen() {
        val newData by viewModel.newData

        LaunchedEffect(newData) {
            newData.current?.temp_c
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            TextC(value = newData.current?.temp_c.toString())
        }
    }

    @Composable
    private fun TextC(value : String) {
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = value
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCurrentWeather()
    }


}