package com.exoplayer

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class MainViewModel (
) : ViewModel() {

    var url = ""
    var url_som = ""

    var isTestMode = true

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MainViewModel()
            }
        }
    }

    fun putPreferences() {
        val editor = prefs.edit()
        editor.putString(APP_PREFERENCES_URL, mainViewModel.url).apply()
        editor.putString(APP_PREFERENCES_URL_SOM, mainViewModel.url_som).apply()
    }

    fun getPreferences() {
        mainViewModel.url = prefs.getString(APP_PREFERENCES_URL, "https://media.w3.org/2010/05/sintel/trailer.mp4")
            .toString()
        mainViewModel.url_som = prefs.getString(APP_PREFERENCES_URL_SOM, "rtsp://192.168.42.1:554/v4l2/video0")
            .toString()
    }

}
