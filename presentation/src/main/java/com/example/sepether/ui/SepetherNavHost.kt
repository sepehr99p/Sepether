package com.example.sepether.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sepether.ui.launcher.LauncherScreen
import com.example.sepether.ui.weather.WeatherScreen
import com.example.sepether.ui.weather.WeatherViewModel

@Composable
fun SepetherNavHost(viewModel: WeatherViewModel) {
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = "weather") {
        composable(route = "launcher") {
            LauncherScreen()
        }
        composable(route = "weather") {
            WeatherScreen(viewModel = viewModel)
        }
    }
}