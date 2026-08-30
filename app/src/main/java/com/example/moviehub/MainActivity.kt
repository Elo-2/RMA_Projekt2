package com.example.moviehub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviehub.navigation.MovieHubApp
import com.example.moviehub.ui.theme.MovieHubTheme
import com.example.moviehub.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val settingsViewModel: SettingsViewModel = viewModel()
            val uiState = settingsViewModel.uiState.collectAsState()

            MovieHubTheme(
                darkTheme = uiState.value.darkThemeEnabled
            ) {
                MovieHubApp(
                    settingsViewModel = settingsViewModel
                )
            }
        }
    }
}