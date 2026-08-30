package com.example.moviehub.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moviehub.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val uiState by profileViewModel.uiState.collectAsState()

    var name by remember {
        mutableStateOf("")
    }

    var newPassword by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    /*
     * Kada se profil učita, postavljamo ime u TextField.
     */
    LaunchedEffect(uiState.profile?.uid) {

        uiState.profile?.let { profile ->

            name = profile.name
        }
    }

    val profileEmail =
        uiState.profile?.email ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Profil",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        if (uiState.isLoading) {

            CircularProgressIndicator()

            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                profileViewModel.clearMessages()
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Ime")
            },
            singleLine = true,
            enabled =
                !uiState.isSaving &&
                        !uiState.isLoading
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = profileEmail,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Email")
            },
            singleLine = true,
            enabled = false
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = {

                profileViewModel.updateName(name)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled =
                !uiState.isSaving &&
                        !uiState.isLoading
        ) {

            if (uiState.isSaving) {

                CircularProgressIndicator()

            } else {

                Text("Sačuvaj profil")
            }
        }

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        Text(
            text = "Promjena lozinke",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = newPassword,
            onValueChange = {
                newPassword = it
                profileViewModel.clearMessages()
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Nova lozinka")
            },
            visualTransformation =
                PasswordVisualTransformation(),
            singleLine = true,
            enabled =
                !uiState.isChangingPassword
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                profileViewModel.clearMessages()
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Potvrdi novu lozinku")
            },
            visualTransformation =
                PasswordVisualTransformation(),
            singleLine = true,
            enabled =
                !uiState.isChangingPassword
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(
            onClick = {

                profileViewModel.changePassword(
                    password = newPassword,
                    confirmPassword = confirmPassword
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled =
                !uiState.isChangingPassword
        ) {

            if (uiState.isChangingPassword) {

                CircularProgressIndicator()

            } else {

                Text("Promijeni lozinku")
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        uiState.successMessage?.let { message ->

            Text(
                text = message,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }

        uiState.errorMessage?.let { message ->

            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        TextButton(
            onClick = {

                profileViewModel.logout()

                navController.navigate("login") {

                    popUpTo("home") {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Odjava")
        }
    }
}