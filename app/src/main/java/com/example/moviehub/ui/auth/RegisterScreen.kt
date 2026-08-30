package com.example.moviehub.ui.auth

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
import com.example.moviehub.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    var name by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    val uiState by authViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "MovieHub",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Registracija",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                authViewModel.clearError()
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Ime")
            },
            singleLine = true,
            enabled = !uiState.isLoading
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                authViewModel.clearError()
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Email")
            },
            singleLine = true,
            enabled = !uiState.isLoading
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                authViewModel.clearError()
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            enabled = !uiState.isLoading
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        uiState.errorMessage?.let { message ->

            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }

        Button(
            onClick = {
                authViewModel.register(
                    name = name,
                    email = email,
                    password = password,
                    onSuccess = {

                        navController.navigate("login") {

                            popUpTo("register") {
                                inclusive = true
                            }
                        }
                    }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {

                CircularProgressIndicator()

            } else {

                Text("Registruj se")
            }
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        TextButton(
            onClick = {

                navController.navigate("login") {

                    popUpTo("register") {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        ) {
            Text("Već imate račun? Prijavite se")
        }
    }
}