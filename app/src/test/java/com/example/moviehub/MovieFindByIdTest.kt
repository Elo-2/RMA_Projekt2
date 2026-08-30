package com.example.moviehub

import com.example.moviehub.model.Movie
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MovieFindByIdTest {

    private val movies = listOf(
        Movie(
            id = 1,
            title = "Inception",
            description = "A thief who steals secrets through dreams.",
            imageUrl = "inception.jpg",
            rating = 8.8,
            releaseYear = 2010,
            genre = "Sci-Fi"
        ),
        Movie(
            id = 2,
            title = "The Dark Knight",
            description = "Batman faces a criminal mastermind.",
            imageUrl = "dark_knight.jpg",
            rating = 9.0,
            releaseYear = 2008,
            genre = "Action"
        ),
        Movie(
            id = 3,
            title = "Interstellar",
            description = "Explorers travel through a wormhole.",
            imageUrl = "interstellar.jpg",
            rating = 8.7,
            releaseYear = 2014,
            genre = "Sci-Fi"
        )
    )

    private fun getMovieById(id: Int): Movie? {
        return movies.find { movie ->
            movie.id == id
        }
    }

    @Test
    fun existingId_returnsCorrectMovie() {
        val movie = getMovieById(1)

        assertEquals("Inception", movie?.title)
    }

    @Test
    fun secondId_returnsCorrectMovie() {
        val movie = getMovieById(2)

        assertEquals("The Dark Knight", movie?.title)
    }

    @Test
    fun thirdId_returnsCorrectMovie() {
        val movie = getMovieById(3)

        assertEquals("Interstellar", movie?.title)
    }

    @Test
    fun nonExistingId_returnsNull() {
        val movie = getMovieById(999)

        assertNull(movie)
    }

    @Test
    fun negativeId_returnsNull() {
        val movie = getMovieById(-1)

        assertNull(movie)
    }

    @Test
    fun zeroId_returnsNull() {
        val movie = getMovieById(0)

        assertNull(movie)
    }

    @Test
    fun movieId_returnsCorrectId() {
        val movie = getMovieById(3)

        assertEquals(3, movie?.id)
    }

    @Test
    fun firstMovie_hasCorrectDescription() {
        val movie = getMovieById(1)

        assertEquals(
            "A thief who steals secrets through dreams.",
            movie?.description
        )
    }

    @Test
    fun secondMovie_hasCorrectDescription() {
        val movie = getMovieById(2)

        assertEquals(
            "Batman faces a criminal mastermind.",
            movie?.description
        )
    }

    @Test
    fun thirdMovie_hasCorrectDescription() {
        val movie = getMovieById(3)

        assertEquals(
            "Explorers travel through a wormhole.",
            movie?.description
        )
    }

    @Test
    fun firstMovie_hasCorrectImageUrl() {
        val movie = getMovieById(1)

        assertEquals("inception.jpg", movie?.imageUrl)
    }

    @Test
    fun secondMovie_hasCorrectImageUrl() {
        val movie = getMovieById(2)

        assertEquals("dark_knight.jpg", movie?.imageUrl)
    }

    @Test
    fun thirdMovie_hasCorrectImageUrl() {
        val movie = getMovieById(3)

        assertEquals("interstellar.jpg", movie?.imageUrl)
    }

    @Test
    fun firstMovie_hasCorrectRating() {
        val movie = getMovieById(1)

        assertEquals(8.8, movie?.rating ?: 0.0, 0.0)
    }

    @Test
    fun secondMovie_hasCorrectRating() {
        val movie = getMovieById(2)

        assertEquals(9.0, movie?.rating ?: 0.0, 0.0)
    }

    @Test
    fun thirdMovie_hasCorrectRating() {
        val movie = getMovieById(3)

        assertEquals(8.7, movie?.rating ?: 0.0, 0.0)
    }

    @Test
    fun firstMovie_hasCorrectReleaseYear() {
        val movie = getMovieById(1)

        assertEquals(2010, movie?.releaseYear)
    }

    @Test
    fun secondMovie_hasCorrectReleaseYear() {
        val movie = getMovieById(2)

        assertEquals(2008, movie?.releaseYear)
    }

    @Test
    fun thirdMovie_hasCorrectReleaseYear() {
        val movie = getMovieById(3)

        assertEquals(2014, movie?.releaseYear)
    }

    @Test
    fun movieId_returnsCorrectGenre() {
        val movie = getMovieById(1)

        assertEquals("Sci-Fi", movie?.genre)
    }

    @Test
    fun secondMovie_returnsCorrectGenre() {
        val movie = getMovieById(2)

        assertEquals("Action", movie?.genre)
    }

    @Test
    fun thirdMovie_returnsCorrectGenre() {
        val movie = getMovieById(3)

        assertEquals("Sci-Fi", movie?.genre)
    }

    @Test
    fun foundMovie_isNotNull() {
        val movie = getMovieById(1)

        assertNotNull(movie)
    }

    @Test
    fun foundMovie_hasMatchingId() {
        val movie = getMovieById(2)

        assertTrue(movie?.id == 2)
    }

    @Test
    fun differentIds_returnDifferentMovies() {
        val firstMovie = getMovieById(1)
        val secondMovie = getMovieById(2)

        assertTrue(firstMovie?.id != secondMovie?.id)
    }
}