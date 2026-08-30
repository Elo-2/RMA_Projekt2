package com.example.moviehub.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moviehub.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val uiState by settingsViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Postavke",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Obavijesti",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Upravljajte postavkama obavijesti.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Switch(
                checked = uiState.notificationsEnabled,
                onCheckedChange = { enabled ->
                    settingsViewModel.setNotificationsEnabled(
                        enabled
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Tamna tema",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = if (uiState.darkThemeEnabled) {
                        "Tamna tema je uključena."
                    } else {
                        "Tamna tema je isključena."
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Switch(
                checked = uiState.darkThemeEnabled,
                onCheckedChange = { enabled ->
                    settingsViewModel.setDarkThemeEnabled(
                        enabled
                    )
                }
            )
        }
    }
}