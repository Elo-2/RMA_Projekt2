package com.example.moviehub.data.remote.chat

data class ChatMessage(
    val id: String = "",
    val userId: String = "",
    val userName: String = "",
    val message: String = "",
    val timestamp: Long = 0L
)