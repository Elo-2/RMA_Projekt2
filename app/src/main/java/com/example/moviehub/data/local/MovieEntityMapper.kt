package com.example.moviehub.data.local

import com.example.moviehub.data.local.entity.MovieEntity
import com.example.moviehub.model.Movie

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