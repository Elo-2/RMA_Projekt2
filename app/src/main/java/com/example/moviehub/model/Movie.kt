package com.example.moviehub.model

data class Movie(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val rating: Double = 0.0,
    val releaseYear: Int = 0,
    val genre: String = ""
)