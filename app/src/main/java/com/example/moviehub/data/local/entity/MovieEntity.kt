package com.example.moviehub.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviehub.model.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val rating: Double,
    val releaseYear: Int,
    val genre: String
)

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        rating = rating,
        releaseYear = releaseYear,
        genre = genre
    )
}

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        rating = rating,
        releaseYear = releaseYear,
        genre = genre
    )
}