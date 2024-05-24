package com.exoplayer

import android.app.Activity
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(navController : NavController) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var textFieldValue: TextFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = mainViewModel.url,
                selection = TextRange(mainViewModel.url.length),
                composition = TextRange(0, mainViewModel.url.length)
            )
        )
    }
    var textFieldValueSom: TextFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = mainViewModel.url_som,
                selection = TextRange(mainViewModel.url_som.length),
                composition = TextRange(0, mainViewModel.url_som.length)
            )
        )
    }


    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()

    androidx.compose.material3.Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {

            TopAppBar (
                title = {
                    Text(text = "ExoPlayer test app (ver. 0.2)")
                }
            )
        },
        bottomBar = {

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {

                OutlinedButton(
                    onClick = {
                        scope.launch {
                            activity?.let {

                                val wiFiIntent = Intent(Settings.ACTION_WIFI_SETTINGS)
                                ActivityCompat.startActivityForResult(
                                    activity,
                                    wiFiIntent,
                                    0,
                                    null
                                )
                            }
                        }
                    }
                ) {
                    Text("Connection settings")
                }
            }

        },
        content = { padding ->
            Column(
                modifier = Modifier.padding(padding).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),

                    label = { Text(text = "URL:")},
                    value = textFieldValue,
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            mainViewModel.url = textFieldValue.text
                            mainViewModel.putPreferences()
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    ),
                    onValueChange = {
                            newValue -> textFieldValue = newValue
                    },
                )
                OutlinedButton(
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 30.dp),
                    onClick = {
                        mainViewModel.isTestMode = true
                        navController.navigate("video")
                    }
                ) {
                    Text(text = "Play (http)")
                }

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),

                    label = { Text(text = "URL:")},
                    value = textFieldValueSom,
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            mainViewModel.url_som = textFieldValueSom.text
                            mainViewModel.putPreferences()
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    ),
                    onValueChange = {
                            newValue -> textFieldValueSom = newValue
                    },
                )

                OutlinedButton(
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 30.dp),
                    onClick = {
                        mainViewModel.isTestMode = false
                        navController.navigate("video")
                    }
                ) {
                    Text(text = "Play (rtsp)")
                }

            }
        }
    )
}
