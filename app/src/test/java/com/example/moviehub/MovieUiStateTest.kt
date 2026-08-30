package com.example.moviehub

import com.example.moviehub.model.Movie
import com.example.moviehub.viewmodel.MovieUiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MovieUiStateTest {

    @Test
    fun initialState_hasNoMovies() {
        val state = MovieUiState()

        assertTrue(state.movies.isEmpty())
    }

    @Test
    fun initialState_hasEmptySearchQuery() {
        val state = MovieUiState()

        assertEquals("", state.searchQuery)
    }

    @Test
    fun initialState_isNotLoading() {
        val state = MovieUiState()

        assertFalse(state.isLoading)
    }

    @Test
    fun state_canContainMovies() {
        val movie = Movie(
            id = 1,
            title = "Inception",
            description = "Test",
            imageUrl = "test.jpg",
            rating = 8.8,
            releaseYear = 2010,
            genre = "Sci-Fi"
        )

        val state = MovieUiState(
            movies = listOf(movie)
        )

        assertEquals(1, state.movies.size)
        assertEquals("Inception", state.movies.first().title)
    }

    @Test
    fun state_canContainMultipleMovies() {
        val movies = listOf(
            Movie(1, "Inception", "Test", "1.jpg", 8.8, 2010, "Sci-Fi"),
            Movie(2, "The Matrix", "Test", "2.jpg", 8.7, 1999, "Action")
        )

        val state = MovieUiState(
            movies = movies
        )

        assertEquals(2, state.movies.size)
    }

    @Test
    fun state_canContainSearchQuery() {
        val state = MovieUiState(
            searchQuery = "Inception"
        )

        assertEquals("Inception", state.searchQuery)
    }

    @Test
    fun state_canBeLoading() {
        val state = MovieUiState(
            isLoading = true
        )

        assertTrue(state.isLoading)
    }

    @Test
    fun state_canStopLoading() {
        val state = MovieUiState(
            isLoading = false
        )

        assertFalse(state.isLoading)
    }

    @Test
    fun state_preservesMovieData() {
        val movie = Movie(
            id = 5,
            title = "Interstellar",
            description = "Space movie",
            imageUrl = "space.jpg",
            rating = 8.7,
            releaseYear = 2014,
            genre = "Sci-Fi"
        )

        val state = MovieUiState(
            movies = listOf(movie)
        )

        val result = state.movies.first()

        assertEquals(5, result.id)
        assertEquals("Interstellar", result.title)
        assertEquals(8.7, result.rating, 0.0)
        assertEquals(2014, result.releaseYear)
        assertEquals("Sci-Fi", result.genre)
    }

    @Test
    fun state_preservesSearchAndLoading() {
        val state = MovieUiState(
            searchQuery = "Matrix",
            isLoading = true
        )

        assertEquals("Matrix", state.searchQuery)
        assertTrue(state.isLoading)
    }
}