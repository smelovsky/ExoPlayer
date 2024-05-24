package com.exoplayer

import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.rtsp.RtspMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(UnstableApi::class) @Composable
fun VideoScreen() {
    val playWhenReady by remember { mutableStateOf(true) }
    val context = LocalContext.current


    lateinit var player: ExoPlayer
    val playerView = PlayerView(context)

    if (mainViewModel.isTestMode) {
        val mediaItem = MediaItem.fromUri(mainViewModel.url)
        player = ExoPlayer.Builder(context).build()
        player.setMediaItem(mediaItem)
        playerView.player = player
    } else {


        val mediaItem = MediaItem.fromUri(mainViewModel.url_som)
        val mediaSource: MediaSource =
            RtspMediaSource.Factory()
                .setForceUseRtpTcp(true)
                .setDebugLoggingEnabled(true)
                .createMediaSource(mediaItem)

        player = ExoPlayer.Builder(context).build()
        player.setMediaSource(mediaSource)
        playerView.player = player

    }


    LaunchedEffect(player) {
        player.prepare()
        player.playWhenReady = playWhenReady
    }

    DisposableEffect(Unit) {
        val scope = CoroutineScope(Dispatchers.Default)
        val job = scope.launch {
            while (true) {
            }
        }


        onDispose {
            player.release()
            job.cancel()
        }
    }

    androidx.compose.material3.Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        AndroidView(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            factory = {

                playerView
            }
        )
    }
}
