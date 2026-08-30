package com.example.moviehub.data.remote.chat

import kotlinx.coroutines.flow.Flow

interface ChatRepositoryContract {

    fun getMessages(): Flow<List<ChatMessage>>

    fun sendMessage(
        userId: String,
        userName: String,
        message: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    )
}