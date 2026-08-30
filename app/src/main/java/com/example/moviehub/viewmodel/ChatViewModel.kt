package com.example.moviehub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviehub.data.remote.chat.ChatMessage
import com.example.moviehub.data.remote.chat.ChatRepository
import com.example.moviehub.data.remote.chat.ChatRepositoryContract
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val messageText: String = "",
    val isLoading: Boolean = true,
    val isSending: Boolean = false,
    val errorMessage: String? = null
)

class ChatViewModel(
    private val repository: ChatRepositoryContract = ChatRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ChatUiState()
    )

    val uiState: StateFlow<ChatUiState> =
        _uiState.asStateFlow()

    init {
        observeMessages()
    }

    private fun observeMessages() {

        viewModelScope.launch {

            try {

                repository
                    .getMessages()
                    .collect { messages ->

                        _uiState.update { state ->
                            state.copy(
                                messages = messages,
                                isLoading = false
                            )
                        }
                    }

            } catch (exception: Exception) {

                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage =
                            exception.message
                                ?: "Greška prilikom učitavanja poruka."
                    )
                }
            }
        }
    }

    fun updateMessageText(text: String) {

        _uiState.update { state ->
            state.copy(
                messageText = text,
                errorMessage = null
            )
        }
    }

    fun sendMessage(
        userId: String,
        userName: String
    ) {

        val message = _uiState.value.messageText.trim()

        if (message.isBlank()) {
            return
        }

        if (_uiState.value.isSending) {
            return
        }

        _uiState.update { state ->
            state.copy(
                isSending = true,
                errorMessage = null
            )
        }

        repository.sendMessage(
            userId = userId,
            userName = userName,
            message = message,

            onSuccess = {

                _uiState.update { state ->
                    state.copy(
                        messageText = "",
                        isSending = false,
                        errorMessage = null
                    )
                }
            },

            onError = { exception ->

                _uiState.update { state ->
                    state.copy(
                        isSending = false,
                        errorMessage =
                            exception.message
                                ?: "Greška prilikom slanja poruke."
                    )
                }
            }
        )
    }
}