package com.exoplayer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.exoplayer.ui.theme.ExoPlayerTheme

lateinit var mainViewModel: MainViewModel
lateinit var prefs: SharedPreferences
val APP_PREFERENCES_URL = "url"
val APP_PREFERENCES_URL_SOM = "url_som"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)

            mainViewModel = viewModel(factory = MainViewModel.Factory)

            mainViewModel.getPreferences()

            Log.d ("zzz", "url: ${mainViewModel.url}")

            val navController = rememberNavController()
            ExoPlayerTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Navigation(navController = navController)
                }
            }
        }
    }

}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MainScreen(navController)
        }
        composable("video") {
            VideoScreen()
        }
    }
}


