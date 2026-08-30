package com.example.moviehub.ui.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moviehub.viewmodel.ChatViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ChatScreen(
    navController: NavController,
    chatViewModel: ChatViewModel = viewModel()
) {
    val uiState by chatViewModel.uiState.collectAsState()

    val currentUser = FirebaseAuth
        .getInstance()
        .currentUser

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Chat podrška",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        if (currentUser == null) {

            Text(
                text = "Niste prijavljeni.",
                style = MaterialTheme.typography.bodyLarge
            )

        } else {

            Text(
                text = "Prijavljeni ste kao: ${
                    currentUser.email ?: "Korisnik"
                }",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        uiState.errorMessage?.let { error ->

            Text(
                text = "Greška: $error",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(
                items = uiState.messages,
                key = { message ->
                    message.id
                }
            ) { message ->

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = message.userName,
                        style = MaterialTheme.typography.labelLarge
                    )

                    Text(
                        text = message.message,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedTextField(
                value = uiState.messageText,
                onValueChange = {
                    chatViewModel.updateMessageText(it)
                },
                modifier = Modifier.weight(1f),
                label = {
                    Text("Poruka")
                },
                singleLine = true,
                enabled = !uiState.isSending
            )

            Button(
                onClick = {

                    currentUser?.let { user ->

                        chatViewModel.sendMessage(
                            userId = user.uid,
                            userName = user.email
                                ?: "Korisnik"
                        )
                    }
                },
                modifier = Modifier.padding(start = 8.dp),
                enabled = !uiState.isSending &&
                        uiState.messageText.isNotBlank() &&
                        currentUser != null
            ) {

                Text(
                    if (uiState.isSending) {
                        "Šaljem..."
                    } else {
                        "Pošalji"
                    }
                )
            }
        }
    }
}